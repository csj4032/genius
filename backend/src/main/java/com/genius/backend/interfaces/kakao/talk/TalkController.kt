package com.genius.backend.interfaces.kakao.talk

import com.genius.backend.infrastructure.security.social.GeniusSocialUserDetail
import lombok.extern.slf4j.Slf4j
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.social.kakao.api.ResultCode
import org.springframework.social.kakao.api.impl.KakaoTemplate
import org.springframework.social.kakao.api.talkTemplate.LinkObject
import org.springframework.social.kakao.api.talkTemplate.TextObject
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@Slf4j
@RestController
class TalkController {

	@PostMapping("/send/me")
	fun sendMe(message: String): ResponseEntity<ResultCode> {
		val authentication = SecurityContextHolder.getContext().authentication
		val geniusUserDetail = authentication.details as GeniusSocialUserDetail
		val kakaoTemplate = KakaoTemplate(geniusUserDetail.getUser().accessToken)
		var textObject = TextObject.builder().text(message).build()
		return ResponseEntity(kakaoTemplate.talkOperation().sendTalk(textObject), HttpStatus.OK)
	}
}