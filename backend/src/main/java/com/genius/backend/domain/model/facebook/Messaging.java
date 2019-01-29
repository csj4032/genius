package com.genius.backend.domain.model.facebook;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Messaging {
	public Sender sender;
	public Recipient recipient;
	public String timeStamp;
	public Message message;
	public Postback postback;
	public Delivery delivery;
}
