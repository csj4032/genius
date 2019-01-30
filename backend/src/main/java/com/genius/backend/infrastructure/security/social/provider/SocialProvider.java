package com.genius.backend.infrastructure.security.social.provider;

public interface SocialProvider {

	void save();

	void sendMessage(String object);
}