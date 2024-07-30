package com.seaneoo.news.user.controller;

import com.seaneoo.news.user.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

	@GetMapping("/me")
	public ResponseEntity<User> me(Authentication authentication) {
		var user = (User) authentication.getPrincipal();
		return ResponseEntity.ok(user);
	}
}
