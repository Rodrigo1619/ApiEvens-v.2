package com.grupo4.topTrend.services.implementations;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grupo4.topTrend.models.dtos.SaveEventElementDTO;
import com.grupo4.topTrend.models.entities.Event;
import com.grupo4.topTrend.models.entities.Sponsor;
import com.grupo4.topTrend.repositories.SponsorRepository;
import com.grupo4.topTrend.services.SponsorService;

@Service
public class SponsorServiceImpl implements SponsorService{

	@Autowired
	private SponsorRepository sponsorRepository;

	@Override
	public Sponsor findOneByCode(UUID code) {
		return sponsorRepository.findOneByCode(code);
	}

	@Override
	public List<Sponsor> findByEventCode(UUID code) {
		return sponsorRepository.findByEventCode(code);
	}

	@Override
	public void createList(List<SaveEventElementDTO> sponsors, Event event) throws Exception {
		sponsorRepository.saveAll(sponsors.stream().map(s -> {
			Sponsor newSponsor = new Sponsor(
					s.getName(), event);
			return newSponsor;}
					).collect(Collectors.toList()));
	}


	@Override
	public void cudList(List<SaveEventElementDTO> sponsors, Event event) throws Exception {

		//Verificar que los elementos que si tengan id, realmente pertenescan a ese evento
		sponsors.forEach( s -> {
			if(s.getCode() != null) {
				boolean doesExists = existsInOtherEvent(s.getCode(), event.getCode());
				if(!doesExists)
					s.setCode(null);// Si no es asi, poner id como nulo para evitar tocar elementos de otros eventos
			}});
		//Proceder a guardar/actualizar los datos, dependiendo si tienen o no id
		List<Sponsor> newList = sponsorRepository.saveAll(sponsors.stream().map(s -> {
			Sponsor newSponsor = new Sponsor(
					s.getName(), event);
			
			if(s.getCode() != null)
				newSponsor.setCode(s.getCode());
			return newSponsor;}
					).collect(Collectors.toList()));
		
		List<Sponsor> oldList = sponsorRepository.findByEventCode(event.getCode());
		
		//Borrar aquellos elementos de peticiones anteriores, que no estuvieran en la lista actual
		sponsorRepository.deleteAll(oldList.stream()
				.filter( s -> !newList.contains(s))
				.collect(Collectors.toList()));
	}
	
	@Override
	public boolean existsInOtherEvent(UUID code, UUID eventCode) {
		return sponsorRepository.existsByCodeAndEventCode(code, eventCode);
	}
}
