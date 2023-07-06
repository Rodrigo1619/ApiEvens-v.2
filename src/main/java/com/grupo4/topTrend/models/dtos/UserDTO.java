package com.grupo4.topTrend.models.dtos;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDTO {
	private UUID code;
	
	private String email;
	
	private String username;
	
	private Boolean active;
}
