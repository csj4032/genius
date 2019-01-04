package com.genius.backend.application;

import com.genius.backend.domain.model.auth.AuthDto;

public interface AuthService {

	AuthDto.Response auth(AuthDto.Request request);
}
