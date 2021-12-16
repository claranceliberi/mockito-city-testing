package rw.ac.rca.termOneExam.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rw.ac.rca.termOneExam.domain.City;
import rw.ac.rca.termOneExam.dto.CreateCityDTO;
import rw.ac.rca.termOneExam.repository.ICityRepository;
import rw.ac.rca.termOneExam.utils.TempratureFahrenheitConversion;

@Service
public class CityService {

	@Autowired
	private ICityRepository cityRepository;
	
	public Optional<City> getById(long id) {
		Optional<City> city = cityRepository.findById(id);
		City foundCity = city.get();
		if(city.isPresent())
			foundCity.setFahrenheit(TempratureFahrenheitConversion.tempToFah(foundCity.getWeather()));
		return Optional.of(foundCity);
	}

	public List<City> getAll() {
		List<City> cities = cityRepository.findAll();
		List<City> newCities = new ArrayList<>();
		for(City city:cities){
			city.setFahrenheit(TempratureFahrenheitConversion.tempToFah(city.getWeather()));
			newCities.add(city);
		}
		return newCities;
	}

	public boolean existsByName(String name) {
		
		return cityRepository.existsByName(name);
	}

	public City save(CreateCityDTO dto) {
		City city =  new City(dto.getName(), dto.getWeather());
		city.setFahrenheit(TempratureFahrenheitConversion.tempToFah(city.getWeather()));
		return cityRepository.save(city);
	}
	

}
