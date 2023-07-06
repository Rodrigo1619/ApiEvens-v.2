package com.grupo4.topTrend.models.dtos;

import com.grupo4.topTrend.models.entities.Token;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TokenDTO {

	private String token;
	
	public TokenDTO(Token token) {
		this.token = token.getContent();
	}
	
}