package com.genius.backend.interfaces.kakao.talk

import lombok.extern.slf4j.Slf4j
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.social.kakao.api.ResultCode
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@Slf4j
@RestController
class TalkController {

	@PostMapping("/send/me")
	fun sendMe(message: String): ResponseEntity<ResultCode> {
		return ResponseEntity(null, HttpStatus.OK)
	}
}