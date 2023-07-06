package com.grupo4.topTrend.Controllers;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.grupo4.topTrend.models.dtos.EventAnalisysDTO;
import com.grupo4.topTrend.models.dtos.PageDTO;
import com.grupo4.topTrend.models.dtos.SaveEventDTO;
import com.grupo4.topTrend.models.dtos.ShowEventDTO;
import com.grupo4.topTrend.models.dtos.ShowEventCardDTO;
import com.grupo4.topTrend.models.entities.Category;
import com.grupo4.topTrend.models.entities.Event;
import com.grupo4.topTrend.models.entities.Location;
import com.grupo4.topTrend.models.entities.User;
import com.grupo4.topTrend.services.CategoryService;
import com.grupo4.topTrend.services.EventService;
import com.grupo4.topTrend.services.InvolvedService;
import com.grupo4.topTrend.services.LocationService;
import com.grupo4.topTrend.services.SponsorService;
import com.grupo4.topTrend.services.TicketService;
import com.grupo4.topTrend.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/event")
@CrossOrigin("*")
public class EventController {
	@Autowired
	EventService eventService;
	@Autowired
	UserService userService;
	@Autowired
	CategoryService categoryService;
	@Autowired
	InvolvedService involvedService;
	@Autowired
	SponsorService sponsorService;
	@Autowired
	LocationService locationService;
	@Autowired
	TicketService ticketService;
	
	@PostMapping("/save")
	@PreAuthorize("hasAuthority('ROLE_ORGANIZATOR')")
	public ResponseEntity<?> register(@RequestBody @Valid SaveEventDTO eventInfo, BindingResult validations){
		if(validations.hasErrors()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		try {
			User owner = userService.findUserAuthenticated();
			Category category = categoryService.findByCode(eventInfo.getCategoryCode());
			Event oldEvent = eventService.findBycode(eventInfo.getCode());
			
			if(category == null)
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			
			if(eventInfo.getCode()!=null) {
				if(oldEvent == null)
					return new ResponseEntity<>(HttpStatus.NOT_FOUND);
				
				if(!oldEvent.getCreator().getCode().equals(owner.getCode()))
					return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
				
				if(eventInfo.getActive() == null)
					eventInfo.setActive(oldEvent.getActive());
			}
			
			Event event = eventService.save(eventInfo, owner, category);
			boolean didUpdate = false;
			if(eventInfo.getCode()!=null)
				didUpdate = eventInfo.getCode().equals(event.getCode());
			
			if(didUpdate) {//Basicamente comprueba si guardo o actualizo
			
			//Basicamente comprueba si guardo o actualizo
				involvedService.cudList(eventInfo.getInvolveds(), event);
				sponsorService.cudList(eventInfo.getSponsors(), event);
				
				List<Location> oldList = locationService.findByEventCode(event.getCode());
				List<Location> newList = locationService.updateList(eventInfo.getLocations(), event);
				
				List<Location> toDelete = oldList.stream()
						.filter( l -> !newList.contains(l)).collect(Collectors.toList());
				

				//TODO antes de eliminar localidades hay que implementar logica de los tickets que quedaran huerfanos
				//Por el momento sera eliminar todos
				ticketService.deleteByLocations(toDelete);
				
				locationService.deleteList( 
						oldList.stream()
							.filter( l -> !newList.contains(l))
							.collect(Collectors.toList())
						, event);
			} else {
				involvedService.createList(eventInfo.getInvolveds(), event);
				sponsorService.createList(eventInfo.getSponsors(), event);
				locationService.createList(eventInfo.getLocations(), event);
			}
			
			return new ResponseEntity<>(event, HttpStatus.OK);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/locations/{eventCode}")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public ResponseEntity<?>findTickets( @PathVariable(name="eventCode") UUID eventCode){
		
		
		if(!eventService.existByCode(eventCode))
			return new ResponseEntity<>( HttpStatus.NOT_FOUND);

		List<Location> locations = locationService.findByEventCode(eventCode);
		return new ResponseEntity<>(locations,HttpStatus.OK);
		
	}

	@GetMapping("/{eventCode}")
	public ResponseEntity<?>findOne( @PathVariable(name="eventCode") UUID eventCode){
		
		ShowEventDTO showEvent = eventService.showEvent(eventCode);
		
		if(showEvent == null)
			return new ResponseEntity<>( HttpStatus.NOT_FOUND);
		
		return new ResponseEntity<>(showEvent,HttpStatus.OK);
		
	}
	

	@GetMapping("/public/nextevents")
	public ResponseEntity<?>findNexts(
			@RequestParam(defaultValue="") String title,
			@RequestParam(defaultValue="0") Integer page,
			@RequestParam(defaultValue="20") Integer size){
		
		PageDTO<ShowEventCardDTO> events = eventService.findNextsByTitle(title, PageRequest.of(page, size));
		
		return new ResponseEntity<>(events,HttpStatus.OK);
		
	}
	
	@GetMapping("/public/prevevents")
	public ResponseEntity<?>findPrevs(
			@RequestParam(defaultValue="") String title,
			@RequestParam(defaultValue="0") Integer page,
			@RequestParam(defaultValue="20") Integer size){
		
		PageDTO<ShowEventCardDTO> events = eventService.findPrevsByTitle(title, PageRequest.of(page, size));
		
		return new ResponseEntity<>(events,HttpStatus.OK);
		
	}
	
	@PatchMapping("/toggleActive/{eventCode}")
	@PreAuthorize("hasAuthority('ROLE_ORGANIZATOR')")
	public ResponseEntity<?> toggleActive(@PathVariable(name="eventCode") UUID eventCode){
		
		Boolean changeTo = eventService.toggleActive(eventCode);
		
		if(changeTo == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		return new ResponseEntity<>(changeTo, HttpStatus.OK);
	}
	

	@GetMapping("/dashboard/{eventCode}")
	@PreAuthorize("hasAuthority('ROLE_ANALYST')")
	public ResponseEntity<?> getStadistics(@PathVariable(name="eventCode") UUID eventCode){
		
		EventAnalisysDTO e = eventService.getStadistics(eventCode);
		
		if(e==null)
			return new ResponseEntity<>( HttpStatus.NOT_FOUND);
		
		return new ResponseEntity<>(e, HttpStatus.OK);
	}
}


