package com.grupo4.topTrend.services;

import java.util.List;
import java.util.UUID;

import com.grupo4.topTrend.models.dtos.SaveLocationDTO;
import com.grupo4.topTrend.models.entities.Event;
import com.grupo4.topTrend.models.entities.Location;

public interface LocationService {
	List<Location> createList(List<SaveLocationDTO> locations, Event event) throws Exception;	List<Location> updateList(List<SaveLocationDTO> locations, Event event) throws Exception;
	void deleteList(List<Location> locations, Event event) throws Exception;
	Location findOneByCode(UUID code);
	Location findByCode(UUID code);
	List<Location> findByEventCode(UUID code);
	boolean existsInOtherEvent(UUID code, UUID eventCode);
}
