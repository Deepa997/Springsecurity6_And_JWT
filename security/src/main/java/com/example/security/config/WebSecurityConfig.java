package com.example.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	private final UserDetailsService userDetailsService;
	private final JwtAuthenticatorFilter jwtAuthenticatorFilter;

	public WebSecurityConfig(UserDetailsService userDetailsService, JwtAuthenticatorFilter jwtAuthenticatorFilter) {
		super();
		this.userDetailsService = userDetailsService;
		this.jwtAuthenticatorFilter = jwtAuthenticatorFilter;
	}

	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		// httpSecurity.authorizeHttpRequests(request ->
		// request.anyRequest().authenticated().and().httpBasic(Customizer.withDefaults()));

		httpSecurity.csrf().disable() // or we can write like .csrf(csrf ->csrf.disable)
				.authorizeHttpRequests(auth -> auth.requestMatchers(new AntPathRequestMatcher("/register")).permitAll()
						.requestMatchers(new AntPathRequestMatcher("/login")).permitAll().anyRequest().authenticated())
				.
				// this is for just popup basic login box
				httpBasic(Customizer.withDefaults())
				.addFilterBefore(jwtAuthenticatorFilter, UsernamePasswordAuthenticationFilter.class);

		// it will give the default loginform ** Customizer.withDefaults()** is optional
		// to keep in
		// .formLogin(Customizer.withDefaults());
		return httpSecurity.build();

	}

	// @Bean
	public UserDetailsService userDetailsService() {
		UserDetails deepa = User.withUsername("deepa").password("{noop}password").roles("USER").build();
		UserDetails veena = User.withUsername("veena").password("password").roles("USER").build();
		return new InMemoryUserDetailsManager(deepa, veena);

	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder(14);
	}

	@Bean

	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailsService);
		// provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
		provider.setPasswordEncoder(bCryptPasswordEncoder());
		return provider;
	}
	@Bean
	
	public AuthenticationManager manager(AuthenticationConfiguration configuration) throws Exception
	{
		return configuration.getAuthenticationManager();
	}
}
