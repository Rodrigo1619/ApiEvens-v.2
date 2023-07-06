package com.grupo4.topTrend.services;

import java.util.List;
import java.util.UUID;

import com.grupo4.topTrend.models.entities.Event;
import com.grupo4.topTrend.models.entities.EventXUser;
import com.grupo4.topTrend.models.entities.User;


public interface EventXUserService {

	List<EventXUserService>findByUser(UUID userCode);
	EventXUser findyByCode(UUID code);
	EventXUser findByUserAndEvent(UUID userCode,UUID eventCode);
	Boolean existByUserAndEvent(UUID userCode,UUID eventCode);
	EventXUser createSongXPlaylist(User user, Event event);
}
