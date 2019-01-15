package com.genius.backend.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.genius.backend.domain.model.auth.AuthDto;

public interface AuthService {

	AuthDto.Response auth(AuthDto.Request request) throws JsonProcessingException;
}
