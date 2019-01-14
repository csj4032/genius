package com.genius.backend.domain.model.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotNull;

public class AuthDto {

	@Data
	@Builder
	@ToString
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Request {
		@NotNull
		@JsonProperty(value = "access_token")
		private String accessToken;
		@NotNull
		@JsonProperty(value = "expries_in")
		private long expiresIn;
		@JsonProperty(value = "refresh_token")
		@NotNull
		private String refreshToken;
		@JsonProperty(value = "refresh_token_expries_in")
		private long refreshTokenExpiresIn;
		private String scope;
		@JsonProperty(value = "token_type")
		private String tokenType;
	}

	@Data
	@Builder
	@ToString
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Response {
		@Builder.Default
		private long userId = 1l;
		@Builder.Default
		private String username = "최성조";
		@Builder.Default
		private String userImage = "http://k.kakaocdn.net/dn/cJDSmp/btqro9kj4ha/jnyMoie9RIQFBFt1aLkwz1/profile_640x640s.jpg";
		private String accessToken;
		@Builder.Default
		private String tokenType = "Bearer";
	}
}