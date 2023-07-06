package com.grupo4.topTrend.services;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.grupo4.topTrend.models.dtos.PageDTO;
import com.grupo4.topTrend.models.dtos.RegisterDTO;
import com.grupo4.topTrend.models.dtos.UserDTO;
import com.grupo4.topTrend.models.dtos.ShowUserDTO;
import com.grupo4.topTrend.models.entities.Role;
import com.grupo4.topTrend.models.entities.Token;
import com.grupo4.topTrend.models.entities.User;

public interface UserService extends UserDetailsService {
	
	
	void deleteById(UUID code) throws Exception;
	
	List<User>findAll();
	
	User findByCode(UUID code);
	User findByIdentifier(String identifier);
	User register(RegisterDTO registerInfo)throws Exception ;
	Token registerToken(User user) throws Exception;
	void cleanTokens(User user) throws Exception;
	User findUserAuthenticated();
	PageDTO<UserDTO> findByActive(Boolean active, String identifier, Pageable pageable);
	ShowUserDTO showUser(UUID code);
	Set<Role> getUserRoles(UUID code);
	Set<Role> setUserRoles(UUID code, List<String> roleStrings) throws Exception;
	void cleanToken() throws Exception;
	Boolean toggleActive(UUID code);
	Boolean comparePassword(String toCompare, String current);
	Boolean isTokenValid(User user, String token);
	Boolean existByCode(UUID code);
}
