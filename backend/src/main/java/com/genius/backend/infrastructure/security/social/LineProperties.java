package com.genius.backend.infrastructure.security.social;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.social.autoconfigure.SocialProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "spring.social.line")
public class LineProperties extends SocialProperties {
}