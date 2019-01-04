package com.genius.backend.domain.model;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Unit {
	String text;
	String name;
	String value;
}
