package com.genius.backend.domain.model.user;

import com.genius.backend.application.ProviderType;
import com.genius.backend.domain.converter.EnumAttributeConverter;

public class ProviderTypeAttributeConverter extends EnumAttributeConverter<ProviderType, String> {

	@Override
	protected Class<ProviderType> enumClass() {
		return ProviderType.class;
	}
}