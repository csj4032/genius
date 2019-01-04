package com.genius.backend.domain.model.alimy;

import com.genius.backend.domain.converter.EnumAttributeConverter;
import org.springframework.stereotype.Component;

@Component
public class AlimyStatusAttributeConverter extends EnumAttributeConverter<AlimyStatus, Integer> {

	@Override
	protected Class<AlimyStatus> enumClass() {
		return AlimyStatus.class;
	}
}