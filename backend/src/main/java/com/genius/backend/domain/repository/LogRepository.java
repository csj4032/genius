package com.genius.backend.domain.repository;

import com.genius.backend.domain.model.log.Log;
import com.genius.backend.domain.model.log.LogType;
import com.genius.backend.infrastructure.persistence.annotations.MasterConnection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.List;

@MasterConnection
public interface LogRepository extends JpaRepository<Log, Long> {

	Log findTop1ByTypeOrderByIdDesc(LogType type);
}