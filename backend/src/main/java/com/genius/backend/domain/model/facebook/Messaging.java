package com.genius.backend.domain.model.facebook;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Messaging implements Serializable {
	public Sender sender;
	public Recipient recipient;
	public String timeStamp;
	public Message message;
	public Postback postback;
	public Delivery delivery;
}
