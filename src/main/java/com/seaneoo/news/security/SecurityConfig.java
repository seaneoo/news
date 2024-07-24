package com.seaneoo.news.security;

import com.seaneoo.news.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig {

	// TODO 2024-07-23, 16:01 Set these values in the application properties
	private static final int ARGON2_SALT_LENGTH = 32;
	private static final int ARGON2_HASH_LENGTH = 64;
	private static final int ARGON2_PARALLELISM = 4;
	private static final int ARGON2_MEMORY = 65_536;
	private static final int ARGON2_ITERATIONS = 3;

	@Autowired
	private UserRepository userRepository;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new Argon2PasswordEncoder(ARGON2_SALT_LENGTH, ARGON2_HASH_LENGTH, ARGON2_PARALLELISM, ARGON2_MEMORY, ARGON2_ITERATIONS);
	}

	@Bean
	public UserDetailsService userDetailsService() {
		return username -> {
			if (username == null) throw new RuntimeException("User not found.");
			return userRepository.findByUsername(username).orElseThrow();
		};
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
}
