package com.genius.backend.domain.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public class BaseEntity implements Serializable {

	@CreatedDate
	@Column(name = "REG_DATETIME", updatable = false)
	private LocalDateTime regDateTime;

	@LastModifiedDate
	@Column(name = "MOD_DATETIME", updatable = true)
	private LocalDateTime modDateTime;
}
