package com.genius.backend.infrastructure.security;

import com.genius.backend.domain.model.user.User;
import com.genius.backend.domain.repository.UserRepository;
import com.genius.backend.infrastructure.security.social.GeniusSocialUserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GeniusUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public final GeniusSocialUserDetail loadUserByUsername(String providerUserId) throws UsernameNotFoundException {
		Optional<User> user = userRepository.findByProviderUserId(providerUserId);
		if (user.isPresent()) return GeniusSocialUserDetail.create(user.get());
		return null;
	}

	public GeniusSocialUserDetail loadUserByUserId(Long id) {
		return GeniusSocialUserDetail.create(userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.valueOf(id), "User not found with Id : " + id)));
	}
}