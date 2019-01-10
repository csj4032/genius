package com.genius.backend.domain.model.alimy;

import com.genius.backend.domain.converter.EnumAttributeConverter;
import org.springframework.stereotype.Component;

@Component
public class AlimyUnitTypeAttributeConverter extends EnumAttributeConverter<AlimyUnitType, String> {

	@Override
	protected Class<AlimyUnitType> enumClass() {
		return AlimyUnitType.class;
	}
}