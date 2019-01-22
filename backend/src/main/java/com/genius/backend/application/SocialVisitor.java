package com.genius.backend.application;


import org.springframework.social.connect.Connection;
import org.springframework.social.kakao.api.Kakao;
import org.springframework.social.line.api.Line;

public interface SocialVisitor {

	void process(Connection<?> connection);

	void process(Kakao kakao);

	void process(Line line);
}