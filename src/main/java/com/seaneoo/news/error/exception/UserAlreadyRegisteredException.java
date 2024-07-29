package com.seaneoo.news.error.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UserAlreadyRegisteredException extends ResponseStatusException {

	public UserAlreadyRegisteredException() {
		super(HttpStatus.CONFLICT, "User is already registered");
	}
}
