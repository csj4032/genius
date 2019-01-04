package com.genius.backend.domain.model.alimy;

import com.genius.backend.domain.converter.EnumAttributeConverter;

public class AlimyUnitTypeConverter extends EnumAttributeConverter<AlimyUnitType, String> {

	@Override
	protected Class<AlimyUnitType> enumClass() {
		return AlimyUnitType.class;
	}
}