package com.grupo4.topTrend.models.dtos;

import java.sql.Time;
import java.util.Date;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class ShowTicketDTO {
	
	private UUID code;
	private String eventTitle;
	private Date eventDate;
	private Time eventTime;
	private String location;
	//private Integer quantity;
	private Float price;
}
