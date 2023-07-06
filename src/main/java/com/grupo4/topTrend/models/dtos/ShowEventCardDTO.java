package com.grupo4.topTrend.models.dtos;

import java.sql.Date;
import java.sql.Time;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ShowEventCardDTO {
	private UUID code;
	private String title;
	private Time time;
	private Date date;
	private Integer duration;
	private String category;
}
