package com.genius.backend.domain.model.user;

import com.genius.backend.domain.model.auth.Role;
import com.genius.backend.domain.model.alimy.Alimy;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@ToString(exclude = {"alimys"})
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "USERS")
public class User implements Serializable {

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "PROVIDER_USER_ID", unique = true)
	private String providerUserId;

	@Column(name = "PASSWORD")
	private String password;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "USER_NAME")
	private String username;

	@Column(name = "ACCESS_TOKEN")
	private String accessToken;

	@Column(name = "IMAGE_URL")
	private String imageUrl;

	@Column(name = "REFRESH_TOKEN")
	private String refreshToken;

	@Column(name = "EXPIRETIME")
	private long exprietime;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "user")
	private Set<Alimy> alimys = new HashSet<>();

	@ManyToMany
	@JoinTable(name = "USERS_ROLES", joinColumns = @JoinColumn(name = "USER_ID", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "ROLE_ID", referencedColumnName = "ID"))
	private Collection<Role> roles;

	public String getAccessToken() {
		return accessToken;
	}
}