package com.seaneoo.news.user.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ChangePasswordPayload {

	@NotBlank(message = "Username field is required.")
	String username;

	@NotBlank(message = "Current password field is required.")
	@JsonProperty("current_password")
	String currentPassword;

	@NotBlank(message = "New password field is required.")
	@Size(min = 8, message = "New password must be at least 8 characters.")
	@JsonProperty("new_password")
	String newPassword;
}
