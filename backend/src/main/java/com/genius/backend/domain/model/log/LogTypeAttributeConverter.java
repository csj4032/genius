package com.genius.backend.domain.model.log;

import com.genius.backend.domain.converter.EnumAttributeConverter;

public class LogTypeAttributeConverter extends EnumAttributeConverter<LogType, Integer> {

	@Override
	protected Class<LogType> enumClass() {
		return LogType.class;
	}
}