package com.grupo4.topTrend.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.grupo4.topTrend.models.entities.Sponsor;

public interface SponsorRepository
	extends JpaRepository<Sponsor, UUID> {
	Sponsor findOneByCode(UUID code);
	List<Sponsor> findByEventCode(UUID eventCode);
	boolean existsByCodeAndEventCode(UUID code, UUID eventCode);
}
