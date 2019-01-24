package com.genius.backend.application;

import com.genius.backend.domain.converter.ConvertedEnum;
import com.genius.backend.domain.converter.ReverseEnumResolver;

public enum ProviderType implements ConvertedEnum<String> {

	KAKAO("kakao"),
	LINE("line"),
	FACEBOOK("facebook");

	private String name;

	ProviderType(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	@Override
	public String toDbValue() {
		return name;
	}

	public String getDbValue() {
		return name;
	}

	public static final ReverseEnumResolver<ProviderType, String> dbValueResolver = new ReverseEnumResolver<>(ProviderType.class, ProviderType::getDbValue);

	public static ProviderType fromDbValue(String dbValue) {
		return dbValueResolver.get(dbValue);
	}
}