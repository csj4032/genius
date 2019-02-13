package com.genius.backend.domain.model.alimy;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.genius.backend.domain.model.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ALIMY_UNITS")
public class AlimyUnit extends BaseEntity implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	//@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	//@JoinColumn(name = "ALIMY_ID", nullable = false)
	//@JsonBackReference
	@Column(name = "ALIMY_ID")
	private Long alimyId;

	@Convert(converter = AlimyUnitTypeAttributeConverter.class)
	@Column(name = "UNIT_TYPE")
	private AlimyUnitType unitType;

	@Column(name = "UNIT_VALUE")
	private String unitValue;

	public void setUnitValue(AlimyDto.UnitType unitType) {
		if (this.getUnitType().equals(AlimyUnitType.SECONDS)) this.setUnitValue(unitType.getSeconds());
		if (this.getUnitType().equals(AlimyUnitType.MINUTES)) this.setUnitValue(unitType.getMinutes());
		if (this.getUnitType().equals(AlimyUnitType.HOURS)) this.setUnitValue(unitType.getHours());
		if (this.getUnitType().equals(AlimyUnitType.DAY_OF_MONTH)) this.setUnitValue(unitType.getDayOfMonth());
		if (this.getUnitType().equals(AlimyUnitType.MONTH)) this.setUnitValue(unitType.getMonth());
		if (this.getUnitType().equals(AlimyUnitType.DAY_OF_WEEK)) this.setUnitValue(unitType.getDayOfWeek());
		if (this.getUnitType().equals(AlimyUnitType.YEAR)) this.setUnitValue(unitType.getYear());
	}

	public void setUnitValue(String unitValue) {
		this.unitValue = unitValue;
	}
}