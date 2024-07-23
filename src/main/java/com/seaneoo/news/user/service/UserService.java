package com.seaneoo.news.user.service;

import com.seaneoo.news.user.entity.User;
import com.seaneoo.news.user.model.AuthenticatePayload;
import com.seaneoo.news.user.model.RegisterPayload;
import com.seaneoo.news.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public User register(RegisterPayload payload) {
		// TODO 2024-07-23, 15:28 Error handling and verification
		var user = User.builder().username(payload.getUsername().toLowerCase()).password(payload.getPassword()).build();
		return userRepository.save(user);
	}

	public User authenticate(AuthenticatePayload payload) {
		// TODO 2024-07-23, 15:28
		throw new UnsupportedOperationException("Not yet implemented");
	}
}
