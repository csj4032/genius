package com.genius.backend.domain.mapper;

import com.genius.backend.domain.model.log.Log;
import com.genius.backend.domain.model.log.LogType;
import com.genius.backend.infrastructure.persistence.annotations.MasterConnection;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@MasterConnection
public interface LogMapper {

	@Select("SELECT ID FROM LOGS WHERE TYPE = 0")
	@Results(value = {@Result(property = "id", column = "ID")})
	List<Log> findByLogType(@Param("logType") LogType logType);
}