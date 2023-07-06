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
@Entity 
@ToString( exclude = {"qrCodes"})
@Table (name = "ticket")
public class Ticket {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID code;
	

	@Column(name = "numero")
	private Integer number;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_localidad")
	private Location location;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_usuario")
	private User user;
	

	@OneToMany(mappedBy = "ticket", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<QrCode> qrCodes;


	public Ticket(Integer number, Location location, User user) {
		super();
		this.number = number;
		this.location = location;
		this.user = user;
	}
}
