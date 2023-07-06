package com.grupo4.topTrend.models.entities;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString(exclude = {"tickets"})
@Entity 
@Table (name = "localidad")
public class Location {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID code;
	
	@Column(name = "precio")
	private Float price;

	@Column(name = "capacidad_maxima")
	private Integer maxCapacity;

	@Column(name = "localidad")
	private String location;
	

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_evento")
	@JsonIgnore
	private Event event;
	

	@OneToMany(mappedBy = "location", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Ticket> tickets;


	public Location(Float price, Integer maxCapacity, String location, Event event) {
		super();
		this.price = price;
		this.maxCapacity = maxCapacity;
		this.location = location;
		this.event = event;
	}
}
