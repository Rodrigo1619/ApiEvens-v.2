package com.grupo4.topTrend.services;

import java.util.UUID;

import com.grupo4.topTrend.models.entities.Ticket;
import com.grupo4.topTrend.models.entities.Transfer;
import com.grupo4.topTrend.models.entities.User;

public interface TransferService {
	Transfer findBynewOwnerAndTicket(UUID newOwnerCode, UUID ticketCode);
	Transfer create(User oldOwner, User newOwner, Ticket ticket, String content) throws Exception;
	//Transfer sendTransfer(User oldOwner, User newOwner) throws Exception;
	Transfer sendTransfer(User oldOwner, User newOwner, Ticket ticket) throws Exception;
	Transfer receiveTransfer(User newOwner);
	Transfer findById(UUID code);
	String generateRandomString(int lenght);
}
