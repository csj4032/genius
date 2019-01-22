package com.genius.backend.domain.model.user

import com.genius.backend.domain.model.BaseEntity
import com.genius.backend.domain.model.alimy.Alimy
import com.genius.backend.domain.model.auth.Role
import javax.persistence.*

@Entity
@Table(name = "USERS")
data class User(
		@Id
		@Column(name = "ID")
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		var id: Long = 0,

		@Column(name = "PROVIDER_ID")
		var providerId: String = "",

		@Column(name = "PROVIDER_USER_ID")
		var providerUserId: String = "",

		@Column(name = "PASSWORD")
		var password: String? = "",

		@Column(name = "EMAIL")
		var email: String? = "",

		@Column(name = "USER_NAME")
		var username: String = "",

		@Column(name = "ACCESS_TOKEN")
		var accessToken: String = "",

		@Column(name = "IMAGE_URL")
		var imageUrl: String? = "",

		@Column(name = "REFRESH_TOKEN")
		var refreshToken: String? = "",

		@Column(name = "EXPIRED_TIME")
		var expiredTime: Long = 0,

		@Column(nullable = true)
		@OneToMany(fetch = FetchType.EAGER, mappedBy = "user", targetEntity = Alimy::class)
		var alimys: Set<Alimy>? = null,

		@ManyToMany(targetEntity = Role::class, fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
		@JoinTable(name = "USERS_ROLES",
				joinColumns = [JoinColumn(name = "USER_ID", referencedColumnName = "ID")],
				inverseJoinColumns = [JoinColumn(name = "ROLE_ID", referencedColumnName = "ID")])
		var roles: Set<Role>? = null
) : BaseEntity() {
	constructor() : this(0, "", "", "", "", "", "", "", "", 0, null, null)
}