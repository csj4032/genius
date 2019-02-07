package com.genius.backend.domain.model.auth;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Getter
@Setter
@Entity
@Table(name = "PRIVILEGES")
public class Privilege implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long id;

	@Column(name = "NAME")
	private String name;

	@ManyToMany(mappedBy = "privileges")
	private Collection<Role> roles;
}
