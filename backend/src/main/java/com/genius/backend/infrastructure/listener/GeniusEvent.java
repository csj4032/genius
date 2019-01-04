package com.genius.backend.infrastructure.listener;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.time.LocalDateTime;

@Getter
@Setter
public class GeniusEvent extends ApplicationEvent {

	private LocalDateTime eventTime;

	public GeniusEvent(Object source, LocalDateTime eventTime) {
		super(source);
		this.eventTime = eventTime;
	}
}