package com.genius.backend.application.impl;

import com.genius.backend.application.SocialVisitor;
import org.springframework.social.connect.Connection;
import org.springframework.social.kakao.api.Kakao;
import org.springframework.social.line.api.Line;

public class SocialSignInVisitor implements SocialVisitor {

	@Override
	public void process(Connection<?> connection) {
		if(connection instanceof Kakao) {
			process((Kakao) connection);
		} else {
			process((Line) connection);
		}
	}

	@Override
	public void process(Kakao kakao) {

	}

	@Override
	public void process(Line line) {

	}
}