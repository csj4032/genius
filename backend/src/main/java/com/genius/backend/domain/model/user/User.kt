package com.genius.backend.domain.model.user

import com.genius.backend.domain.model.alimy.Alimy
import com.genius.backend.domain.model.auth.Role
import javax.persistence.*

@Entity
@Table(name = "USER")
data class User(
		@Id
		@Column(name = "ID")
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		var id: Long,

		@Column(name = "PROVIDER_USER_ID", unique = true)
		var providerUserId: String,

		@Column(name = "PASSWORD")
		var password: String,

		@Column(name = "EMAIL")
		var email: String,

		@Column(name = "USER_NAME")
		var username: String,

		@Column(name = "ACCESS_TOKEN")
		var accessToken: String,

		@Column(name = "IMAGE_URL")
		var imageUrl: String,

		@Column(name = "REFRESH_TOKEN")
		var refreshToken: String,

		@Column(name = "EXPIRED_TIME")
		var expiredTime: Long,

		@Column(nullable = true)
		@OneToMany(fetch = FetchType.LAZY, mappedBy = "user", targetEntity = Alimy::class)
		var alimys: Set<Alimy>? = null,

		@ManyToMany(targetEntity = Role::class)
		@JoinTable(name = "USERS_ROLES",
				joinColumns = [JoinColumn(name = "USER_ID", referencedColumnName = "ID")],
				inverseJoinColumns = [JoinColumn(name = "ROLE_ID", referencedColumnName = "ID")])
		var roles: Set<Role>? = null
) {
	constructor() : this(0, "", "", "", "", "", "", "", 0, null, null)
}