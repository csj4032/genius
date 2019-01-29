package com.genius.backend.domain.model.facebook;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class Data implements Serializable {
	private String id;
	private Page page;
}
