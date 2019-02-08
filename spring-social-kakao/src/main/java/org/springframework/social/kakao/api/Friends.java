package org.springframework.social.kakao.api;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Friends {
	private List<Elements> elements;
	private Integer total_count;
}
