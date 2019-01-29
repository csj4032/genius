package com.genius.backend.domain.model.facebook;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Delivery {
	public List<String> mids;
	public String watermark;
	public int seq;
}
