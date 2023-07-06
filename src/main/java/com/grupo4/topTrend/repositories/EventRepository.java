package com.grupo4.topTrend.repositories;

import java.sql.Date;
import java.sql.Time;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.grupo4.topTrend.models.entities.Event;

public interface EventRepository
	extends JpaRepository<Event, UUID>{

	Event findOneByCode(UUID code);
	Boolean existsByCodeAndCreatorCode(UUID eventCode, UUID creatorCode);
	Page<Event> findByDateGreaterThanOrDateAndTimeGreaterThan(Date date1, String title1, Date date2, Time time, String title2, Pageable pageable);
	Page<Event> findByDate( Date date, Pageable pageable);// xD
	Page<Event> findByDateGreaterThanAndTitleContainingAndActiveTrueOrDateEqualsAndTimeGreaterThanAndTitleContainingAndActiveTrue(Date date1, String title1, Date date2, Time time, String title2, Pageable pageable);// xD
	Page<Event> findByDateLessThanAndTitleContainingAndActiveTrueOrDateEqualsAndTimeLessThanAndTitleContainingAndActiveTrue(Date date1, String title1, Date date2, Time time, String title2, Pageable pageable);// xD x2
	Page<Event> findByTitleContaining(String title, Pageable pageable);


	@Query("SELECT COUNT(t) FROM Ticket t WHERE t.location.event.code = :eventCode")
	Long countTickets(@Param("eventCode") UUID eventCode);

	
	@Query("SELECT COUNT(q) FROM QrCode q WHERE q.ticket.location.event.code = :eventCode AND q.validationDate IS NOT NULL")
	Long countValidatedQrCodes(@Param("eventCode") UUID eventCode);
}
