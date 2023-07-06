package com.grupo4.topTrend.models.dtos;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.UUID;

import com.grupo4.topTrend.models.entities.Category;
import com.grupo4.topTrend.models.entities.Involved;
import com.grupo4.topTrend.models.entities.Location;
import com.grupo4.topTrend.models.entities.Sponsor;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SaveEventDTO {
	//Como tambien se deberian poder actualizar eventos, creo que es conveniente tener el identificador
	private UUID code;

	@NotEmpty(message="title must no to be empty")
	private String title;

	private String img;

	@NotNull(message="Duration must no to be empty")
	private Integer duration;

	@NotNull(message="Date must no to be empty")
	private Date date;
	
	@NotNull(message="Time must no to be empty")
	private Time time;
	
	private Boolean active;
	
	
	//Con estos tengo dudas de dejarlos en el dto
	@NotNull(message="Category must no to be empty")
	private UUID categoryCode;

	
	private List<SaveEventElementDTO> involveds;

	private List<SaveEventElementDTO> sponsors;	
	
	private List<SaveLocationDTO> locations;
}
