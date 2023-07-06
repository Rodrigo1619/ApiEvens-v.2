package com.grupo4.topTrend.models.dtos;
import java.util.Set;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ShowUserDTO {

	private UUID code;
	private String email;
	private String username;
	private Set<String> roles;
}
