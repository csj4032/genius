package com.genius.backend.application;

import com.genius.backend.domain.model.alimy.Alimy;
import com.genius.backend.domain.model.alimy.AlimyDto;
import com.genius.backend.domain.model.alimy.AlimyStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AlimyService {

	Page<AlimyDto.Response> listForPage(Pageable pageable);

	Page<AlimyDto.Response> searchWithPage(AlimyDto.Search search, Pageable pageable);

	AlimyDto.Response findById(Long alimyId);

	AlimyDto.ResponseForForm findByIdForForm(Long id);

	List<AlimyDto.Response> findByUserId(Long userId);

	AlimyDto.Response save(AlimyDto.RequestForSave request);

	void save(AlimyDto.RequestForSaveForm request);

	void save(Alimy alimy);

	AlimyDto.Response update(AlimyDto.RequestForUpdate request);

	void update(AlimyDto.RequestForUpdateForm request);

	Alimy update(AlimyDto.RequestForStatus request);

	int delete(List<Long> alimyIds);

	void sendAlimyForBatch();

	AlimyStatus status(Long alimyId);

	Boolean existsByUserId(long id);
}