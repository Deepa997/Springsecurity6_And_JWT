package com.example.security.service;

import java.util.Objects;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.security.entity.User;
import com.example.security.repository.UserRepository;

@Service
public class UserService {
	private final UserRepository repository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final AuthenticationManager authentication;

	private final JwtService jwtservice;

	public UserService(UserRepository repository, BCryptPasswordEncoder bCryptPasswordEncoder,
			AuthenticationManager authentication, JwtService jwtservice) {
		super();
		this.repository = repository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.authentication = authentication;
		this.jwtservice = jwtservice;
	}

	public User register(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		return repository.save(user);

	}

	public String verify(User user) {
		Authentication authenticate = authentication
				.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

		if (authenticate.isAuthenticated()) 
			return jwtservice.genarateTocken(user);
		return "failure";
		
		}
	

}
