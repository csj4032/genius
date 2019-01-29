package com.genius.backend.domain.model.facebook;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ResponseMessage {
	public String object;
	public List<Entry> entry;
}
