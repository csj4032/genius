package com.genius.backend.domain.model.alimy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.genius.backend.application.validation.CronExpressionAnnotation;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

public class AlimyDto {

	@Data
	@Builder
	@ToString
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Request {
		@NotNull
		private AlimyStatus status;
		@Size(min = 1, max = 100)
		private String subject;
		@Size(min = 1, max = 200, message = "{alimy.requestForSave.validation.message}")
		private String message;
		@Valid
		private UnitType unitType;
	}

	@Data
	@ToString(callSuper = true)
	@AllArgsConstructor
	@NoArgsConstructor
	@EqualsAndHashCode(callSuper = false)
	public static class RequestForSave extends Request {
		@NotNull
		private long userId;

		@Builder(builderMethodName = "RequestForSaveBuilder")
		public RequestForSave(long userId, AlimyStatus status, String subject, String message, UnitType unitType) {
			super(status, subject, message, unitType);
			this.userId = userId;
		}
	}

	@Data
	@ToString(callSuper = true)
	@AllArgsConstructor
	@NoArgsConstructor
	@EqualsAndHashCode(callSuper = false)
	public static class RequestForUpdate extends Request {
		@NotNull
		private long id;

		@Builder(builderMethodName = "RequestForUpdateBuilder")
		public RequestForUpdate(long id, AlimyStatus status, String subject, String message, UnitType unitType) {
			super(status, subject, message, unitType);
			this.id = id;
		}
	}

	@Data
	@Builder
	@ToString
	@AllArgsConstructor
	@NoArgsConstructor
	public static class RequestForDelete {
		private List<Long> ids;
	}

	@Data
	@Builder
	@ToString
	@AllArgsConstructor
	@NoArgsConstructor
	public static class RequestForStatus {
		private long id;
		private AlimyStatus status;
	}

	@Data
	@Builder
	@ToString
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Response {
		private long id;
		private long userId;
		private String username;
		private Integer status;
		private String subject;
		private String message;
		@JsonIgnore
		private Set<AlimyUnit> alimyUnit;
		private UnitType unitType;

		public UnitType getUnitType() {
			return UnitType.builder()
					.minutes(alimyUnit.stream().filter(e -> e.getUnitType().equals(AlimyUnitType.MINUTES)).findFirst().get().getUnitValue())
					.hours(alimyUnit.stream().filter(e -> e.getUnitType().equals(AlimyUnitType.HOURS)).findFirst().get().getUnitValue())
					.dayOfMonth(alimyUnit.stream().filter(e -> e.getUnitType().equals(AlimyUnitType.DAY_OF_MONTH)).findFirst().get().getUnitValue())
					.month(alimyUnit.stream().filter(e -> e.getUnitType().equals(AlimyUnitType.MONTH)).findFirst().get().getUnitValue())
					.dayOfWeek(alimyUnit.stream().filter(e -> e.getUnitType().equals(AlimyUnitType.DAY_OF_WEEK)).findFirst().get().getUnitValue())
					.year(alimyUnit.stream().filter(e -> e.getUnitType().equals(AlimyUnitType.YEAR)).findFirst().get().getUnitValue())
					.build();
		}
	}

