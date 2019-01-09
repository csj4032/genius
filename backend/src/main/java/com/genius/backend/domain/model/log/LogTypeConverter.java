package com.genius.backend.domain.model.log;

import com.genius.backend.domain.converter.EnumAttributeConverter;

public class LogTypeConverter extends EnumAttributeConverter<LogType, Integer> {

	@Override
	protected Class<LogType> enumClass() {
		return LogType.class;
	}
}
