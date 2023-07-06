package com.grupo4.topTrend.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import com.grupo4.topTrend.models.entities.Location;

public interface LocationRepository
	extends JpaRepository<Location, UUID> {
	Location findOneByCode(UUID code);
	List<Location> findByEventCode(UUID eventCode);
	boolean existsByCodeAndEventCode(UUID code, UUID eventCode);
}
