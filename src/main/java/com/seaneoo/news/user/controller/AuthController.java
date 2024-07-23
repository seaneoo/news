package com.seaneoo.news.user.controller;

import com.seaneoo.news.user.entity.User;
import com.seaneoo.news.user.model.AuthenticatePayload;
import com.seaneoo.news.user.model.RegisterPayload;
import com.seaneoo.news.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private UserService userService;

	@PostMapping("/register")
	public ResponseEntity<User> register(@RequestBody @Valid RegisterPayload payload) {
		var user = userService.register(payload);
		return ResponseEntity.status(HttpStatus.CREATED).body(user);
	}

	@PostMapping("/authenticate")
	public ResponseEntity<User> authenticate(@RequestBody @Valid AuthenticatePayload payload) {
		var user = userService.authenticate(payload);
		// TODO 2024-07-23, 15:29
		throw new UnsupportedOperationException("Not yet implemented");
	}
}
