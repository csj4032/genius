package com.genius.backend.domain.model.facebook;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class ResponseMessage {
	public String object;
	public List<Entry> entry;
}
