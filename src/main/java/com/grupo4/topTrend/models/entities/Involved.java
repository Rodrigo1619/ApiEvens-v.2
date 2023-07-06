package com.grupo4.topTrend.models.entities;

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
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
//@ToString(exclude = {"playlists", "tokens"})
@Entity 
@Table (name = "involucrado")
public class Involved {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID code;

	@Column(name = "involucrado")
	private String involved;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_evento")
	private Event event;

	public Involved(String involved, Event event) {
		super();
		this.involved = involved;
		this.event = event;
	}
}
