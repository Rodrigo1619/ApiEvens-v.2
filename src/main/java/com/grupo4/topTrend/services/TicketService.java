package com.grupo4.topTrend.services;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Pageable;

import com.grupo4.topTrend.models.dtos.PageDTO;
import com.grupo4.topTrend.models.dtos.ShowTicketDTO;
import com.grupo4.topTrend.models.entities.Location;
import com.grupo4.topTrend.models.entities.Ticket;
import com.grupo4.topTrend.models.entities.User;

public interface TicketService {
	Ticket setOwner(UUID ticketCode, UUID userCode, String transferContent);
	Ticket findByUserCode(UUID code);
	Ticket create(User user, Location location) throws Exception;
	Ticket findByCode(UUID code);
	PageDTO<ShowTicketDTO> findByUser(UUID userCode,String title, Pageable pageable);
	Ticket findById(UUID code);
	ShowTicketDTO findTicketById(UUID Ticketcode);
	Long countTicketByLocation(UUID location);
	PageDTO<Ticket> findByLocation(UUID locationCode);
	PageDTO<Ticket> findByEvent(UUID EventCode);
	PageDTO<Ticket> findByUser(String identifier, Pageable pageable);
	void deleteByLocations(List<Location> locations);
	Boolean belongsTo(UUID ticketCode, UUID ownerCode);
	
}
