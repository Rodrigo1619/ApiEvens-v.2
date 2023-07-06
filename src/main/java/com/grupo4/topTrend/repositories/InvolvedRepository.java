package com.grupo4.topTrend.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.grupo4.topTrend.models.entities.Involved;

public interface InvolvedRepository
	extends JpaRepository<Involved, UUID> {
	Involved findOneByCode(UUID code);
	List<Involved> findByEventCode(UUID eventCode);
	boolean existsByCodeAndEventCode(UUID code, UUID eventCode);
}
