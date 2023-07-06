package com.grupo4.topTrend.services.implementations;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grupo4.topTrend.models.dtos.PageDTO;
import com.grupo4.topTrend.models.entities.QrCode;
import com.grupo4.topTrend.models.entities.Ticket;
import com.grupo4.topTrend.repositories.QrCodeRepository;
import com.grupo4.topTrend.services.QrCodeService;
import com.grupo4.topTrend.utils.GenerateQRUtil;

@Service
public class QrCodeServiceImpl implements QrCodeService {
	@Autowired
	QrCodeRepository qrCodeRepository;

	@Override
	public QrCode generateQrCode(Ticket ticket) {
		
	    //si la fecha de expiracion es mayor que la fecha actual, aun hay ticket sin validar
		List<QrCode> qrCodes =  qrCodeRepository.findByTicketCodeAndExpirationDateGreaterThan(ticket.getCode(), Timestamp.valueOf(LocalDateTime.now()));
		if(!qrCodes.isEmpty()) {
			return qrCodes.get(0);
		}
		// Generar un UUID aleatorio
	    UUID randomUUID = UUID.randomUUID();

	    
	    QrCode qrCode = new QrCode();
	    qrCode.setCode(randomUUID);
	    qrCode.setTicket(ticket);

	    // Asignar la fecha de expiración al objeto qrCode mas 10 minutos
	    qrCode.setExpirationDate(Timestamp.valueOf(LocalDateTime.now().plusMinutes(10)));


	 // Guardar el objeto QrCode en la base de datos
	    qrCodeRepository.save(qrCode);

	    return qrCode;
	}

	@Override
	public PageDTO<QrCode> findByTicket(Ticket ticket) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean validateQrCode(UUID code) {
		QrCode qrCode = qrCodeRepository.findOneByCode(code);
	    
	    if (qrCode == null || qrCode.getValidationDate() != null) {
	        // El código QR no existe o ya ha sido validado previamente
	        return false;
	    }
	    
	    // Establecer la fecha y hora actual como fecha de validación
	    Timestamp validationDate = new Timestamp(System.currentTimeMillis());
	    qrCode.setValidationDate(validationDate);
	    
	    // Guardar el objeto QrCode actualizado en la base de datos
	    qrCodeRepository.save(qrCode);
	    
	    return true;
	}

	@Override
	public Boolean anyValidatedQrCode(UUID ticketCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PageDTO<QrCode> findByUser(UUID userCode) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
