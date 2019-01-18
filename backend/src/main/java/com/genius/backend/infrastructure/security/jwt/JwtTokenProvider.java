package com.genius.backend.infrastructure.security.jwt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.genius.backend.domain.model.auth.Role;
import com.genius.backend.domain.model.user.User;
import com.genius.backend.infrastructure.security.social.GeniusSocialUserDetail;
import com.genius.backend.infrastructure.security.social.GeniusUserDetailToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.*;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Date;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.reducing;
import static java.util.stream.Collectors.toSet;

@Slf4j
@Component
public class JwtTokenProvider {

	@Value("${genius.jwtSecret}")
	private String jwtSecret;

	@Value("${genius.jwtExpirationInMs}")
	private int jwtExpirationInMs;

	public boolean validateToken(String token) throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException, IllegalArgumentException {
		if (!StringUtils.hasText(token)) return false;
		Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
		return true;
	}

	public Long getUserIdFromJWT(String token) {
		var claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
		return Long.parseLong(claims.getSubject());
	}

	public GeniusUserDetailToken getGeniusUserDetailTokenFromJWT(String token) {
		var claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
		try {
			return new ObjectMapper().readValue(claims.getSubject(), GeniusUserDetailToken.class);
		} catch (Exception ex) {
			log.error(ex.getLocalizedMessage());
		}
		return null;
	}

	public String generateToken(User user) throws JsonProcessingException {
		var now = new Date();
		var expiryDate = new Date(now.getTime() + jwtExpirationInMs);
		var geniusUserDetailToken = GeniusUserDetailToken.builder().id(user.getId()).username(user.getUsername()).password(user.getProviderUserId()).roles(user.getRoles().stream().map(Role::getName).collect(toSet())).build();
		return Jwts.builder()
				.setSubject(new ObjectMapper().writeValueAsString(geniusUserDetailToken))
				.setIssuedAt(new Date())
				.setExpiration(expiryDate)
				.signWith(SignatureAlgorithm.HS512, jwtSecret)
				.compact();
	}
}