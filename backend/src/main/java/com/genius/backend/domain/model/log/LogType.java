package com.genius.backend.domain.model.log;

import com.genius.backend.domain.converter.ConvertedEnum;
import com.genius.backend.domain.converter.ReverseEnumResolver;
import lombok.Getter;

@Getter
public enum LogType implements ConvertedEnum<Integer> {

	HTTP_REQUEST(0, "httpRequest", HttpRequestLog.class),
	HTTP_RESPONSE(1, "httpResponse", HttpResponseLog.class);

	private Integer code;
	private String name;
	private Class clazz;

	LogType(Integer code, String name, Class clazz) {
		this.code = code;
		this.name = name;
		this.clazz = clazz;
	}

	@Override
	public Integer toDbValue() {
		return code;
	}

	public Integer getDbValue() {
		return code;
	}

	public static final ReverseEnumResolver<LogType, Integer> dbValueResolver = new ReverseEnumResolver<>(LogType.class, LogType::getDbValue);

	public static LogType fromDbValue(Integer dbValue) {
		return dbValueResolver.get(dbValue);
	}
}