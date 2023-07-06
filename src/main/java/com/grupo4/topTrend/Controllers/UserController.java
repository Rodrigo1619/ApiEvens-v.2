package com.grupo4.topTrend.Controllers;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.grupo4.topTrend.models.dtos.PageDTO;
import com.grupo4.topTrend.models.dtos.UserDTO;
import com.grupo4.topTrend.models.dtos.SetRolesDTO;
import com.grupo4.topTrend.models.dtos.ShowUserDTO;
import com.grupo4.topTrend.models.entities.Role;
import com.grupo4.topTrend.models.entities.User;
import com.grupo4.topTrend.services.UserService;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {
	@Autowired
	private UserService userService;
	

	@GetMapping("/moderate/get/inactive")
	@PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR')")
	public ResponseEntity<?> getInactive( 
			@RequestParam(defaultValue="") String identifier,
			@RequestParam(defaultValue="0") Integer page,
			@RequestParam(defaultValue="20") Integer size) {
		
		PageDTO<UserDTO> users = userService.findByActive(false, identifier, PageRequest.of(page, size));
		
		return new ResponseEntity<>(users, HttpStatus.OK);
	}

	@GetMapping("/moderate/get/active")
	@PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR')")
	public ResponseEntity<?> getActive( 
			@RequestParam(defaultValue="") String identifier,
			@RequestParam(defaultValue="0") Integer page,
			@RequestParam(defaultValue="20") Integer size) {
		PageDTO<UserDTO> users = userService.findByActive(true, identifier, PageRequest.of(page, size));
		
		return new ResponseEntity<>(users, HttpStatus.OK);
	}
	
	@PatchMapping("/moderate/toggleActive/{userCode}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR')")
	public ResponseEntity<?> toggleActive(@PathVariable(name="userCode") UUID userCode){
		
		Boolean changeTo = userService.toggleActive(userCode);
		
		if(changeTo == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		return new ResponseEntity<>(changeTo, HttpStatus.OK);
	}
	

	@GetMapping("/admin/roles/{userCode}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<?> getRoles( @PathVariable( name="userCode") UUID userCode) {
		
		if(!userService.existByCode(userCode))
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		Set<Role> roles = userService.getUserRoles(userCode);
				
		return new ResponseEntity<>(roles, HttpStatus.OK);
	}
	
	@PutMapping("/admin/roles/{userCode}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<?> setRoles( 
			@PathVariable( name="userCode") UUID userCode,
			@RequestBody SetRolesDTO roles) {
		try {
		
		if(!userService.existByCode(userCode))
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		Set<Role> updatedRoles;
			updatedRoles = userService.setUserRoles(userCode, roles.getRoles());
			
			return new ResponseEntity<>(updatedRoles, HttpStatus.OK);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
				
	}
	
	@GetMapping("/whoiam")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public ResponseEntity<?> whoIAm() {
		
		User user = userService.findUserAuthenticated();
		ShowUserDTO showUser = userService.showUser(user.getCode());
				
		return new ResponseEntity<>(showUser, HttpStatus.OK);
	}
}
