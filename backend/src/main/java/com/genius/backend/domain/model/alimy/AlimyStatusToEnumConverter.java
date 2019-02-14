package com.genius.backend.domain.model.alimy;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AlimyStatusToEnumConverter implements Converter<String, AlimyStatus> {

	@Override
	public AlimyStatus convert(String value) {
		return AlimyStatus.of(value);
	}
}
