package com.seaneoo.news.user.controller;

import com.seaneoo.news.user.entity.User;
import com.seaneoo.news.user.model.AuthenticatePayload;
import com.seaneoo.news.user.model.ChangePasswordPayload;
import com.seaneoo.news.user.model.RegisterPayload;
import com.seaneoo.news.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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
	public ResponseEntity<String> authenticate(@RequestBody @Valid AuthenticatePayload payload) {
		var token = userService.authenticate(payload);
		return ResponseEntity.ok(token);
	}

	@PostMapping("/change-password")
	public ResponseEntity<User> changePassword(@RequestBody @Valid ChangePasswordPayload payload) {
		var user = userService.changePassword(payload);
		return ResponseEntity.ok(user);
	}

	@DeleteMapping("/delete-account")
	public ResponseEntity<Object> deleteAccount(Authentication authentication) {
		userService.deleteAccount(authentication);
		return ResponseEntity.status(HttpStatus.GONE).build();
	}
}
