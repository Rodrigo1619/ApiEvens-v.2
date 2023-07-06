package com.grupo4.topTrend.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grupo4.topTrend.models.dtos.LoginDTO;
import com.grupo4.topTrend.models.dtos.RegisterDTO;
import com.grupo4.topTrend.models.dtos.TokenDTO;
import com.grupo4.topTrend.models.entities.Token;
import com.grupo4.topTrend.models.entities.User;
import com.grupo4.topTrend.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthController {

	@Autowired
	public UserService userService;
	
	@PostMapping("/signup")
	public ResponseEntity<?> register(@RequestBody @Valid RegisterDTO userInfo, BindingResult validations){
		if(validations.hasErrors()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		try {
			User user = userService.register(userInfo);
			if(user == null)
				return new ResponseEntity<>(HttpStatus.CONFLICT);
			
			return new ResponseEntity<>(user, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody @Valid LoginDTO userInfo, BindingResult validations){
		if(validations.hasErrors()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		try {
			User user = userService.findByIdentifier(userInfo.getIdentifier());
			if(user == null)
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			
			if( !userService.comparePassword(userInfo.getPassword(), user.getPassword()))
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			
			
			//return new ResponseEntity<>(user, HttpStatus.OK);
			Token token = userService.registerToken(user);
			return new ResponseEntity<>(new TokenDTO(token), HttpStatus.OK);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
