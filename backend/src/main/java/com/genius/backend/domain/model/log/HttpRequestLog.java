package com.genius.backend.domain.model.log;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class HttpRequestLog {
	private String path;
	private String remoteIp;
	private String platformType;
	private String deviceType;
}
