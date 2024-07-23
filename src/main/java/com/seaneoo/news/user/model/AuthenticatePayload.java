package com.seaneoo.news.user.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthenticatePayload {

	@NotBlank(message = "Username is required.")
	String username;

	@NotBlank(message = "Password is required.")
	String password;
}
