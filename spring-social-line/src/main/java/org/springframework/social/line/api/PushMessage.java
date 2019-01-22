package org.springframework.social.line.api;

public class PushMessage {
	private String[] to;
	private TextMessage[] textMessages;

	public String[] getTo() {
		return to;
	}

	public void setTo(String[] to) {
		this.to = to;
	}

	public TextMessage[] getTextMessages() {
		return textMessages;
	}

	public void setTextMessages(TextMessage[] textMessages) {
		this.textMessages = textMessages;
	}
}