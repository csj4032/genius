package com.genius.backend.domain.model.facebook;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class IdsForPages implements Serializable {
	private List<Data> data = new ArrayList<>();
}
