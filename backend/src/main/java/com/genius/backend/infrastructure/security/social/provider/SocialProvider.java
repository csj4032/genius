package com.genius.backend.infrastructure.security.social.provider;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public interface SocialProvider {

	void sendMessage(String object);
}
