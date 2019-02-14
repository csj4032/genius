package com.genius.backend.domain.model.alimy;

import com.cronutils.descriptor.CronDescriptor;
import com.cronutils.model.definition.CronDefinitionBuilder;
import com.cronutils.parser.CronParser;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.genius.backend.application.validation.CronExpressionAnnotation;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import static com.cronutils.model.CronType.QUARTZ;

public class AlimyDto {

	private AlimyDto() {

	}

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
		@Size(min = 1, max = 500, message = "{alimy.requestForSave.validation.message}")
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
	@ToString(callSuper = true)
	@NoArgsConstructor
	@EqualsAndHashCode(callSuper = false)
	public static class RequestForSaveForm extends Request {
		@NotNull
		private long userId;
	}

	@Data
	@ToString(callSuper = true)
	@AllArgsConstructor
	@NoArgsConstructor
	@EqualsAndHashCode(callSuper = false)
	public static class RequestForUpdateForm extends Request {
		private long userId;
		private long alimyId;
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
		private String description;
		private LocalDateTime regDateTime;

		public UnitType getUnitType() {
			return UnitType.builder()
					.minutes(alimyUnit.stream().filter(e -> e.getUnitType().equals(AlimyUnitType.MINUTES)).findFirst().orElseThrow().getUnitValue())
					.hours(alimyUnit.stream().filter(e -> e.getUnitType().equals(AlimyUnitType.HOURS)).findFirst().orElseThrow().getUnitValue())
					.dayOfMonth(alimyUnit.stream().filter(e -> e.getUnitType().equals(AlimyUnitType.DAY_OF_MONTH)).findFirst().orElseThrow().getUnitValue())
					.month(alimyUnit.stream().filter(e -> e.getUnitType().equals(AlimyUnitType.MONTH)).findFirst().orElseThrow().getUnitValue())
					.dayOfWeek(alimyUnit.stream().filter(e -> e.getUnitType().equals(AlimyUnitType.DAY_OF_WEEK)).findFirst().orElseThrow().getUnitValue())
					.year(alimyUnit.stream().filter(e -> e.getUnitType().equals(AlimyUnitType.YEAR)).findFirst().orElseThrow().getUnitValue())
					.build();
		}

		public String getDescription() {
			return cronDescriptor.describe(parser.parse(getUnitType().getCronExpression()));
		}
	}

	@Data
	@Builder
	@ToString
	@AllArgsConstructor
	@NoArgsConstructor
	public static class ResponseForForm {
		private long alimyId;
		private long userId;
		private String username;
		private Integer status;
		private String subject;
		private String message;
		private Set<AlimyUnit> alimyUnit;
		private String minutes;
		private String hours;
		private String dayOfMonth;
		private String month;
		private String dayOfWeek;
		private String year;

		public String getMinutes() {
			return alimyUnit.stream().filter(e -> e.getUnitType().equals(AlimyUnitType.MINUTES)).findFirst().orElseThrow().getUnitValue();
		}

		public String getHour() {
			return alimyUnit.stream().filter(e -> e.getUnitType().equals(AlimyUnitType.HOURS)).findFirst().orElseThrow().getUnitValue();
		}

		public String getDayOfMonth() {
			return alimyUnit.stream().filter(e -> e.getUnitType().equals(AlimyUnitType.DAY_OF_MONTH)).findFirst().orElseThrow().getUnitValue();
		}

		public String getMonth() {
			return alimyUnit.stream().filter(e -> e.getUnitType().equals(AlimyUnitType.MONTH)).findFirst().orElseThrow().getUnitValue();
		}

		public String getDayOfWeek() {
			return alimyUnit.stream().filter(e -> e.getUnitType().equals(AlimyUnitType.DAY_OF_WEEK)).findFirst().orElseThrow().getUnitValue();
		}

		public String getYear() {
			return alimyUnit.stream().filter(e -> e.getUnitType().equals(AlimyUnitType.YEAR)).findFirst().orElseThrow().getUnitValue();
		}
	}

	private static CronParser parser = new CronParser(CronDefinitionBuilder.instanceDefinitionFor(QUARTZ));
	private static CronDescriptor cronDescriptor = CronDescriptor.instance(Locale.KOREA);

