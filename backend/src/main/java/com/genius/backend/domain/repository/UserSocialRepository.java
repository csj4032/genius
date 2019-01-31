package com.genius.backend.domain.repository;

import com.genius.backend.domain.model.user.UserSocial;
import com.genius.backend.infrastructure.persistence.annotations.MasterConnection;
import org.springframework.data.jpa.repository.JpaRepository;

@MasterConnection
public interface UserSocialRepository extends JpaRepository<UserSocial, Long> {


}