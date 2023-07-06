package com.grupo4.topTrend.services.implementations;

import java.sql.Date;
import java.sql.Time;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.grupo4.topTrend.models.dtos.EventAnalisysDTO;
import com.grupo4.topTrend.models.dtos.PageDTO;
import com.grupo4.topTrend.models.dtos.SaveEventDTO;
import com.grupo4.topTrend.models.dtos.ShowEventDTO;
import com.grupo4.topTrend.models.dtos.ShowEventCardDTO;
import com.grupo4.topTrend.models.entities.Category;
import com.grupo4.topTrend.models.entities.Event;
import com.grupo4.topTrend.models.entities.User;
import com.grupo4.topTrend.repositories.CategoryRepository;
import com.grupo4.topTrend.repositories.EventRepository;
import com.grupo4.topTrend.repositories.InvolvedRepository;
import com.grupo4.topTrend.repositories.LocationRepository;
import com.grupo4.topTrend.repositories.SponsorRepository;
import com.grupo4.topTrend.services.EventService;

@Service
public class EventServiceImpl implements EventService{
	@Autowired
	EventRepository eventRepository;

	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	InvolvedRepository involvedRepository;
	
	@Autowired
	SponsorRepository sponsorRepository;

	@Autowired
	LocationRepository locationRepository;

	@Override
	public Event save(SaveEventDTO saveInfo, User user, Category category) throws Exception {
		System.out.println("/////////////////////////////////////////////////////////////");
		System.out.println(saveInfo.getDate());
		Event e = new Event(
			saveInfo.getTitle(),
			saveInfo.getImg(),
			saveInfo.getDuration(),
			saveInfo.getTime(),
			saveInfo.getDate(),
			saveInfo.getActive(),
			category,
			user);
		
		if(saveInfo.getCode() != null)
			e.setCode(saveInfo.getCode());
		
		eventRepository.save(e);
		System.out.println(e.getDate());
		System.out.println(new java.util.Date());
		return e;
	}

	@Override
	public PageDTO<Event> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PageDTO<ShowEventCardDTO> findNextsByTitle(String title, Pageable pageable) {
		Date now = new Date(System.currentTimeMillis());
		Time nowTime = new Time(System.currentTimeMillis());
		System.out.println(now);
		System.out.println(nowTime);
			
		Page<Event> events = eventRepository.findByDateGreaterThanAndTitleContainingAndActiveTrueOrDateEqualsAndTimeGreaterThanAndTitleContainingAndActiveTrue(now, title, now, nowTime, title, pageable);
		//Page<Event> events = eventRepository.findByDate( now, pageable);
		
		return new PageDTO<ShowEventCardDTO>(
				events.getContent().stream().map(
						e -> new ShowEventCardDTO(
								e.getCode(),
								e.getTitle(),
								e.getTime(),
								e.getDate(),
								e.getDuration(),
								e.getCategory().getCategory()))
					.collect(Collectors.toList()),
				events.getNumber(),
				events.getSize(),
				events.getTotalElements(),
				events.getTotalPages());
	}

	@Override
	public PageDTO<ShowEventCardDTO> findPrevsByTitle(String title, Pageable pageable) {
		Date now = new Date(System.currentTimeMillis());
		Time nowTime = new Time(System.currentTimeMillis());
		System.out.println(now);
		System.out.println(nowTime);
		Page<Event> events = eventRepository.findByDateLessThanAndTitleContainingAndActiveTrueOrDateEqualsAndTimeLessThanAndTitleContainingAndActiveTrue(now, title, now, nowTime, title, pageable);
		
		return new PageDTO<ShowEventCardDTO>(
				events.getContent().stream().map(
						e -> new ShowEventCardDTO(
								e.getCode(),
								e.getTitle(),
								e.getTime(),
								e.getDate(),
								e.getDuration(),
								e.getCategory().getCategory()))
					.collect(Collectors.toList()),
				events.getNumber(),
				events.getSize(),
				events.getTotalElements(),
				events.getTotalPages());
	}

	@Override
	public PageDTO<ShowEventCardDTO> findByUserAndTitle(String identifier, String title, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean existByCode(UUID eventCode) {
		
		return eventRepository.existsById(eventCode);
	}

	@Override
	public Event findBycode(UUID code) {
		return eventRepository.findOneByCode(code);
	}

	@Override
	public Boolean isOwnedBy(UUID eventCode, UUID userCode) {
		return eventRepository.existsByCodeAndCreatorCode(eventCode, userCode);
	}
	
	@Override
	public Boolean toggleActive(UUID code) {
		Event event = eventRepository.findOneByCode(code);
		if(event == null)
			return null;
		
		event.setActive(!event.getActive());
		eventRepository.save(event);
		return event.getActive();
	}

	@Override
	public ShowEventDTO showEvent(UUID eventCode) {
		Event event = eventRepository.findOneByCode(eventCode);
		
		if(event == null)
			return null;
		
		return new ShowEventDTO(
				event.getCode(),
				event.getTitle(),
				event.getTime(),
				event.getDate(),
				event.getCategory().getCategory(),
				event.getInvolveds().stream().map(i-> i.getInvolved()).collect(Collectors.toList()),
				event.getSponsors().stream().map(s-> s.getSponsor()).collect(Collectors.toList()),
				event.getLocations());
	}

	@Override
	public EventAnalisysDTO getStadistics(UUID eventCode) {
		
		Event event = eventRepository.findOneByCode(eventCode);
		
		if(event == null)
			return null;
		
		Long ticketSold = eventRepository.countTickets(eventCode);
		Float assistPer =100* (float) (eventRepository.countValidatedQrCodes(eventCode))/(float)(ticketSold);
		
		EventAnalisysDTO e = new EventAnalisysDTO(
				eventCode,
				event.getTitle(),
				ticketSold,
				assistPer,
				null,
				null,
				null,
				null);
				
		return e;
	}
}
