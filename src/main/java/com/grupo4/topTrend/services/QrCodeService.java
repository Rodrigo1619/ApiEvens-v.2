package com.grupo4.topTrend.services;

import java.util.UUID;
import com.grupo4.topTrend.models.dtos.PageDTO;
import com.grupo4.topTrend.models.entities.QrCode;
import com.grupo4.topTrend.models.entities.Ticket;

public interface QrCodeService {
	QrCode generateQrCode(Ticket ticket);
	PageDTO <QrCode> findByTicket(Ticket ticket);
	Boolean validateQrCode(UUID code);
	Boolean anyValidatedQrCode(UUID ticketCode);
	PageDTO <QrCode> findByUser(UUID userCode);
	
}
