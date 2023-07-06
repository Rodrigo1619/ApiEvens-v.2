package com.grupo4.topTrend.services.implementations;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grupo4.topTrend.models.dtos.SaveEventElementDTO;
import com.grupo4.topTrend.models.entities.Event;
import com.grupo4.topTrend.models.entities.Involved;
import com.grupo4.topTrend.repositories.InvolvedRepository;
import com.grupo4.topTrend.services.InvolvedService;

@Service
public class InvolvedServiceImpl implements InvolvedService {
	
	@Autowired
	private InvolvedRepository involvedRepository;

	@Override
	public Involved findOneByCode(UUID code) {
		return involvedRepository.findOneByCode(code);
	}

	@Override
	public List<Involved> findByEventCode(UUID code) {
		return involvedRepository.findByEventCode(code);
	}

	@Override
	public void createList(List<SaveEventElementDTO> involveds, Event event) throws Exception {
		involvedRepository.saveAll(involveds.stream().map(i -> {
			Involved newInvolved = new Involved(
					i.getName(), event);
			return newInvolved;}
					).collect(Collectors.toList()));
	}


	@Override
	public void cudList(List<SaveEventElementDTO> involveds, Event event) throws Exception {

		//Verificar que los elementos que si tengan id, realmente pertenescan a ese evento
		involveds.forEach( i -> {
			if(i.getCode() != null) {
				boolean doesExists = existsInOtherEvent(i.getCode(), event.getCode());
				if(!doesExists)
					i.setCode(null);// Si no es asi, poner id como nulo para evitar tocar elementos de otros eventos
			}});
		
		//Proceder a guardar/actualizar los datos, dependiendo si tienen o no id
		List<Involved> newList = involvedRepository.saveAll(involveds.stream().map(i -> {
			Involved newInvolved = new Involved(
					i.getName(), event);
			
			if(i.getCode() != null)
				newInvolved.setCode(i.getCode());
			return newInvolved;}
					).collect(Collectors.toList()));
		
		List<Involved> oldList = involvedRepository.findByEventCode(event.getCode());
		
		//Borrar aquellos elementos de peticiones anteriores, que no estuvieran en la lista actual
		involvedRepository.deleteAll(oldList.stream()
				.filter( i -> !newList.contains(i))
				.collect(Collectors.toList()));
	}
	
	@Override
	public boolean existsInOtherEvent(UUID code, UUID eventCode) {
		return involvedRepository.existsByCodeAndEventCode(code, eventCode);
	}
}
