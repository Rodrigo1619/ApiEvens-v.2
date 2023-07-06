package com.grupo4.topTrend.services.implementations;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.grupo4.topTrend.models.dtos.PageDTO;
import com.grupo4.topTrend.models.dtos.RegisterDTO;
import com.grupo4.topTrend.models.dtos.UserDTO;
import com.grupo4.topTrend.models.dtos.ShowUserDTO;
import com.grupo4.topTrend.models.entities.Role;
import com.grupo4.topTrend.models.entities.Token;
import com.grupo4.topTrend.models.entities.User;
import com.grupo4.topTrend.repositories.RoleRepository;
import com.grupo4.topTrend.repositories.TokenRepository;
import com.grupo4.topTrend.repositories.UserRepository;
import com.grupo4.topTrend.services.UserService;
import com.grupo4.topTrend.utils.JWTTools;

import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepository userRepository;

	@Autowired
	TokenRepository tokenRepository;

	@Autowired
	RoleRepository roleRepository;
		
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	private JWTTools jwtTools;
	
	@Override
	public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {
		User user = userRepository.findOneByUsernameOrEmail(identifier, identifier);
	
		if (user == null) {
			throw new UsernameNotFoundException("User: " + identifier + ", not found!");
		}
		Set<Role> roles = roleRepository.findByUsersCode(user.getCode());
		
		User userWrapper = new User(
				user.getCode(),
				user.getEmail(),
				user.getUsername(),
				user.getPassword(),
				user.getActive(),
				roles);
		
		System.out.println(userWrapper);
		
		return userWrapper;
	}

	@Override
	public void deleteById(UUID code) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<User> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User findByCode(UUID code) {
		return userRepository.findOneByCode(code);
	}

	@Override
	public User findByIdentifier(String identifier) {
		List<User> users = userRepository.findByUsernameOrEmail(identifier, identifier);
		if(users.isEmpty())
			return null;
		else
			return users.get(0);
	}

	@Override
	public Boolean comparePassword(String toCompare, String current) {
		return passwordEncoder.matches(toCompare, current);

	}
	
	@Override
	@Transactional(rollbackOn = Exception.class)
	public User register(RegisterDTO registerInfo) throws Exception {
		Boolean doesExist = !userRepository.findByUsernameOrEmail(registerInfo.getUsername(), registerInfo.getEmail()).isEmpty();
		
		if(doesExist)
			return null;
		Role role = roleRepository.findByRole("ROLE_USER");
		
		User user = new User(
				registerInfo.getEmail(),
				registerInfo.getUsername(),
				passwordEncoder.encode(registerInfo.getPassword()),
				role
				);
		return userRepository.save(user);
	}

	@Override
	public Token registerToken(User user) throws Exception {
		cleanTokens(user);
		
		String tokenString = jwtTools.generateToken(user);
		Token token = new Token(tokenString, user);
		
		tokenRepository.save(token);
		
		return token;
	}

	@Override
	public Boolean isTokenValid(User user, String token) {
		try {
			cleanTokens(user);
			List<Token> tokens = tokenRepository.findByUserAndActive(user, true);
			
			tokens.stream()
				.filter(tk -> tk.getContent().equals(token))
				.findAny()
				.orElseThrow(() -> new Exception());
			
			return true;
		} catch (Exception e) {
			return false;
		}		
	}

	@Override
	public void cleanTokens(User user) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public User findUserAuthenticated() {
		String username = SecurityContextHolder
				.getContext()
				.getAuthentication()
				.getName();
			
			return findByIdentifier(username);
	}

	@Override
	public void cleanToken() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PageDTO<UserDTO> findByActive(Boolean active, String identifier, Pageable pageable) {
		Page<User> users = userRepository
				.findByActiveAndEmailContainingOrActiveAndUsernameContaining(active, identifier, active, identifier, pageable);
		
		PageDTO<UserDTO> userPageDTO = new PageDTO<>(
				users.getContent().stream().map( u -> {
					return new UserDTO(
						u.getCode(),
						u.getEmail(),
						u.getUsername(),
						u.getActive());
	
				}).collect(Collectors.toList()),
				users.getNumber(), 
				users.getSize(), 
				users.getTotalElements(),
				users.getTotalPages());
				
		
		return userPageDTO;
	}

	@Override
	public Boolean toggleActive(UUID code) {
		User user = userRepository.findOneByCode(code);
		if(user == null)
			return null;
		
		user.setActive(!user.getActive());
		userRepository.save(user);
		return user.getActive();
	}

	@Override
	public Set<Role> getUserRoles(UUID code) {
		return roleRepository.findByUsersCode(code);
	}

	@Override
	public Set<Role> setUserRoles(UUID userCode, List<String> roleStrings) throws Exception {

		User user = userRepository.findOneByCode(userCode);
		
		if(user == null) {
			return null;
		}

		roleStrings.add("ROLE_USER"); //Para que todos los usuarios siempre queden con este rol
		
		Set<Role> newRoles = new HashSet<>();

	    for (String roleString : roleStrings) {
	        Role role = roleRepository.findByRole(roleString);
	        newRoles.add(role);
	    }

	    user.setRoles(newRoles);
	    userRepository.save(user);
		return user.getRoles();
	}

	@Override
	public Boolean existByCode(UUID code) {
		return userRepository.existsByCode(code);
	}

	@Override
	public ShowUserDTO showUser(UUID code) {
		User user = userRepository.findOneByCode(code);
		if(user == null)
			return null;
		
		return new ShowUserDTO(
				user.getCode(),
				user.getEmail(),
				user.getUsername(),				
				user.getRoles().stream().map(r -> r.getRole()).collect(Collectors.toSet()));
	}	
}
