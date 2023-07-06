package com.grupo4.topTrend.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.grupo4.topTrend.models.entities.Token;
import com.grupo4.topTrend.models.entities.User;

public interface TokenRepository
	extends JpaRepository<Token, UUID> {

	List<Token> findByUserAndActive(User user, Boolean active);
	List<Token> findByActive(Boolean active);
}
