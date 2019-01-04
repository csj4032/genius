package com.genius.backend.infrastructure.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;

@Slf4j
public class GeniusListener implements ApplicationListener<GeniusEvent> {

	@Override
	public void onApplicationEvent(GeniusEvent geniusEvent) {
		log.info("{}", geniusEvent);
	}
}