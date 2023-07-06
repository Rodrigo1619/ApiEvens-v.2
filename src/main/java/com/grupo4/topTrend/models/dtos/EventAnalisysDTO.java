package com.grupo4.topTrend.models.dtos;

import java.util.HashMap;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EventAnalisysDTO {
	private UUID code;
	private String title;
	private Long ticketSold;
	private Float attendancePercentage;
	private Float groupPercentage;
	private Float indvPercentage;
	
	private HashMap<String, Integer> locations;
	private HashMap<String, Integer> validations;
}
