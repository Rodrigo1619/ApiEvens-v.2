package com.grupo4.topTrend.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.grupo4.topTrend.models.entities.EventXUser;

public interface EventXUserRepository
	extends JpaRepository<EventXUser, UUID>{
	EventXUser findOneByCode(UUID code);
}
