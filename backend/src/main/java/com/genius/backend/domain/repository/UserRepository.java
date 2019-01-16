package com.genius.backend.domain.repository;

import com.genius.backend.domain.model.user.User;
import com.genius.backend.infrastructure.persistence.annotations.MasterConnection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@MasterConnection
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findById(Long id);

	Optional<User> findByProviderUserId(String providerUserId);

}