	@Data
	@Builder
	@ToString
	@AllArgsConstructor
	@NoArgsConstructor
	public static class UnitType {
		@Builder.Default
		private String seconds = "0";
		@Builder.Default
		@Pattern(message = "기입하신 분 형식에 어긋났습니다.", regexp = "((([0-9]|[0-5][0-9])(-([0-9]|[0-5][0-9]))?,)*([0-9]|[0-5][0-9])(-([0-9]|[0-5][0-9]))?)|(([\\*]|[0-9]|[0-5][0-9])/([0-9]|[0-5][0-9]))|([\\?])|([\\*])")
		private String minutes = "0";
		@Builder.Default
		@Pattern(message = "기입하신 시 형식에 어긋났습니다.", regexp = "((([0-9]|[0-1][0-9]|[2][0-3])(-([0-9]|[0-1][0-9]|[2][0-3]))?,)*([0-9]|[0-1][0-9]|[2][0-3])(-([0-9]|[0-1][0-9]|[2][0-3]))?)|(([\\*]|[0-9]|[0-1][0-9]|[2][0-3])/([0-9]|[0-1][0-9]|[2][0-3]))|([\\?])|([\\*])")
		private String hours = "9-18";
		@Builder.Default
		@Pattern(message = "기입하신 일 형식에 어긋났습니다.", regexp = "((([1-9]|[0][1-9]|[1-2][0-9]|[3][0-1])(-([1-9]|[0][1-9]|[1-2][0-9]|[3][0-1]))?,)*([1-9]|[0][1-9]|[1-2][0-9]|[3][0-1])(-([1-9]|[0][1-9]|[1-2][0-9]|[3][0-1]))?(C)?)|(([1-9]|[0][1-9]|[1-2][0-9]|[3][0-1])/([1-9]|[0][1-9]|[1-2][0-9]|[3][0-1])(C)?)|(L(-[0-9])?)|(L(-[1-2][0-9])?)|(L(-[3][0-1])?)|(LW)|([1-9]W)|([1-3][0-9]W)|([\\?])|([\\*])")
		private String dayOfMonth = "1-30";
		@Builder.Default
		@Pattern(message = "기입하신 월 형식에 어긋났습니다.", regexp = "((([1-9]|0[1-9]|1[0-2])(-([1-9]|0[1-9]|1[0-2]))?,)*([1-9]|0[1-9]|1[0-2])(-([1-9]|0[1-9]|1[0-2]))?)|(([1-9]|0[1-9]|1[0-2])/([1-9]|0[1-9]|1[0-2]))|(((JAN|FEB|MAR|APR|MAY|JUN|JUL|AUG|SEP|OCT|NOV|DEC)(-(JAN|FEB|MAR|APR|MAY|JUN|JUL|AUG|SEP|OCT|NOV|DEC))?,)*(JAN|FEB|MAR|APR|MAY|JUN|JUL|AUG|SEP|OCT|NOV|DEC)(-(JAN|FEB|MAR|APR|MAY|JUN|JUL|AUG|SEP|OCT|NOV|DEC))?)|((JAN|FEB|MAR|APR|MAY|JUN|JUL|AUG|SEP|OCT|NOV|DEC)/(JAN|FEB|MAR|APR|MAY|JUN|JUL|AUG|SEP|OCT|NOV|DEC))|([\\?])|([\\*])")
		private String month = "1-12";
		@Builder.Default
		@Pattern(message = "기입하신 요일 형식에 어긋났습니다.", regexp = "(([1-7](-([1-7]))?,)*([1-7])(-([1-7]))?)|([1-7]/([1-7]))|(((MON|TUE|WED|THU|FRI|SAT|SUN)(-(MON|TUE|WED|THU|FRI|SAT|SUN))?,)*(MON|TUE|WED|THU|FRI|SAT|SUN)(-(MON|TUE|WED|THU|FRI|SAT|SUN))?(C)?)|((MON|TUE|WED|THU|FRI|SAT|SUN)/(MON|TUE|WED|THU|FRI|SAT|SUN)(C)?)|(([1-7]|(MON|TUE|WED|THU|FRI|SAT|SUN))(L|LW)?)|(([1-7]|MON|TUE|WED|THU|FRI|SAT|SUN)#([1-7])?)|([\\?])|([\\*])")
		private String dayOfWeek = "?";
		@Builder.Default
		@NotNull(message = "원하시는 년도를 기입 바랍니다.")
		@Pattern(message = "기입하신 년도가 형식에 어긋났습니다.", regexp = "([\\*])?|((19[7-9][0-9])|(20[0-9][0-9]))?|(((19[7-9][0-9])|(20[0-9][0-9]))/((19[7-9][0-9])|(20[0-9][0-9])))?|((((19[7-9][0-9])|(20[0-9][0-9]))(-((19[7-9][0-9])|(20[0-9][0-9])))?,)*((19[7-9][0-9])|(20[0-9][0-9]))(-((19[7-9][0-9])|(20[0-9][0-9])))?)?")
		private String year = "2019-2020";

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