package com.genius.backend.domain.model.log;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class HttpResponseLog {
	private Object data;
}
