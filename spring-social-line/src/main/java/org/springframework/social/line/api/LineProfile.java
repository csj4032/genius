package org.springframework.social.line.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class LineProfile extends LineObject {
	private String userId;
	private String displayName;
	private String pictureUrl;
	private String statusMessage;

	public LineProfile(
			@JsonProperty("displayName") String displayName,
			@JsonProperty("userId") String userId,
			@JsonProperty("pictureUrl") String pictureUrl,
			@JsonProperty("statusMessage") String statusMessage) {
		this.displayName = displayName;
		this.userId = userId;
		this.pictureUrl = pictureUrl;
		this.statusMessage = statusMessage;
	}
}
