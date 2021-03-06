package com.genius.backend.infrastructure.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.genius.backend.domain.model.auth.Role;
import com.genius.backend.domain.model.user.User;
import com.genius.backend.infrastructure.security.social.GeniusSocialUserDetail;
import com.genius.backend.infrastructure.security.social.GeniusUserDetailToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

public class JwtTokenProviderTest {

	@Test
	public void generateTokenTest() throws IOException {
		var objectMapper = new ObjectMapper();
		var user = new User();
		user.setId(1);
		user.setUsername("최성조");
		user.setProviderUserId("1234");
		user.setRoles(Set.of(Role.builder().id(3L).name("USER").build()));
		var geniusUserDetail = GeniusSocialUserDetail.create(user);
		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + 600000000);
		var geniusToken = GeniusUserDetailToken.builder().id(geniusUserDetail.getId()).username(geniusUserDetail.getUsername()).password(geniusUserDetail.getProviderUserId()).roles(user.getRoles().stream().map(e-> e.getName()).collect(Collectors.toSet())).build();
		String token = Jwts.builder().setSubject(objectMapper.writeValueAsString(geniusToken)).setIssuedAt(new Date()).setExpiration(expiryDate).signWith(SignatureAlgorithm.HS512, "genius").compact();
		System.out.println(token);
		Claims claims = Jwts.parser().setSigningKey("genius").parseClaimsJws(token).getBody();
		System.out.println(claims.getSubject());
		//var de = objectMapper.readValue(claims.getSubject(), GeniusUserDetailToken.class);
		//System.out.println(de.getId());
	}
}