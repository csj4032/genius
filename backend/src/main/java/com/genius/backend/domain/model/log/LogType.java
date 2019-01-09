package com.genius.backend.domain.model.log;

import com.genius.backend.domain.converter.ConvertedEnum;
import com.genius.backend.domain.converter.ReverseEnumResolver;
import lombok.Getter;

@Getter
public enum LogType implements ConvertedEnum<Integer> {

	HTTP_REQUEST(0, "httpRequest", HttpRequestLog.class, LogBindType.JOIN_POINT),
	HTTP_RESPONSE(1, "httpResponse", HttpResponseLog.class, LogBindType.PROCEEDING_JOIN_POINT);

	private Integer code;
	private String name;
	private Class clazz;
	private LogBindType logBindType;

	LogType(Integer code, String name, Class clazz, LogBindType logBindType) {
		this.code = code;
		this.name = name;
		this.clazz = clazz;
		this.logBindType = logBindType;
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