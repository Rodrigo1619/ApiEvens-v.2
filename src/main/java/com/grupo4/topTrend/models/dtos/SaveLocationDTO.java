package com.grupo4.topTrend.models.dtos;

import java.util.Optional;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SaveLocationDTO {

	private UUID code;
	@NotEmpty
	private Float price;
	@NotEmpty
	private Integer maxCapacity;
	@NotBlank
	private String name;
	
}
