package com.genius.backend.domain.model.alimy;

import org.springframework.core.convert.converter.Converter;

public class AlimyStatusToEnumConverter implements Converter<Integer, AlimyStatus> {

	@Override
	public AlimyStatus convert(Integer value) {
		return AlimyStatus.of(value);
	}
}
