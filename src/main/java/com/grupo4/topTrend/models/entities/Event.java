package com.grupo4.topTrend.models.entities;

import java.sql.Time;
import java.sql.Date;
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
@ToString(exclude = {"eventxusers", "involveds", "sponsors", "locations"})
@Entity 
@Table (name = "evento")
public class Event {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID code;
	
	@Column(name = "titulo")
	private String title;

	@Column(name = "imagen")
	private String img;

	@Column(name = "duracion")
	private Integer duration;

	@Column(name = "fecha")
	private Date date;

	@Column(name = "hora")
	private Time time;
	
	@Column(name = "activo", insertable = false)
	private Boolean active;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_creador")
	private User creator;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_categoria")
	private Category category;
	

	@OneToMany(mappedBy = "event", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<EventXUser> eventxusers;

	@OneToMany(mappedBy = "event", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Involved> involveds;

	@OneToMany(mappedBy = "event", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Sponsor> sponsors;

	@OneToMany(mappedBy = "event", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Location> locations;

	public Event(String title, String img, Integer duration, Time time, Date date, Boolean active, Category category, User creator) {
		super();
		this.title = title;
		this.img = img;
		this.duration = duration;
		this.time = time;
		this.date = date;
		this.active = active;
		this.category = category;
		this.creator = creator;
	}
}
