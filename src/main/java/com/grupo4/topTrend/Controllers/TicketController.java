package com.grupo4.topTrend.Controllers;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.grupo4.topTrend.models.dtos.MakeTransferDTO;
import com.grupo4.topTrend.models.dtos.PageDTO;
import com.grupo4.topTrend.models.dtos.ShowTicketDTO;
import com.grupo4.topTrend.models.dtos.TransferContentDTO;
import com.grupo4.topTrend.models.entities.Event;
import com.grupo4.topTrend.models.entities.Location;
import com.grupo4.topTrend.models.entities.QrCode;
import com.grupo4.topTrend.models.entities.Ticket;
import com.grupo4.topTrend.models.entities.Transfer;
import com.grupo4.topTrend.models.entities.User;
import com.grupo4.topTrend.services.CategoryService;
import com.grupo4.topTrend.services.EventService;
import com.grupo4.topTrend.services.LocationService;
import com.grupo4.topTrend.services.QrCodeService;
import com.grupo4.topTrend.services.TicketService;
import com.grupo4.topTrend.services.TransferService;
import com.grupo4.topTrend.services.UserService;


import jakarta.validation.Valid;

@RestController
@RequestMapping("/ticket")
@CrossOrigin("*")
public class TicketController {
	
	@Autowired
	TicketService ticketService;
	@Autowired
	EventService eventService;
	@Autowired
	UserService userService;
	@Autowired
	CategoryService categoryService;
	@Autowired
	LocationService locationService;
	@Autowired
	TransferService transferService;
	@Autowired
	QrCodeService qrCodeService;
	
	//Para poder comprar los tickets
	@PostMapping("/{locationCode}")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public ResponseEntity<?> buyticket(@PathVariable(name="locationCode") UUID locationCode){
		try {
			Location location = locationService.findOneByCode(locationCode);
			
			if(location == null)
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			
			User user = userService.findUserAuthenticated();
			Event event = location.getEvent();
			Date date = new Date();
			
			//SI LA CAPACIDAD MAXIMA ESTA LLENA
			if(location.getMaxCapacity() <= ticketService.countTicketByLocation(locationCode))
				return new ResponseEntity<>(HttpStatus.CONFLICT);
			//como ya no esta disponible le mandamos el 410
			if(!event.getActive()|| event.getDate().before(date))
				return new ResponseEntity<>(HttpStatus.GONE);
			
			//hoy si se efectua la compra
			ticketService.create(user, location);
			return new ResponseEntity<>(HttpStatus.OK);
			

			
		}catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//para obtener todos los tickets
	@GetMapping("/get/{ticketCode}")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public ResponseEntity<?>findOneTicketByUser(
			@RequestParam(defaultValue="") String title,
			@RequestParam(defaultValue="0") Integer page,
			@RequestParam(defaultValue="20") Integer size,
			@PathVariable(name = "ticketCode") UUID ticketCode){
		
		User user = userService.findUserAuthenticated();
		if(user == null)
			return new ResponseEntity<>( HttpStatus.NOT_FOUND);
		
		ShowTicketDTO ticket = ticketService.findTicketById(ticketCode);
		
		return new ResponseEntity<>(ticket,HttpStatus.OK);
		
	}
	
	//PARA OBTENER TODOS LOS TICKETS SEGUN EL USUARIO
	
	@GetMapping("/get")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public ResponseEntity<?>findTickets(
			@RequestParam(defaultValue="") String title,
			@RequestParam(defaultValue="0") Integer page,
			@RequestParam(defaultValue="20") Integer size){
		
		User user = userService.findUserAuthenticated();
		if(user == null)
			return new ResponseEntity<>( HttpStatus.NOT_FOUND);
		
		PageDTO<ShowTicketDTO> tickets = ticketService.findByUser(user.getCode(), title, PageRequest.of(page, size));
		
		return new ResponseEntity<>(tickets,HttpStatus.OK);	
	}
	
	
	//PARA PODER HACER LA TRANSFERENCIA
	
	@PostMapping("/sendTransfer")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public ResponseEntity<?> sendTransfer(@RequestBody @Valid MakeTransferDTO transferDTO) {
	    try {
	        UUID ticketCode = transferDTO.getTicketCode();
	        Ticket ticket = ticketService.findById(ticketCode);
	        if (ticket == null)
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

	        User user = userService.findUserAuthenticated();
	        if (!ticketService.belongsTo(ticketCode, user.getCode()))
	            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

	        UUID newOwnerCode = transferDTO.getNewOwnerCode();
	        User oldOwner = userService.findByCode(user.getCode());
	        User newOwner = userService.findByCode(newOwnerCode);

	        if (oldOwner == null || newOwner == null)
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

	        transferService.sendTransfer(oldOwner, newOwner, ticket);
	        return new ResponseEntity<>(HttpStatus.OK);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	
	//para poder recibir la transferencia
	@PostMapping("/receiveTransfer/{transferId}")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public ResponseEntity<?> receiveTransfer(@PathVariable(name = "transferId") UUID transferId,
	        @RequestBody @Valid TransferContentDTO transferContent) {
	    try {
	    	String content = transferContent.getContent();
	    	User user = userService.findUserAuthenticated();
	    	Transfer transfer = transferService.findById(transferId);
	    	
	    	if(transfer == null){
	    		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    	}
	    	
	        if (!transfer.getNewOwner().getCode().equals(user.getCode())) {
	            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	        }
	        
	        if (!transfer.getContent().equals(content)) 
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        
	         ticketService.setOwner(transfer.getTicket().getCode(),
	        		user.getCode(), content);
	        
	        return new ResponseEntity<>(HttpStatus.OK);
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	
	//PARA GENERAR EL CODIGO PARA EL QR
	@PostMapping("/qr/generate/{ticketId}")
	@PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<?> generateQrCode(@PathVariable(name = "ticketId") UUID ticketId) {
        try {
        	User user = userService.findUserAuthenticated();
        	if(ticketService.belongsTo(ticketId, user.getCode())) {
        		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        	}
            Ticket ticket = ticketService.findById(ticketId);
            
            if (ticket == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
         
            QrCode qrCode = qrCodeService.generateQrCode(ticket);

            return new ResponseEntity<>(qrCode, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }
	//AHORA PARA PODER VALIDAR ESE CODIGO 
	@PreAuthorize("hasAuthority('ROLE_VALIDATOR')")
	@PostMapping("/qr/validate/{qrCode}")
	public ResponseEntity<?> validateQrCode(@PathVariable(name = "qrCode") UUID qrCode) {
	    try {
	        boolean isValidated = qrCodeService.validateQrCode(qrCode);
	        
	        if (isValidated) {
	            return new ResponseEntity<>("QR code validated successfully", HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>("QR code not found or already validated", HttpStatus.NOT_FOUND);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	
}
