package com.genius.backend.application;

import com.genius.backend.domain.model.alimy.Alimy;

import java.util.function.Consumer;

public enum ProviderType implements AlimySender {

	KAKAO("kakao", (alimy) -> {
		System.out.println("kakao talk");
	}),

	LINE("line", (alimy -> {
		System.out.println("line message");
	}));

	private String name;
	private Consumer<Alimy> consumer;

	ProviderType(String name, Consumer<Alimy> consumer) {
		this.name = name;
		this.consumer = consumer;
	}

	@Override
	public void sendAlimy(Alimy alimy) {
		this.consumer.accept(alimy);
	}
}