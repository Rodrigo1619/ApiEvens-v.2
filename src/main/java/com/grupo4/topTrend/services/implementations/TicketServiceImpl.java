package com.grupo4.topTrend.services.implementations;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.grupo4.topTrend.models.dtos.PageDTO;
import com.grupo4.topTrend.models.dtos.ShowTicketDTO;
import com.grupo4.topTrend.models.entities.Event;
import com.grupo4.topTrend.models.entities.Location;
import com.grupo4.topTrend.models.entities.Ticket;
import com.grupo4.topTrend.models.entities.User;
import com.grupo4.topTrend.repositories.TicketRepository;
import com.grupo4.topTrend.services.EventService;
import com.grupo4.topTrend.services.TicketService;
import com.grupo4.topTrend.services.UserService;

import jakarta.transaction.Transactional;

@Service
public class TicketServiceImpl implements TicketService {
	@Autowired
	TicketRepository ticketRepository;
	@Autowired
	UserService userService;

	@Override
	public Ticket setOwner(UUID ticketCode, UUID userCode, String transferContent) {
		Ticket ticket = ticketRepository.findOneByCode(ticketCode);
	    if (ticket == null) 
	        return null;
	    
	    User newOwner = userService.findByCode(userCode);
	    if (newOwner == null) 
	        return null;
	    
	    
	    ticket.setUser(newOwner);
	    return ticketRepository.save(ticket);
	}

	@Override
	public PageDTO<Ticket> findByLocation(UUID locationCode) {
		return null;
	}

	@Override
	public PageDTO<Ticket> findByEvent(UUID EventCode) {
		// TODO Auto-generated method stub
		return null;
	}

	

	@Override
	public Boolean belongsTo(UUID ticketCode, UUID ownerCode) {
		Ticket t = findByCode(ticketCode);
		if(t == null)
			return false;
		
		return t.getUser().getCode() == ownerCode;
	}

	@Override
	public Ticket findByUserCode(UUID code) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Ticket create(User user, Location location) throws Exception{
		
		Ticket ticket = new Ticket(
				5,
				location,
				user
				);
		
		return ticketRepository.save(ticket);
	}

	@Override
	public Long countTicketByLocation(UUID location) {
		return ticketRepository.countByLocationCode(location);
	}

	@Override
	@Transactional
	public void deleteByLocations(List<Location> locations) {
		locations.forEach( l -> ticketRepository.deleteByLocationCode(l.getCode()));		
	}

	@Override
	public ShowTicketDTO findTicketById(UUID Ticketcode) {
		if(Ticketcode == null)
			return null;
		
		Ticket ticket = findById(Ticketcode);
		
		
		if(ticket == null)
			return null;
		
		Event event = ticket.getLocation().getEvent();
		
		ShowTicketDTO tickets = new ShowTicketDTO(
				event.getCode(),
				event.getTitle(),
				event.getDate(),
				event.getTime(),
				ticket.getLocation().getLocation(),
				ticket.getLocation().getPrice()
				);
		
		return tickets;
	}


	@Override
	public Ticket findById(UUID code) {
		return ticketRepository.findOneByCode(code);
	}

	@Override
	public PageDTO<Ticket> findByUser(String identifier, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PageDTO<ShowTicketDTO> findByUser(UUID userCode, String title ,Pageable pageable) {
		
		Page<Ticket> ticket = ticketRepository.findByUserCode(userCode, pageable);
		
		List<ShowTicketDTO> tickets = ticket.getContent().stream().map(t -> new ShowTicketDTO(
				t.getCode(),
				t.getLocation().getEvent().getTitle(),
				t.getLocation().getEvent().getDate(),
				t.getLocation().getEvent().getTime(),
				t.getLocation().getLocation(),
				t.getLocation().getPrice()
				))
				.collect(Collectors.toList());
		
		PageDTO<ShowTicketDTO> newLisOfTickets = new PageDTO<ShowTicketDTO>(tickets.stream().filter(t -> t.getEventTitle().contains(title))
														.collect(Collectors.toList()), 
														ticket.getNumber(), ticket.getSize(), ticket.getTotalElements(), 
														ticket.getTotalPages());

		
		
		return newLisOfTickets;
	}

	@Override
	public Ticket findByCode(UUID code) {
		return ticketRepository.findOneByCode(code);
	}

	

	

	
}
