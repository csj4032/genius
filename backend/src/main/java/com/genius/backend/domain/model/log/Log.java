package com.genius.backend.domain.model.log;

import com.genius.backend.domain.model.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "LOGS")
@Entity
public class Log extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private long id;

	@Convert(converter = LogTypeAttributeConverter.class)
	@Column(name = "TYPE")
	private LogType type;

	@Column(name = "VALUE")
	private String value;
}