package com.seaneoo.news.user.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterPayload {

	@NotBlank(message = "Username is required.")
	@Size(min = 2, max = 20, message = "Username must be between 2 and 20 characters.")
	@Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Username can only contain letters and numbers.")
	String username;

	@NotBlank(message = "Password is required.")
	@Size(min = 8, message = "Password must be at least 8 characters.")
	String password;

	// TODO 2024-07-23, 15:37 Add a field for verifying the password
}
