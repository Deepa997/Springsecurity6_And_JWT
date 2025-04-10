package com.example.security.controller;

import java.util.Objects;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.security.entity.User;
import com.example.security.repository.UserRepository;
import com.example.security.service.UserService;

@RestController
public class UserController {
	private UserRepository userRepository;

	private final UserService service;
	public UserController(UserRepository userRepository, UserService service) {
		super();
		this.userRepository = userRepository;
		this.service = service;
	}

	
	@PostMapping("/register")
	public User registerUser(@RequestBody User user)
	{
		return service.register(user);
	}
	@PostMapping("/login")
	public String login(@RequestBody User user)
	{
		return service.verify(user);
		
	}

}
