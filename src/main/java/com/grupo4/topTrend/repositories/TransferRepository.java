package com.grupo4.topTrend.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.grupo4.topTrend.models.entities.Transfer;


public interface TransferRepository extends JpaRepository<Transfer, UUID> {
	Transfer findOneByCode(UUID code);
	Transfer findOneByNewOwnerCodeAndTicketCode(UUID newOwnerCode, UUID ticketCode);
	
}
