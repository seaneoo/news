package com.seaneoo.news.user.service;

import com.seaneoo.news.error.exception.UserNotFoundException;
import com.seaneoo.news.security.JwtService;
import com.seaneoo.news.user.entity.User;
import com.seaneoo.news.user.model.AuthenticatePayload;
import com.seaneoo.news.user.model.RegisterPayload;
import com.seaneoo.news.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtService jwtService;

	public User register(RegisterPayload payload) {
		// TODO 2024-07-23, 15:28 Error handling and verification
		var hashedPassword = passwordEncoder.encode(payload.getPassword());
		var user = User.builder().username(payload.getUsername().toLowerCase()).password(hashedPassword).build();
		return userRepository.save(user);
	}

	public String authenticate(AuthenticatePayload payload) {
		var userOptional = userRepository.findByUsername(payload.getUsername().toLowerCase());
		if (userOptional.isEmpty()) {
			throw new UserNotFoundException();
		}

		var auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userOptional.get().getUsername(), payload.getPassword()));
		var user = (User) auth.getPrincipal();
		return jwtService.generateToken(user);
	}
}
