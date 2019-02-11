package org.springframework.social.kakao.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class Friends {
	private List<Elements> elements;
	@JsonProperty("total_count")
	private Integer totalCount;
	@JsonProperty("before_url")
	private String beforeUrl;
	@JsonProperty("after_url")
	private String afterUrl;
}