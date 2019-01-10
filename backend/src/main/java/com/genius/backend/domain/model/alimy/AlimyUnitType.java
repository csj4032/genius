package com.genius.backend.domain.model.alimy;

import com.genius.backend.domain.converter.ConvertedEnum;
import com.genius.backend.domain.converter.ReverseEnumResolver;
import lombok.Getter;

@Getter
public enum AlimyUnitType implements ConvertedEnum<String> {
	SECONDS("S", "seconds"),
	MINUTES("MM", "minutes"),
	HOURS("H", "hours"),
	DAY_OF_MONTH("DOM", "day of month"),
	MONTH("M", "month"),
	YEAR("Y", "year"),
	DAY_OF_WEEK("DOW", "day of week");

	private String type;
	private String text;

	AlimyUnitType(String type, String text) {
		this.type = type;
		this.text = text;
	}

	@Override
	public String toDbValue() {
		return type;
	}

	public String getDbValue() {
		return type;
	}

	public static final ReverseEnumResolver<AlimyUnitType, String> dbValueResolver = new ReverseEnumResolver<>(AlimyUnitType.class, AlimyUnitType::getDbValue);

	public static AlimyUnitType fromDbValue(String dbValue) {
		return dbValueResolver.get(dbValue);
	}
}