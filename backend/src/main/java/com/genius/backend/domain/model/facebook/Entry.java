package com.genius.backend.domain.model.facebook;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Entry {
	public String id;
	public String time;
	public List<Messaging> messaging;
}
