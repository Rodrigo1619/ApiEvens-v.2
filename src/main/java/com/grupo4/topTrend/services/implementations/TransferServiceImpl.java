package com.grupo4.topTrend.services.implementations;

import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grupo4.topTrend.models.entities.Ticket;
import com.grupo4.topTrend.models.entities.Transfer;
import com.grupo4.topTrend.models.entities.User;
import com.grupo4.topTrend.repositories.TransferRepository;
import com.grupo4.topTrend.repositories.UserRepository;
import com.grupo4.topTrend.services.TransferService;
import com.grupo4.topTrend.services.UserService;

@Service
public class TransferServiceImpl implements TransferService {
	@Autowired
	TransferRepository transferRepository;
	
	@Autowired
	UserService userService;
	
	 @Autowired
	 UserRepository userRepository;

	@Override
	public Transfer findBynewOwnerAndTicket(UUID newOwnerCode, UUID ticketCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Transfer create(User oldOwner, User newOwner, Ticket ticket, String content) throws Exception {
		
		
		Transfer transfer = new Transfer(
					
				);
		return transfer;
	}

	@Override
	public Transfer sendTransfer(User oldOwner, User newOwner, Ticket ticket) throws Exception {
	    String content = generateRandomString(10);
	    Transfer transfer = new Transfer(
	        oldOwner,
	        newOwner,
	        ticket,
	        content
	    );

	    return transferRepository.save(transfer);
	}

	@Override
	public String generateRandomString(int lenght) {
		String allowedCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
	    Random random = new Random();
	    StringBuilder sb = new StringBuilder(lenght);

	    for (int i = 0; i < lenght; i++) {
	        int randomIndex = random.nextInt(allowedCharacters.length());
	        char randomChar = allowedCharacters.charAt(randomIndex);
	        sb.append(randomChar);
	    }

	    return sb.toString();
	}

	@Override
	public Transfer receiveTransfer(User newOwner) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Transfer findById(UUID code) {
		return transferRepository.findOneByCode(code);
	}
}
