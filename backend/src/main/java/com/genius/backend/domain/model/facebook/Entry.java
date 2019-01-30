package com.genius.backend.domain.model.facebook;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class Entry {
	public String id;
	public String time;
	public List<Messaging> messaging;
}
