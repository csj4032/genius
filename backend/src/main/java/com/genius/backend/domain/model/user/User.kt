package com.genius.backend.domain.model.user

import com.genius.backend.application.ProviderType
import com.genius.backend.domain.model.BaseEntity
import com.genius.backend.domain.model.alimy.Alimy
import com.genius.backend.domain.model.auth.Role
import com.genius.backend.domain.model.log.LogTypeAttributeConverter
import javax.persistence.*

@Entity
@Table(name = "USERS")
data class User(
		@Id
		@Column(name = "ID")
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		var id: Long = 0,

		@Convert(converter = ProviderTypeAttributeConverter::class)
		@Column(name = "PROVIDER_TYPE")
		var providerType: ProviderType,

		@Column(name = "PROVIDER_USER_ID")
		var providerUserId: String = "",

		@Column(name = "PASSWORD")
		var password: String? = "",

		@Column(name = "EMAIL")
		var email: String? = "",

		@Column(name = "USER_NAME")
		var username: String = "",

		@Column(name = "IMAGE_URL")
		var imageUrl: String? = "",

		@OneToOne(cascade = [CascadeType.ALL], fetch = FetchType.LAZY, mappedBy = "user")
		@JoinColumn(name = "USER_ID")
		var userSocial: UserSocial? = null,

		@Column(nullable = true)
		@OneToMany(fetch = FetchType.EAGER, mappedBy = "user", targetEntity = Alimy::class)
		var alimys: Set<Alimy>? = null,

		@ManyToMany(targetEntity = Role::class, fetch = FetchType.EAGER)
		@JoinTable(name = "USERS_ROLES",
				joinColumns = [JoinColumn(name = "USER_ID", referencedColumnName = "ID")],
				inverseJoinColumns = [JoinColumn(name = "ROLE_ID", referencedColumnName = "ID")])
		var roles: Set<Role>? = null
) : BaseEntity() {
	constructor() : this(0, ProviderType.KAKAO, "", "", "", "", "", UserSocial(), null, null)
}