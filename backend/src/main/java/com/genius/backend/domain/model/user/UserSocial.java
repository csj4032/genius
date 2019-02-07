package com.genius.backend.domain.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.genius.backend.domain.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@Entity
@ToString(exclude = {"user"})
@Table(name = "USERS_SOCIAL")
public class UserSocial extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private long id;

	@JsonIgnore
	@OneToOne(fetch = FetchType.LAZY)
	private User user;

	@Column(name = "ACCESS_TOKEN")
	private String accessToken;

	@Column(name = "REFRESH_TOKEN")
	private String refreshToken;

	@Column(name = "EXPIRED_TIME")
	private long expiredTime;
}