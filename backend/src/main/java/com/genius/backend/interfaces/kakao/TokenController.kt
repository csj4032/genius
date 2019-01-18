package com.genius.backend.interfaces.kakao

import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.social.kakao.api.AccessTokenInfo
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate

@RestController
class TokenController() {

	val url = "https://kapi.kakao.com/v1/user/access_token_info"

	@GetMapping("/token/check")
	fun tokenExpireCheck(accessToken: String): String {
		val restTemplate = RestTemplate()
		val headers = HttpHeaders()
		headers.set("Authorization", "Bearer ${accessToken}")
		val entity = HttpEntity<HttpHeaders>(headers)
		val responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, AccessTokenInfo::class.java)
		return responseEntity.statusCode.name
	}
}