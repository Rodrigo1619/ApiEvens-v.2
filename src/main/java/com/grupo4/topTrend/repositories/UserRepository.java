package com.grupo4.topTrend.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.grupo4.topTrend.models.entities.User;

public interface UserRepository
	extends JpaRepository<User, UUID> {

	User findOneByCode(UUID code);
	List<User> findByUsername(String username);
	User findOneByUsernameOrEmail(String username, String email);
	List<User> findByUsernameOrEmail(String username, String email);
	List<User> findByUsernameContainingOrEmailContaining(String username, String email);
	Page<User> findByActiveAndEmailContainingOrActiveAndUsernameContaining(Boolean active1, String email, Boolean active2, String username, Pageable pageable);
	Boolean existsByCode(UUID code);
}
