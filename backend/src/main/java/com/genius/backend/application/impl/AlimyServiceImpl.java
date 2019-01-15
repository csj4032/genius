package com.genius.backend.application.impl;

import com.genius.backend.application.AlimyService;
import com.genius.backend.application.exception.NotExistAlimyException;
import com.genius.backend.application.exception.NotExistUserException;
import com.genius.backend.domain.model.alimy.Alimy;
import com.genius.backend.domain.model.alimy.AlimyDto;
import com.genius.backend.domain.model.alimy.AlimyStatus;
import com.genius.backend.domain.model.log.*;
import com.genius.backend.domain.repository.*;
import com.genius.backend.infrastructure.security.social.GeniusSocialUserDetail;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.quartz.CronExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.kakao.api.impl.KakaoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
	private LogRepository logRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public AlimyDto.Response findById(Long id) {
		Optional<Alimy> alimy = alimyRepository.findById(id);
		return alimy.isPresent() ? modelMapper.map(alimy, AlimyDto.Response.class) : null;
	}

	@Override
	public Page<AlimyDto.Response> listForPage(Pageable pageable) {
		var id = ((GeniusSocialUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
		var alimyList = alimyRepository.findByUserId(id, pageable).getContent();
		return new PageImpl(modelMapper.map(alimyList, new TypeToken<List<AlimyDto.Response>>() {}.getType()), pageable, alimyList.size());
	}

	@Transactional(readOnly = true)
	public Page<AlimyDto.Response> searchWithPage(AlimyDto.Search search, Pageable pageable) {
		search.setUserId(((GeniusSocialUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId());
		var alimyList = alimyRepository.findAll(AlimyPredicate.search(search), pageable).getContent();
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
		var id = userRepository.findById(request.getUserId()).orElseThrow(() -> new NotExistUserException(request.getUserId()));
		var alimy = modelMapper.map(request, Alimy.class);
		alimy.setUser(id);
		alimy.setAlimyUnit(request.getUnitType());
		alimyRepository.save(alimy);
		return modelMapper.map(alimy, AlimyDto.Response.class);
	}

	@Override
	@Transactional
	@PostAuthorize(value = "returnObject.username == authentication.principal.username")
	public AlimyDto.Response update(AlimyDto.RequestForUpdate request) {
		var alimy = alimyRepository.findById(request.getId()).orElseThrow(() -> new NotExistAlimyException(request.getId()));
		modelMapper.map(request, alimy);
		alimyRepository.save(alimy);
		var response = modelMapper.map(alimy, AlimyDto.Response.class);
		alimy.setAlimyUnit(request.getUnitType());
		return response;
	}

	@Override
	@PostAuthorize(value = "returnObject.user.id == authentication.principal.id")
	public Alimy update(AlimyDto.RequestForStatus request) {
		var alimy = alimyRepository.findById(request.getId()).orElseThrow(() -> new NotExistAlimyException(request.getId()));
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
				.forEach(e -> {
					var resultCode = new KakaoTemplate(e.getUser().getAccessToken()).talkOperation().sendTalk(e.getMessage());
					var value = SendTalkLog.builder().subject(e.getSubject()).message(e.getMessage()).cronExpression(e.getCronExpression()).build();
					var gLog = Log.builder().type(LogType.SEND_TALK).value(value.toJson(new LogJsonValue())).build();
					logRepository.save(gLog);
					log.info("result : {}", resultCode);
				});
	}
}