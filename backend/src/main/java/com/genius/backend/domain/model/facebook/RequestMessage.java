package com.genius.backend.domain.model.facebook;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class RequestMessage {
	private Recipient recipient;
	private Message message;
}
