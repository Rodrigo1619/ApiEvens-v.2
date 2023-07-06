package com.grupo4.topTrend.services.implementations;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grupo4.topTrend.models.entities.Event;
import com.grupo4.topTrend.models.entities.EventXUser;
import com.grupo4.topTrend.models.entities.User;
import com.grupo4.topTrend.repositories.EventXUserRepository;
import com.grupo4.topTrend.services.EventXUserService;

@Service
public class EventXServiceServiceImpl implements EventXUserService {
	@Autowired
	EventXUserRepository eventXServiceRepository;

	@Override
	public List<EventXUserService> findByUser(UUID userCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EventXUser findyByCode(UUID code) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EventXUser findByUserAndEvent(UUID userCode, UUID eventCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean existByUserAndEvent(UUID userCode, UUID eventCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EventXUser createSongXPlaylist(User user, Event event) {
		// TODO Auto-generated method stub
		return null;
	}
}
