package com.grupo4.topTrend.models.dtos;

import java.util.Optional;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SaveEventElementDTO {
	private UUID code;
	
	@NotBlank
	private String name;
}
