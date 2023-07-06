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
@Table (name = "patrocinador")
public class Sponsor {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID code;
	

	@Column(name = "patrocinador")
	private String sponsor;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_evento")
	private Event event;

	public Sponsor(String sponsor, Event event) {
		super();
		this.sponsor = sponsor;
		this.event = event;
	}
}
