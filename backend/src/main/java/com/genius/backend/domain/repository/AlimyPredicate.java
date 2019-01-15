package com.genius.backend.domain.repository;

import com.genius.backend.domain.model.alimy.AlimyDto;
import com.genius.backend.domain.model.alimy.QAlimy;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

public class AlimyPredicate {

	private AlimyPredicate() {
		throw new IllegalStateException("Utility class");
	}

	public static Predicate search(AlimyDto.Search search) {

		QAlimy alimy = QAlimy.alimy;
		BooleanBuilder builder = new BooleanBuilder();

		if (search.getId() != 0) {
			builder.and(alimy.id.eq(search.getId()));
		}

		if (search.getUserId() != 0) {
			builder.and(alimy.user.id.eq(search.getUserId()));
		}

		if(!search.getUsername().isEmpty()) {
			builder.and(alimy.user.username.like(search.getUsername()));
		}

		if (!search.getSubject().isEmpty()) {
			builder.and(alimy.subject.contains(search.getSubject()));
		}

		if (!search.getMessage().isEmpty()) {
			builder.and(alimy.message.contains(search.getMessage()));
		}

		if (search.getStatus() != null) {
			builder.and(alimy.status.eq(search.getStatus()));
		}

		return builder;
	}
}