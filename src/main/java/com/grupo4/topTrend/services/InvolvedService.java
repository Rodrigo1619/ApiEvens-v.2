package com.grupo4.topTrend.services;

import java.util.List;
import java.util.UUID;

import com.grupo4.topTrend.models.dtos.SaveEventElementDTO;
import com.grupo4.topTrend.models.entities.Event;
import com.grupo4.topTrend.models.entities.Involved;

public interface InvolvedService {
	void createList(List<SaveEventElementDTO> involveds, Event event) throws Exception;;
	void cudList(List<SaveEventElementDTO> involveds, Event event) throws Exception;;
	Involved findOneByCode(UUID code);
	List<Involved> findByEventCode(UUID code);
	boolean existsInOtherEvent(UUID code, UUID eventCode);
}
