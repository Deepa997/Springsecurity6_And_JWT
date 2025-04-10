package com.example.security.service;

import java.util.Objects;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.example.security.config.CustomUserDetails;
import com.example.security.entity.User;
import com.example.security.repository.UserRepository;
@Component
public class CustomUserdetailsService implements UserDetailsService {
	private UserRepository userRepository;

	public CustomUserdetailsService(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		User user= userRepository.findByusername(username);
		if(Objects.isNull(user))
		{
			throw new  UsernameNotFoundException("User not found");
		}
		return new CustomUserDetails(user);
	}

}
