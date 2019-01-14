package com.genius.backend.infrastructure.security;

import com.genius.backend.domain.model.user.User;
import com.genius.backend.infrastructure.security.social.GeniusUserDetail;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.Test;

import java.util.Date;

public class JwtTokenProviderTest {

	@Test
	public void generateTokenTest() {
		var user = new User();
		user.setId(1);
		var geniusUserDetail = new GeniusUserDetail(user);
		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + 6000000);
		String jwt = Jwts.builder().setSubject(Long.toString(geniusUserDetail.getId())).setIssuedAt(new Date()).setExpiration(expiryDate).signWith(SignatureAlgorithm.HS512, "genius").compact();
		System.out.println(jwt);
	}
}