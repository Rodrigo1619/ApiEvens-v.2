package com.grupo4.topTrend.repositories;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.grupo4.topTrend.models.entities.QrCode;

public interface QrCodeRepository 
extends JpaRepository<QrCode, UUID> {
	QrCode findOneByCode(UUID code);
	QrCode findOneByTicketCodeAndValidationDateIsNotNull(UUID code);
	List<QrCode> findByTicketCodeAndExpirationDateGreaterThan(UUID code, Timestamp expirationDate);
	
}
