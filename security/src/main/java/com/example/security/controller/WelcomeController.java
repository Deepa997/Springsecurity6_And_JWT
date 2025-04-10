package com.example.security.controller;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {

	@GetMapping("")
	private String welcome() {
		return "welcome to the spring security learning";
	}
	
	@GetMapping("/csrf")
	private CsrfToken getCsrf(HttpServletRequest request)
	{
		return (CsrfToken) request.getAttribute("_csrf");
		
	}
}
