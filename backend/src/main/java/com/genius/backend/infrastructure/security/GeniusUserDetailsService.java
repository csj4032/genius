package com.genius.backend.infrastructure.security;

import com.genius.backend.domain.repository.UserRepository;
import com.genius.backend.infrastructure.security.social.GeniusSocialUserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class GeniusUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public final GeniusSocialUserDetail loadUserByUsername(String providerUserId) {
		var user = userRepository.findByProviderUserId(providerUserId).orElseThrow(() -> new UsernameNotFoundException(providerUserId));
		return GeniusSocialUserDetail.create(user);
	}
}