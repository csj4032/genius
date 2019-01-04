package com.genius.backend.application.impl;

import com.genius.backend.application.AlimyService;
import com.genius.backend.domain.model.alimy.Alimy;
import com.genius.backend.domain.model.alimy.AlimyDto;
import com.genius.backend.domain.model.alimy.AlimyStatus;
import com.genius.backend.domain.repository.AlimyRepository;
import com.genius.backend.domain.repository.AlimyUnitRepository;
import com.genius.backend.domain.repository.UserRepository;
import com.genius.backend.infrastructure.security.social.GeniusUserDetail;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.quartz.CronExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.kakao.api.impl.KakaoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class AlimyServiceImpl implements AlimyService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AlimyRepository alimyRepository;

	@Autowired
	private AlimyUnitRepository alimyUnitRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public AlimyDto.Response findById(Long id) {
		return modelMapper.map(alimyRepository.findById(id).get(), AlimyDto.Response.class);
	}

	@Override
	public Page<AlimyDto.Response> listForPage(Pageable pageable) {
		var id = ((GeniusUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
		var alimyList = alimyRepository.findByUserId(id, pageable).getContent();
		return new PageImpl(modelMapper.map(alimyList, new TypeToken<List<AlimyDto.Response>>() {}.getType()), pageable, alimyList.size());
	}

	@Override
	@Transactional
	public int delete(List<Long> ids) {
		alimyUnitRepository.deleteAlimyUnitByAlimyIds(ids);
		return alimyRepository.deleteByIds(ids);
	}

	@Override
	@Transactional
	public AlimyDto.Response save(AlimyDto.RequestForSave request) {
		var alimy = modelMapper.map(request, Alimy.class);
		alimy.setUser(userRepository.findById(request.getUserId()).get());
		alimy.setAlimyUnit(request.getUnitType());
		alimyRepository.save(alimy);
		var response = modelMapper.map(alimy, AlimyDto.Response.class);
		return response;
	}

	@Override
	@Transactional
	@PostAuthorize(value = "returnObject.username == authentication.principal.username")
	public AlimyDto.Response update(AlimyDto.RequestForUpdate request) {
		var alimy = alimyRepository.findById(request.getId()).get();
		modelMapper.map(request, alimy);
		alimyRepository.save(alimy);
		var response = modelMapper.map(alimy, AlimyDto.Response.class);
		alimy.setAlimyUnit(request.getUnitType());
		return response;
	}

	@Override
	@PostAuthorize(value = "returnObject.user.id == authentication.principal.id")
	public Alimy update(AlimyDto.RequestForStatus request) {
		var alimy = alimyRepository.findById(request.getId()).get();
		alimy.setStatus(request.getStatus());
		alimyRepository.save(alimy);
		return alimy;
	}

	@Override
	public void sendTalkForBatch() {
		var alimyList = alimyRepository.findByStatus(AlimyStatus.START);
		var date = new Date();
		alimyList.parallelStream()
				.filter(e -> {
					try {
						CronExpression cronTrigger = new CronExpression(e.getCronExpression());
						return cronTrigger.isSatisfiedBy(date);
					} catch (ParseException ex) {
						log.error("잘못된 크론 표현식 입니다. AlimyId : {} {}", e.getId(), e.getCronExpression());
						return false;
					}
				})
				.forEach(e -> new KakaoTemplate(e.getUser().getAccessToken()).talkOperation().sendTalk(e.getMessage()));
	}
}