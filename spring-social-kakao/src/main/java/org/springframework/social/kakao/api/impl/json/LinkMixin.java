package org.springframework.social.kakao.api.impl.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Value
public class LinkMixin {
	@JsonProperty("web_url")
	private String webUrl;
	@JsonProperty("mobile_web_url")
	private String mobileWebUrl;
	@JsonProperty("android_execution_params")
	private String androidExecutionParams;
	@JsonProperty("ios_execution_params")
	private String iosExecutionParams;

	public LinkMixin() {
		this("", "", "", "");
	}

	public LinkMixin(String webUrl, String mobileWebUrl, String androidExecutionParams, String iosExecutionParams) {
		this.webUrl = webUrl;
		this.mobileWebUrl = mobileWebUrl;
		this.androidExecutionParams = androidExecutionParams;
		this.iosExecutionParams = iosExecutionParams;
	}
}