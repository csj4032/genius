package com.genius.backend.infrastructure.security.social;

import com.genius.backend.domain.model.user.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.social.security.SocialUserDetails;

import java.util.Collection;
import java.util.Collections;

import static java.util.stream.Collectors.toList;

public class GeniusSocialUserDetail implements SocialUserDetails {

	private static final long serialVersionUID = 5197941260523577515L;

	private User user;
	private Collection<? extends GrantedAuthority> authorities;

	public GeniusSocialUserDetail(User user) {
		this.user = user;
	}

	public GeniusSocialUserDetail(User user, Collection<? extends GrantedAuthority> authorities) {
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

	public static GeniusSocialUserDetail create(User user) {
		Collection<GrantedAuthority> authorities = Collections.unmodifiableCollection(user.getRoles().stream().map(e -> new SimpleGrantedAuthority("ROLE_" + e.getName())).collect(toList()));
		return new GeniusSocialUserDetail(user, authorities);
	}

	public static GeniusSocialUserDetail create(GeniusUserDetailToken geniusUserDetailToken) {
		Collection<GrantedAuthority> authorities = Collections.unmodifiableCollection(geniusUserDetailToken.getRoles().stream().map(e -> new SimpleGrantedAuthority("ROLE_" + e)).collect(toList()));
		var user = new User();
		user.setId(geniusUserDetailToken.getId());
		user.setProviderUserId(geniusUserDetailToken.getPassword());
		user.setPassword(geniusUserDetailToken.getPassword());
		user.setUsername(geniusUserDetailToken.getUsername());
		return new GeniusSocialUserDetail(user, authorities);
	}
}
