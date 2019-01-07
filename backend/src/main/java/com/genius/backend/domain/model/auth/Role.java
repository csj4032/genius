package com.genius.backend.domain.model.auth;

import com.genius.backend.domain.model.user.User;
import lombok.*;

import javax.persistence.*;
import java.util.Collection;

@Getter
@Setter
@Entity
@Table(name = "ROLES")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@Column(name = "NAME")
	private String name;

	@ManyToMany(mappedBy = "roles")
	private Collection<User> users;

	@ManyToMany
	@JoinTable(name = "ROLES_PRIVILEGES", joinColumns = @JoinColumn(name = "ROLE_ID", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "PRIVILEGE_ID", referencedColumnName = "ID"))
	private Collection<Privilege> privileges;
}