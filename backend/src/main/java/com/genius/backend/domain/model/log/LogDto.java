package com.genius.backend.domain.model.log;

import lombok.*;

public class LogDto {

	@Getter
	@Setter
	@ToString
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Response {
		private long id;
		private LogType type;
		private Object value;
	}
}