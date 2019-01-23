package com.genius.backend.application;

public enum ProviderType {

	KAKAO("kakao"),
	LINE("line");

	private String name;

	ProviderType(String name) {
		this.name = name;
	}
}