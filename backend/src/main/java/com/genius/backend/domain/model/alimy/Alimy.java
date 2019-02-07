package com.genius.backend.domain.model.alimy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.genius.backend.application.ProviderType;
import com.genius.backend.domain.model.BaseEntity;
import com.genius.backend.domain.model.user.User;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"user"})
@Entity
@Table(name = "ALIMY")
@JsonInclude(JsonInclude.Include.CUSTOM)
@JsonDeserialize
public class Alimy extends BaseEntity implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "USER_ID", nullable = false)
	private User user;

	@Column(name = "STATUS")
	private AlimyStatus status;

	@Column(name = "SUBJECT")
	private String subject;

	@Column(name = "MESSAGE")
	private String message;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "alimy", fetch = FetchType.EAGER)
	@JsonManagedReference
	@Builder.Default
	private Set<AlimyUnit> alimyUnit = new HashSet<>();

	public void setAlimyUnit(AlimyDto.UnitType unitType) {
		if (alimyUnit.isEmpty()) {
			unitTypeUnPivot(alimyUnit, unitType, this);
		} else {
			unitTypePivot(alimyUnit, unitType);
		}
	}

	private void unitTypeUnPivot(Set<AlimyUnit> alimyUnit, AlimyDto.UnitType unitType, Alimy alimy) {
		AlimyUnit seconds = AlimyUnit.builder().alimy(alimy).unitType(AlimyUnitType.SECONDS).unitValue(unitType.getSeconds()).build();
		AlimyUnit minutes = AlimyUnit.builder().alimy(alimy).unitType(AlimyUnitType.MINUTES).unitValue(unitType.getMinutes()).build();
		AlimyUnit hours = AlimyUnit.builder().alimy(alimy).unitType(AlimyUnitType.HOURS).unitValue(unitType.getHours()).build();
		AlimyUnit dayOfMonth = AlimyUnit.builder().alimy(alimy).unitType(AlimyUnitType.DAY_OF_MONTH).unitValue(unitType.getDayOfMonth()).build();
		AlimyUnit month = AlimyUnit.builder().alimy(alimy).unitType(AlimyUnitType.MONTH).unitValue(unitType.getMonth()).build();
		AlimyUnit dayOfWeek = AlimyUnit.builder().alimy(alimy).unitType(AlimyUnitType.DAY_OF_WEEK).unitValue(unitType.getDayOfWeek()).build();
		AlimyUnit year = AlimyUnit.builder().alimy(alimy).unitType(AlimyUnitType.YEAR).unitValue(unitType.getYear()).build();
		alimyUnit.addAll(Set.of(seconds, minutes, hours, dayOfMonth, month, dayOfWeek, year));
	}

	private void unitTypePivot(Set<AlimyUnit> alimyUnit, AlimyDto.UnitType unitType) {
		alimyUnit.stream().forEach(e -> {
			if (e.getUnitType().equals(AlimyUnitType.SECONDS)) e.setUnitValue(unitType.getSeconds());
			if (e.getUnitType().equals(AlimyUnitType.MINUTES)) e.setUnitValue(unitType.getMinutes());
			if (e.getUnitType().equals(AlimyUnitType.HOURS)) e.setUnitValue(unitType.getHours());
			if (e.getUnitType().equals(AlimyUnitType.DAY_OF_MONTH)) e.setUnitValue(unitType.getDayOfMonth());
			if (e.getUnitType().equals(AlimyUnitType.MONTH)) e.setUnitValue(unitType.getMonth());
			if (e.getUnitType().equals(AlimyUnitType.DAY_OF_WEEK)) e.setUnitValue(unitType.getDayOfWeek());
			if (e.getUnitType().equals(AlimyUnitType.YEAR)) e.setUnitValue(unitType.getYear());
		});
	}

	@JsonIgnore
	public String getUsername() {
		return this.getUser().getUsername();
	}

	@JsonIgnore
	public String getYear() {
		return getUnitOfUnitType(e -> e.getUnitType().equals(AlimyUnitType.YEAR));
	}

	@JsonIgnore
	public String getDayOfWeek() {
		return getUnitOfUnitType(e -> e.getUnitType().equals(AlimyUnitType.DAY_OF_WEEK));
	}

	@JsonIgnore
	public String getMonth() {
		return getUnitOfUnitType(e -> e.getUnitType().equals(AlimyUnitType.MONTH));
	}

	@JsonIgnore
	public String getDayOfMonth() {
		return getUnitOfUnitType(e -> e.getUnitType().equals(AlimyUnitType.DAY_OF_MONTH));
	}

	@JsonIgnore
	public String getHours() {
		return getUnitOfUnitType(e -> e.getUnitType().equals(AlimyUnitType.HOURS));
	}

	@JsonIgnore
	public String getMinutes() {
		return getUnitOfUnitType(e -> e.getUnitType().equals(AlimyUnitType.MINUTES));
	}

	@JsonIgnore
	public String getUnitOfUnitType(Predicate<AlimyUnit> predicate) {
		return alimyUnit.stream().filter(predicate).findFirst().orElseThrow().getUnitValue();
	}

	@JsonIgnore
	public String getCronExpression() {
		return new StringBuilder("0 ")
				.append(getMinutes()).append(" ")
				.append(getHours()).append(" ")
				.append(getDayOfMonth()).append(" ")
				.append(getMonth()).append(" ")
				.append(getDayOfWeek()).append(" ")
				.append(getYear()).toString();
	}

	@JsonIgnore
	public ProviderType getProviderType() {
		return getUser().getProviderType();
	}
}