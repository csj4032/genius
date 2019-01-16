package com.genius.backend.infrastructure.security.social;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;

public class GeniusSocialUserDetailService implements SocialUserDetailsService {

	private UserDetailsService userDetailsService;

	public GeniusSocialUserDetailService(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	@Override
	public SocialUserDetails loadUserByUserId(String userId) {
		UserDetails userDetails = userDetailsService.loadUserByUsername(userId);
		return (GeniusSocialUserDetail) userDetails;
	}
}