package com.genius.backend.domain.model.log;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Log {
	private long id;
	private LogType type;
	private Class clazz;
	private String value;
}
