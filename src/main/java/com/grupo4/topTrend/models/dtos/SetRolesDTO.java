package com.grupo4.topTrend.models.dtos;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SetRolesDTO {
	@NotNull
	List<String> roles;
}
