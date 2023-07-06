package com.grupo4.topTrend.models.entities;

import java.util.UUID;

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
@Entity 
@Table (name = "transferencia")
public class Transfer {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID code;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_usuario")
    private User oldOwner;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_usuario_destino")
    private User newOwner;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_ticket")
    private Ticket ticket;

    @Column(name = "contenido")
    private String content;

    public Transfer(User oldOwner, User newOwner, Ticket ticket, String content) {
        this.oldOwner = oldOwner;
        this.newOwner = newOwner;
        this.ticket = ticket;
        this.content = content;
    }

    
}

