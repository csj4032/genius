package com.genius.backend.domain.model.alimy;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
public class Alimy extends BaseEntity implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

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
	private Set<AlimyUnit> alimyUnit = new HashSet<>();

	public void setAlimyUnit(AlimyDto.UnitType unitType) {
		if (alimyUnit.isEmpty()) {
			AlimyUnit seconds = AlimyUnit.builder().alimy(this).unitType(AlimyUnitType.SECONDS).unitValue(unitType.getSeconds()).build();
			AlimyUnit minutes = AlimyUnit.builder().alimy(this).unitType(AlimyUnitType.MINUTES).unitValue(unitType.getMinutes()).build();
			AlimyUnit hours = AlimyUnit.builder().alimy(this).unitType(AlimyUnitType.HOURS).unitValue(unitType.getHours()).build();
			AlimyUnit dayOfMonth = AlimyUnit.builder().alimy(this).unitType(AlimyUnitType.DAY_OF_MONTH).unitValue(unitType.getDayOfMonth()).build();
			AlimyUnit month = AlimyUnit.builder().alimy(this).unitType(AlimyUnitType.MONTH).unitValue(unitType.getMonth()).build();
			AlimyUnit dayOfWeek = AlimyUnit.builder().alimy(this).unitType(AlimyUnitType.DAY_OF_WEEK).unitValue(unitType.getDayOfWeek()).build();
			AlimyUnit year = AlimyUnit.builder().alimy(this).unitType(AlimyUnitType.YEAR).unitValue(unitType.getYear()).build();
			this.alimyUnit = Set.of(seconds, minutes, hours, dayOfMonth, month, dayOfWeek, year);
		} else {
			this.alimyUnit.stream().forEach(e-> {
				if(e.getUnitType().equals(AlimyUnitType.SECONDS)) e.setUnitValue(unitType.getSeconds());
				if(e.getUnitType().equals(AlimyUnitType.MINUTES)) e.setUnitValue(unitType.getMinutes());
				if(e.getUnitType().equals(AlimyUnitType.HOURS)) e.setUnitValue(unitType.getHours());
				if(e.getUnitType().equals(AlimyUnitType.DAY_OF_MONTH)) e.setUnitValue(unitType.getDayOfMonth());
				if(e.getUnitType().equals(AlimyUnitType.MONTH)) e.setUnitValue(unitType.getMonth());
				if(e.getUnitType().equals(AlimyUnitType.DAY_OF_WEEK)) e.setUnitValue(unitType.getDayOfWeek());
				if(e.getUnitType().equals(AlimyUnitType.YEAR)) e.setUnitValue(unitType.getYear());
			});
		}
	}

	public String getUsername() {
		return this.getUser().getUsername();
	}

	public String getYear() {
		return getUnitOfUnitType(e -> e.getUnitType().equals(AlimyUnitType.YEAR));
	}

	public String getDayOfWeek() {
		return getUnitOfUnitType(e -> e.getUnitType().equals(AlimyUnitType.DAY_OF_WEEK));
	}

	public String getMonth() {
		return getUnitOfUnitType(e -> e.getUnitType().equals(AlimyUnitType.MONTH));
	}

	public String getDayOfMonth() {
		return getUnitOfUnitType(e -> e.getUnitType().equals(AlimyUnitType.DAY_OF_MONTH));
	}

	public String getHours() {
		return getUnitOfUnitType(e -> e.getUnitType().equals(AlimyUnitType.HOURS));
	}

	public String getMinutes() {
		return getUnitOfUnitType(e -> e.getUnitType().equals(AlimyUnitType.MINUTES));
	}

	public String getUnitOfUnitType(Predicate<AlimyUnit> predicate) {
		return alimyUnit.stream().filter(predicate).findFirst().orElseThrow().getUnitValue();
	}

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