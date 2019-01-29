package com.genius.backend.domain.model.facebook;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class IdsForPagesResponse {
	private String id;
	@JsonProperty("ids_for_pages")
	private IdsForPages idsForPages;
}
