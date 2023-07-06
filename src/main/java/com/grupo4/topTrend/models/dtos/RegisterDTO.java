package com.grupo4.topTrend.models.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterDTO {
	@NotEmpty(message = "username must not be empty")
	private String username;
	
	@NotEmpty(message = "username must not be empty")
	@Email
	private String email;
	
	@NotEmpty(message = "password must not be empty")
	@Size(min = 8)
	@Pattern(regexp = "^(?=.*[0-9]).*$", message = "password must have at leat one number")
	@Pattern(regexp = "^(?=.*[a-z]).*$", message = "password must have at leat one lowercase letter")
	@Pattern(regexp = "^(?=.*[A-Z]).*$", message = "password must have at leat one uppercase letter")
	@Pattern(regexp = "^(?=.*[@#$%^&+=]).*$", message = "password must have at leat one special character")
	private String password;
}
