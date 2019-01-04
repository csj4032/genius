package com.genius.backend.infrastructure.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "genius")
public class GeniusProperties {
	private String name;
	private int age;
}