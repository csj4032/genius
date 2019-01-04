package com.genius.backend.infrastructure.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

@Getter
@Setter
@ConfigurationProperties(prefix = "my")
@PropertySource(value = "classpath:random.properties")
public class RandomProperties {
	private int secret;
	private int number;
	private int bignumber;
	private int uuid;
}
