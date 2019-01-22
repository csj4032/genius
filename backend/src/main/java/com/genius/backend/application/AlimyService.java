package com.genius.backend.application;

import com.genius.backend.domain.model.alimy.Alimy;
import com.genius.backend.domain.model.alimy.AlimyDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AlimyService {

	Page<AlimyDto.Response> listForPage(Pageable pageable);

	Page<AlimyDto.Response> searchWithPage(AlimyDto.Search search, Pageable pageable);

	AlimyDto.Response findById(Long alimyId);

	AlimyDto.Response save(AlimyDto.RequestForSave request);

	AlimyDto.Response update(AlimyDto.RequestForUpdate request);

	Alimy update(AlimyDto.RequestForStatus request);

	int delete(List<Long> alimyIds);

	void sendAlimyForBatch();

}