package rw.ac.rca.termOneExam.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import rw.ac.rca.termOneExam.domain.City;
import rw.ac.rca.termOneExam.dto.CreateCityDTO;
import rw.ac.rca.termOneExam.repository.ICityRepository;
import rw.ac.rca.termOneExam.utils.TempratureFahrenheitConversion;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CityServiceTest {

    @Mock
    private ICityRepository cityRepository;

    @InjectMocks
    private CityService cityService;

    @Test
    public void all_test() {
        when(cityRepository.findAll()).thenReturn(Arrays.asList(new City("Kigali",24), new City("Nyamirambo",20)));

        assertEquals("Nyamirambo", cityService.getAll().get(1).getName());
        assertEquals(TempratureFahrenheitConversion.tempToFah(20), cityService.getAll().get(1).getFahrenheit());
    }

    @Test
    public void findById_testFound() {
        when(cityRepository.findById(100L)).thenReturn(Optional.of(new City("Kigali",24)));

        assertEquals("Kigali", cityService.getById(100L).get().getName());
        assertEquals(TempratureFahrenheitConversion.tempToFah(24), cityService.getById(100L).get().getFahrenheit());
    }

    @Test
    public void findById_testNotFound() {
        when(cityRepository.findById(100L)).thenReturn(Optional.empty());

        assertEquals(false, cityService.getById(100L).isPresent());
    }

    @Test
    public void existByName_success() {
        when(cityRepository.existsByName("Kigali")).thenReturn(true);

        assertEquals(true, cityService.existsByName("Kigali"));
    }

    @Test
    public void existByName_fail() {
        when(cityRepository.existsByName("Kigaliii")).thenReturn(false);

        assertEquals(false, cityService.existsByName("Kigaliii"));
    }

    @Test
    public void createCity_success() {
        CreateCityDTO cityDTO = new CreateCityDTO("Nyamijos",33);
        when(cityRepository.save(any(City.class))).thenReturn(new City(cityDTO.getName(),cityDTO.getWeather()));

        assertEquals(cityDTO.getName(), cityService.save(cityDTO).getName());
        assertEquals(TempratureFahrenheitConversion.tempToFah(cityDTO.getWeather()), cityService.save(cityDTO).getFahrenheit());
    }
}
