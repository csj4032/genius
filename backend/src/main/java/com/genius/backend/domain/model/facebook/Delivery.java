package com.genius.backend.domain.model.facebook;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Delivery {
	public List<String> mids;
	public String watermark;
	public Integer seq;
}