	@Data
	@Builder
	@ToString
	@AllArgsConstructor
	@NoArgsConstructor
	public static class UnitType {
		@Builder.Default
		private String seconds = "0";
		@Pattern(regexp = "((([0-9]|[0-5][0-9])(-([0-9]|[0-5][0-9]))?,)*([0-9]|[0-5][0-9])(-([0-9]|[0-5][0-9]))?)|(([\\*]|[0-9]|[0-5][0-9])/([0-9]|[0-5][0-9]))|([\\?])|([\\*])")
		private String minutes;
		@Pattern(regexp = "((([0-9]|[0-1][0-9]|[2][0-3])(-([0-9]|[0-1][0-9]|[2][0-3]))?,)*([0-9]|[0-1][0-9]|[2][0-3])(-([0-9]|[0-1][0-9]|[2][0-3]))?)|(([\\*]|[0-9]|[0-1][0-9]|[2][0-3])/([0-9]|[0-1][0-9]|[2][0-3]))|([\\?])|([\\*])")
		private String hours;
		@Pattern(regexp = "((([1-9]|[0][1-9]|[1-2][0-9]|[3][0-1])(-([1-9]|[0][1-9]|[1-2][0-9]|[3][0-1]))?,)*([1-9]|[0][1-9]|[1-2][0-9]|[3][0-1])(-([1-9]|[0][1-9]|[1-2][0-9]|[3][0-1]))?(C)?)|(([1-9]|[0][1-9]|[1-2][0-9]|[3][0-1])/([1-9]|[0][1-9]|[1-2][0-9]|[3][0-1])(C)?)|(L(-[0-9])?)|(L(-[1-2][0-9])?)|(L(-[3][0-1])?)|(LW)|([1-9]W)|([1-3][0-9]W)|([\\?])|([\\*])")
		private String dayOfMonth;
		@Pattern(regexp = "((([1-9]|0[1-9]|1[0-2])(-([1-9]|0[1-9]|1[0-2]))?,)*([1-9]|0[1-9]|1[0-2])(-([1-9]|0[1-9]|1[0-2]))?)|(([1-9]|0[1-9]|1[0-2])/([1-9]|0[1-9]|1[0-2]))|(((JAN|FEB|MAR|APR|MAY|JUN|JUL|AUG|SEP|OCT|NOV|DEC)(-(JAN|FEB|MAR|APR|MAY|JUN|JUL|AUG|SEP|OCT|NOV|DEC))?,)*(JAN|FEB|MAR|APR|MAY|JUN|JUL|AUG|SEP|OCT|NOV|DEC)(-(JAN|FEB|MAR|APR|MAY|JUN|JUL|AUG|SEP|OCT|NOV|DEC))?)|((JAN|FEB|MAR|APR|MAY|JUN|JUL|AUG|SEP|OCT|NOV|DEC)/(JAN|FEB|MAR|APR|MAY|JUN|JUL|AUG|SEP|OCT|NOV|DEC))|([\\?])|([\\*])")
		private String month;
		@Pattern(regexp = "(([1-7](-([1-7]))?,)*([1-7])(-([1-7]))?)|([1-7]/([1-7]))|(((MON|TUE|WED|THU|FRI|SAT|SUN)(-(MON|TUE|WED|THU|FRI|SAT|SUN))?,)*(MON|TUE|WED|THU|FRI|SAT|SUN)(-(MON|TUE|WED|THU|FRI|SAT|SUN))?(C)?)|((MON|TUE|WED|THU|FRI|SAT|SUN)/(MON|TUE|WED|THU|FRI|SAT|SUN)(C)?)|(([1-7]|(MON|TUE|WED|THU|FRI|SAT|SUN))(L|LW)?)|(([1-7]|MON|TUE|WED|THU|FRI|SAT|SUN)#([1-7])?)|([\\?])|([\\*])")
		private String dayOfWeek;
		@NotNull(message = "원하시는 년도를 기입 바랍니다.")
		@Pattern(message = "기입하신 년도가 형식에 어긋났습니다.", regexp = "([\\*])?|((19[7-9][0-9])|(20[0-9][0-9]))?|(((19[7-9][0-9])|(20[0-9][0-9]))/((19[7-9][0-9])|(20[0-9][0-9])))?|((((19[7-9][0-9])|(20[0-9][0-9]))(-((19[7-9][0-9])|(20[0-9][0-9])))?,)*((19[7-9][0-9])|(20[0-9][0-9]))(-((19[7-9][0-9])|(20[0-9][0-9])))?)?")
		private String year;

		@JsonIgnore
		@CronExpressionAnnotation
		public String getCronExpression() {
			return new StringBuilder("0 ")
					.append(getMinutes()).append(" ")
					.append(getHours()).append(" ")
					.append(getDayOfMonth()).append(" ")
					.append(getMonth()).append(" ")
					.append(getDayOfWeek()).append(" ")
					.append(getYear()).toString();
		}
	}

	@Data
	@Builder
	@ToString
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Search {
		private long id;
		private long userId;
		private AlimyStatus status;
		@Builder.Default
		private String username = "";
		@Builder.Default
		private String subject = "";
		@Builder.Default
		private String message = "";
	}
}