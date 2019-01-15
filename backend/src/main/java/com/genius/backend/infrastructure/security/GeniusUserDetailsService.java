package com.genius.backend.infrastructure.security;

import com.genius.backend.domain.model.user.User;
import com.genius.backend.domain.repository.UserRepository;
import com.genius.backend.infrastructure.security.social.GeniusUserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.ResourceNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class GeniusUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public final GeniusUserDetail loadUserByUsername(String id) throws UsernameNotFoundException {
		User user = userRepository.findById(Long.valueOf(id)).orElseThrow(() -> new UsernameNotFoundException("User not found with Id : " + id));
		return GeniusUserDetail.create(user);
	}

	public GeniusUserDetail loadUserByUserId(Long id) {
		return GeniusUserDetail.create(userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.valueOf(id), "User not found with Id : " + id)));
	}
}