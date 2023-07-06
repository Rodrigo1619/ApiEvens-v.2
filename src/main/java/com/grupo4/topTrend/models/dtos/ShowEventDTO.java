package com.grupo4.topTrend.models.dtos;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.UUID;

import com.grupo4.topTrend.models.entities.Location;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ShowEventDTO {
	private UUID code;
	private String title;
	private Time time;
	private Date date;
	private String category;
	private List<String> sponsors;
	private List<String> involveds;
	private List<Location> locations;
}
