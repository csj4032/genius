package com.genius.backend.domain.model.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.genius.backend.domain.model.user.User;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Getter
@Setter
@Entity
@Builder
@ToString(exclude = {"users", "privileges"})
@Table(name = "ROLES")
@AllArgsConstructor
@NoArgsConstructor
public class Role implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@Column(name = "NAME")
	private String name;

	@JsonIgnore
	@ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER)
	private Collection<User> users;

	@JsonIgnore
	@ManyToMany
	@JoinTable(name = "ROLES_PRIVILEGES", joinColumns = @JoinColumn(name = "ROLE_ID", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "PRIVILEGE_ID", referencedColumnName = "ID"))
	private Collection<Privilege> privileges;
}