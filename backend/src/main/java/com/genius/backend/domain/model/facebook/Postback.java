package com.genius.backend.domain.model.facebook;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Postback {
	public Action payload;
}
