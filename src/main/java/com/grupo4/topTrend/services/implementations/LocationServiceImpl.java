package com.grupo4.topTrend.services.implementations;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grupo4.topTrend.models.dtos.SaveLocationDTO;
import com.grupo4.topTrend.models.entities.Event;
import com.grupo4.topTrend.models.entities.Location;
import com.grupo4.topTrend.repositories.LocationRepository;
import com.grupo4.topTrend.services.LocationService;

@Service
public class LocationServiceImpl implements LocationService{
	
	@Autowired
	LocationRepository locationRepository;

	@Override
	public List<Location> createList(List<SaveLocationDTO> locations, Event event) throws Exception {
		return locationRepository.saveAll(locations.stream().map(i -> {
			Location newLocation = new Location(
					i.getPrice(),
					i.getMaxCapacity(),
					i.getName(),
					event);
			return newLocation;}
					).collect(Collectors.toList()));
	}

	@Override
	public List<Location> updateList(List<SaveLocationDTO> locations, Event event) throws Exception {
		//Verificar que los localidades que si tengan id, realmente pertenescan a ese evento
		locations.forEach( l -> {
			if(l.getCode() != null) {
				boolean doesExists = existsInOtherEvent(l.getCode(), event.getCode());
				if(!doesExists)
					l.setCode(null);// Si no es asi, poner id como nulo para evitar tocar localidades de otros eventos
			}});
		
		//Proceder a guardar/actualizar los datos, dependiendo si tienen o no id
		return locationRepository.saveAll(locations.stream().map(l -> {
			Location newLocation = new Location(
					l.getPrice(),
					l.getMaxCapacity(),
					l.getName(),
					event);

			if(l.getCode() != null)
				newLocation.setCode(l.getCode());
			return newLocation;}
					).collect(Collectors.toList()));
	}

	@Override
	public void deleteList(List<Location> locations, Event event) throws Exception {
		locationRepository.deleteAll(locations);;
	}

	@Override
	public Location findOneByCode(UUID code) {
		return locationRepository.findOneByCode(code);
	}

	@Override
	public List<Location> findByEventCode(UUID code) {
		return locationRepository.findByEventCode(code);
	}

	@Override
	public boolean existsInOtherEvent(UUID code, UUID eventCode) {
		return locationRepository.existsByCodeAndEventCode(code, eventCode);
	}

	@Override
	public Location findByCode(UUID code) {
		// TODO Auto-generated method stub
		return null;
	}

}
