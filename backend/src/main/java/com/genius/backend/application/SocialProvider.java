package com.genius.backend.application;

import com.genius.backend.domain.model.alimy.Alimy;

import java.util.List;

public interface SocialProvider {

	void sendAlimy(List<Alimy> alimies);
}