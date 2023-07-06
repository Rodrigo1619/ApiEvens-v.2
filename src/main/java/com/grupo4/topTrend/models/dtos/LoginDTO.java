package com.grupo4.topTrend.models.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class LoginDTO {

	@NotEmpty
	private String identifier;
	
	@NotEmpty
	private String password;
	
}