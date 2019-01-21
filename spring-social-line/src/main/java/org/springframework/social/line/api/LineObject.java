package org.springframework.social.line.api;

import java.util.HashMap;
import java.util.Map;

public class LineObject {

	private Map<String, Object> extraData;

	public LineObject() {
		this.extraData = new HashMap<String, Object>();
	}

	public Map<String, Object> getExtraData() {
		return extraData;
	}

	protected void add(String key, Object value) {
		extraData.put(key, value);
	}
}
