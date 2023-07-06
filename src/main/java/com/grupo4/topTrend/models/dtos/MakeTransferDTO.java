package com.grupo4.topTrend.models.dtos;

import java.util.UUID;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MakeTransferDTO {
	@NotNull
	UUID newOwnerCode;
	@NotNull
	UUID ticketCode;

}
