package com.genius.backend.domain.model.alimy;

import com.fasterxml.jackson.annotation.JsonValue;
import com.genius.backend.application.exception.UnknownEnumValueException;
import com.genius.backend.domain.converter.ConvertedEnum;
import com.genius.backend.domain.converter.ReverseEnumResolver;
import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

@Getter
public enum AlimyStatus implements ConvertedEnum<Integer> {
	STOP(0, "stop"),
	START(1, "start");

	private Integer status;
	private String text;

	AlimyStatus(Integer status, String text) {
		this.status = status;
		this.text = text;
	}

	public static AlimyStatus of(Integer value) {
		if (null == value) return null;
		Optional<AlimyStatus> alimyStatus = Arrays.stream(AlimyStatus.values()).filter(e -> e.getStatus().equals(value)).findFirst();
		if (alimyStatus.isPresent()) return alimyStatus.get();
		throw new UnknownEnumValueException("AlimyStatus: unknown value: " + value);
	}

	@Override
	public Integer toDbValue() {
		return status;
	}

	@JsonValue
	public String getTest(){
		return text;
	}

	public Integer getDbValue() {
		return status;
	}

	public String getStrValue() {
		return text;
	}

	public static final ReverseEnumResolver<AlimyStatus, Integer> dbValueResolver = new ReverseEnumResolver<>(AlimyStatus.class, AlimyStatus::getDbValue);

	public static AlimyStatus fromDbValue(Integer dbValue) {
		return dbValueResolver.get(dbValue);
	}

	public static final ReverseEnumResolver<AlimyStatus, String> strResolver = new ReverseEnumResolver<>(AlimyStatus.class, AlimyStatus::getStrValue);

	public static AlimyStatus fromStrValue(String value) {
		return strResolver.get(value);
	}
}

