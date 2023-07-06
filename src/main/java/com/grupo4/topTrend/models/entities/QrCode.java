package com.grupo4.topTrend.models.entities;

import java.sql.Timestamp;
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

@Data
@NoArgsConstructor
//@ToString(exclude = {"playlists", "tokens"})
@Entity 
@Table (name = "codigoqr")
public class QrCode {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID code;

	@Column(name = "fecha_expiracion")
	private Timestamp expirationDate;

	@Column(name = "fecha_validacion")
	private Timestamp validationDate;	

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_ticket")
	private Ticket ticket;


	public QrCode(Timestamp expirationDate, Ticket ticket) {
		super();
		this.expirationDate = expirationDate;
		this.ticket = ticket;
	}
	
	public String getContent() {
		return this.code.toString();
	}
}
