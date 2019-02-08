package com.genius.backend.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class NavigationItem {
	private String name;
	private String link;
	private Boolean isScroll;
}