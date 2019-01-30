package com.genius.backend.domain.model.facebook;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Messaging implements Serializable {
	public Sender sender;
	public Recipient recipient;
	public String timeStamp;
	public Message message;
	public Postback postback;
	public Delivery delivery;

	@JsonCreator
	public Messaging(
			@JsonProperty("sender") Sender sender,
			@JsonProperty("recipient") Recipient recipient,
			@JsonProperty("timeStamp") String timeStamp,
			@JsonProperty("message") Message message,
			@JsonProperty("postback") Postback postback,
			@JsonProperty("delivery") Delivery delivery) {
		this.sender = sender;
		this.recipient = recipient;
		this.timeStamp = timeStamp;
		this.message = message;
		this.postback = postback;
		this.delivery = delivery;
	}

	public Messaging() {
	}
}
