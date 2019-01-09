package com.genius.backend.domain.model.log;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class HttpRequestLog {
	private String path;
	private String remoteAddr;
	private String os;
	private String device;
	private String browser;
	private Map<String, String[]> params;
}
