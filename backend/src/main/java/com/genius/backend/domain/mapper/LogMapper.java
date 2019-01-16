package com.genius.backend.domain.mapper;

import com.genius.backend.domain.model.log.Log;
import com.genius.backend.infrastructure.persistence.annotations.MasterConnection;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@MasterConnection
public interface LogMapper {

	@Select("SELECT ID FROM LOGS")
	List<Log> findAll();
}