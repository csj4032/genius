package com.genius.backend.domain.model.log;

public enum LogType {

	HTTP_REQUEST("httpRequest");

	private String name;
	private Class clazz;

	LogType(String name) {
		this.name = name;
	}

	public void setClazz(Class clazz) {
		this.clazz = clazz;
	}
}
