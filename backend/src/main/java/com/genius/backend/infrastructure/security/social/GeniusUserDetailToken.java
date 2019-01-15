package com.genius.backend.infrastructure.security.social;

import com.genius.backend.domain.model.auth.Role;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GeniusUserDetailToken {
	private long id;
	private String username;
	private String password;
	private Set<String> roles;
}
