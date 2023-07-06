package com.grupo4.topTrend.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.grupo4.topTrend.models.entities.Ticket;

public interface TicketRepository
	extends JpaRepository<Ticket, UUID> {
	Ticket findOneByCode(UUID code);
	Ticket findByUserCode(UUID code);
	
	
	//List<Ticket> findByEventTitle(String eventTitle);
	Long countByLocationCode(UUID location);
	@Modifying
    @Query("DELETE FROM Ticket t WHERE t.location.code = :locationCode")
    void deleteByLocationCode(@Param("locationCode") UUID locationCode);
	Page<Ticket> findByLocationCode(UUID code, Pageable pageable);
	Page<Ticket> findByUserCode(UUID code, Pageable pageable);
	Page<Ticket> findByUserUsernameOrUserEmail(String username, String email, Pageable pageable);
	List<Ticket> findByLocationEventCode(UUID eventCode);
}
