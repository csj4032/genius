package com.genius.backend.domain.repository;

import com.genius.backend.domain.model.alimy.Alimy;
import com.genius.backend.domain.model.alimy.AlimyStatus;
import com.genius.backend.infrastructure.persistence.annotations.MasterConnection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@MasterConnection
public interface AlimyRepository extends JpaRepository<Alimy, Long> {

	List<Alimy> findByStatus(AlimyStatus alimyStatus);

	@Transactional
	@Modifying
	@Query("DELETE FROM Alimy a WHERE a.id IN :ids")
	int deleteByIds(@Param("ids") List<Long> ids);

	@Transactional
	@Modifying
	@Query("DELETE FROM Alimy a WHERE a.user.id = :id")
	void deleteByUserId(@Param("id") long id);

	Page<Alimy> findByUserId(Long userId, Pageable pageable);
}