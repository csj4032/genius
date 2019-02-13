package com.genius.backend.application.impl;

import com.genius.backend.application.AlimyService;
import com.genius.backend.application.exception.NotExistAlimyException;
import com.genius.backend.application.exception.NotExistUserException;
import com.genius.backend.domain.model.alimy.Alimy;
import com.genius.backend.domain.model.alimy.AlimyDto;
import com.genius.backend.domain.model.alimy.AlimyStatus;
import com.genius.backend.domain.repository.AlimyPredicate;
import com.genius.backend.domain.repository.AlimyRepository;
import com.genius.backend.domain.repository.AlimyUnitRepository;
import com.genius.backend.domain.repository.UserRepository;
import com.genius.backend.infrastructure.security.social.GeniusSocialUserDetail;
import com.genius.backend.infrastructure.security.social.provider.SocialProviderBuilder;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.quartz.CronExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

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

	@Autowired
	private SocialProviderBuilder socialProviderBuilder;

	@Override
	public AlimyDto.Response findById(Long id) {
		Optional<Alimy> alimy = getAlimy(id);
		return alimy.isPresent() ? modelMapper.map(alimy.get(), AlimyDto.Response.class) : null;
	}

	@Override
	public AlimyDto.ResponseForForm findByIdForForm(Long id) {
		Optional<Alimy> alimy = getAlimy(id);
		return alimy.isPresent() ? modelMapper.map(alimy.get(), AlimyDto.ResponseForForm.class) : null;
	}

	@Override
	public List<AlimyDto.Response> findByUserId(Long userId) {
		Optional<List<Alimy>> alimyList = alimyRepository.findTop5ByUserIdOrderByIdDesc(userId);
		return alimyList.isPresent() ? modelMapper.map(alimyList.get(), new TypeToken<List<AlimyDto.Response>>() {
		}.getType()) : Collections.emptyList();
	}

	@Override
	public Page<AlimyDto.Response> listForPage(Pageable pageable) {
		var id = ((GeniusSocialUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
		var alimyList = alimyRepository.findByUserId(id, pageable).getContent();
		return new PageImpl(modelMapper.map(alimyList, new TypeToken<List<AlimyDto.Response>>() {
		}.getType()), pageable, alimyList.size());
	}

	@Transactional(readOnly = true)
	public Page<AlimyDto.Response> searchWithPage(AlimyDto.Search search, Pageable pageable) {
		search.setUserId(((GeniusSocialUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId());
		var alimyList = alimyRepository.findAll(AlimyPredicate.search(search), pageable).getContent();
		return new PageImpl(modelMapper.map(alimyList, new TypeToken<List<AlimyDto.Response>>() {
		}.getType()), pageable, alimyList.size());
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
		Alimy alimy = getAlimy(request);
		save(alimy);
		return modelMapper.map(alimy, AlimyDto.Response.class);
	}

	@Override
	public void save(AlimyDto.RequestForSaveForm requestForSaveForm) {
		var userId = ((GeniusSocialUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
		if (requestForSaveForm.getAlimyId() != null && requestForSaveForm.getAlimyId() > 0) {
			var alimy = alimyRepository.findById(requestForSaveForm.getAlimyId()).orElseThrow();
			if (alimy.getUser().getId() == userId) {
				alimy.setSubject(requestForSaveForm.getSubject());
				alimy.setMessage(requestForSaveForm.getMessage());
				alimy.setAlimyUnit(requestForSaveForm.getUnitType());
				alimyRepository.save(alimy);
			}
		} else {
			var requestForSave = modelMapper.map(requestForSaveForm, AlimyDto.RequestForSave.class);
			requestForSave.setUserId(userId);
			save(getAlimy(requestForSave));
		}
	}

	@Override
	public void save(Alimy alimy) {
		alimyRepository.save(alimy);
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
	public void sendAlimyForBatch() {
		alimyRepository.findByStatus(AlimyStatus.START).stream().filter(getAlimyPredicate(new Date())).forEach(e -> socialProviderBuilder.create(e.getUser()).pushMessage(e.getSubject() + "\n" + e.getMessage()));
	}

	@Override
	public AlimyStatus status(Long id) {
		var alimy = alimyRepository.findById(id).orElseThrow();
		var status = alimy.getStatus().equals(AlimyStatus.START) ? AlimyStatus.STOP : AlimyStatus.START;
		alimy.setStatus(status);
		alimyRepository.save(alimy);
		return status;
	}

	@NotNull
	private Predicate<Alimy> getAlimyPredicate(Date date) {
		return e -> {
			try {
				CronExpression cronTrigger = new CronExpression(e.getCronExpression());
				return cronTrigger.isSatisfiedBy(date);
			} catch (ParseException ex) {
				log.error("잘못된 크론 표현식 입니다. AlimyId : {} {}", e.getId(), e.getCronExpression());
				return false;
			}
		};
	}

	@NotNull
	private Optional<Alimy> getAlimy(Long id) {
		return alimyRepository.findById(id);
	}

	@NotNull
	private Alimy getAlimy(AlimyDto.RequestForSave request) {
		var user = userRepository.findById(request.getUserId()).orElseThrow(() -> new NotExistUserException(request.getUserId()));
		var alimy = modelMapper.map(request, Alimy.class);
		alimy.setUser(user);
		alimy.setAlimyUnit(request.getUnitType());
		return alimy;
	}
}