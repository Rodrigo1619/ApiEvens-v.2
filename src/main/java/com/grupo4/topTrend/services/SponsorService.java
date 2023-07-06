package com.grupo4.topTrend.services;

import java.util.List;
import java.util.UUID;

import com.grupo4.topTrend.models.dtos.SaveEventElementDTO;
import com.grupo4.topTrend.models.entities.Event;
import com.grupo4.topTrend.models.entities.Sponsor;

public interface SponsorService {
	void createList(List<SaveEventElementDTO> sponsors, Event event) throws Exception;
	void cudList(List<SaveEventElementDTO> sponsors, Event event) throws Exception;
	Sponsor findOneByCode(UUID code);
	List<Sponsor> findByEventCode(UUID code);
	boolean existsInOtherEvent(UUID code, UUID eventCode);
}
