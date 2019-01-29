package com.genius.backend.domain.model.facebook;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Message {
	public String mid;
	public int seq;
	public List<Attachment> attachments;
	public String text;
}
