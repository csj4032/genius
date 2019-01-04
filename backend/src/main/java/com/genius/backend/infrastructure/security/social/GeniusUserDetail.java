package com.genius.backend.infrastructure.security.social;

import com.genius.backend.domain.model.user.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.social.security.SocialUserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class GeniusUserDetail implements SocialUserDetails {

	private static final long serialVersionUID = 5197941260523577515L;

	private User user;
	private Collection<? extends GrantedAuthority> authorities;

	public GeniusUserDetail(User user) {
		this.user = user;
	}

	public GeniusUserDetail(User user, Collection<? extends GrantedAuthority> authorities) {
		this.user = user;
		this.authorities = authorities;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return user.getProviderUserId();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	public String getProviderUserId() {
		return user.getProviderUserId();
	}

	public long getId() {
		return user.getId();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public String getUserId() {
		return Long.toString(user.getId());
	}

	public static GeniusUserDetail create(User user) {
		List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
		return new GeniusUserDetail(user, authorities);
	}
}
