package com.genius.backend.domain.repository;

import com.genius.backend.domain.model.alimy.AlimyUnit;
import com.genius.backend.infrastructure.persistence.annotations.MasterConnection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@MasterConnection
public interface AlimyUnitRepository extends JpaRepository<AlimyUnit, Long> {

	@Transactional
	@Modifying
	@Query("DELETE FROM AlimyUnit a WHERE a.alimyId = :id")
	void deleteAlimyUnitByAlimyId(@Param("id") long AlimyId);

	@Transactional
	@Modifying
	@Query("DELETE FROM AlimyUnit a WHERE a.alimyId IN :ids")
	void deleteAlimyUnitByAlimyIds(@Param("ids") List<Long> ids);

}