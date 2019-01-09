package com.genius.backend.domain.repository;

import com.genius.backend.domain.model.log.Log;
import com.genius.backend.infrastructure.persistence.annotations.MasterConnection;
import org.springframework.data.jpa.repository.JpaRepository;

@MasterConnection
public interface LogRepository extends JpaRepository<Log, Long> {

}