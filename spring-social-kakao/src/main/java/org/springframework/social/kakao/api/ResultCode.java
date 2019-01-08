package org.springframework.social.kakao.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ResultCode {

	@JsonProperty("result_code")
	int resultCode;
}
