package com.genius.backend.application;

import com.genius.backend.domain.model.alimy.Alimy;
import com.genius.backend.domain.model.alimy.AlimyDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AlimyService {

	AlimyDto.Response findById(Long alimyId);

	AlimyDto.Response save(AlimyDto.RequestForSave request);

	AlimyDto.Response update(AlimyDto.RequestForUpdate request);

	Alimy update(AlimyDto.RequestForStatus request);

	Page<AlimyDto.Response> listForPage(Pageable pageable);

	int delete(List<Long> alimyIds);

	void sendTalkForBatch();

}