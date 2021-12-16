package rw.ac.rca.termOneExam.utils;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import rw.ac.rca.termOneExam.domain.City;
import rw.ac.rca.termOneExam.repository.ICityRepository;
import rw.ac.rca.termOneExam.service.CityService;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.number.OrderingComparison.lessThan;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CityUtilTest {
    @Autowired
    private CityService cityService;

    @Mock
    private ICityRepository mockCityRepository;

    @Test
    public void no40OrMoreTemp() {

        List<City> cities = cityService.getAll();

        for(City city:cities){
            assertThat("Temprature", city.getWeather(),lessThan(Double.parseDouble("41")));
        }

    }

    @Test
    public void no10OrLessTemp() {

        List<City> cities = cityService.getAll();

        for(City city:cities){
            assertThat("Temprature", city.getWeather(),greaterThan(Double.parseDouble("10")));
        }

    }

    @Test
    public void MusanzeOrKigaliExists() {

        List<City> cities = cityService.getAll();
        assertThat(cities).extracting(City::getName).contains("Musanze","Kigali");
    }

    @Test
    public void testSpying() {
        List<City> cityList = new ArrayList<City>();
        List<City> spyCityList = Mockito.spy(cityList);


        spyCityList.add(new City("Kigali",24));
        spyCityList.add(new City("Nyamirambo",20));

        Mockito.verify(spyCityList).add(new City("Kigali",24));
        Mockito.verify(spyCityList).add(new City("Nyamirambo",20));

        assertEquals(2, spyCityList.size());
    }

    @Test
    public void testMocking() {

        Mockito.when(mockCityRepository.count()).thenReturn(11L);

        long userCount = mockCityRepository.count();

        Assert.assertEquals(11L, userCount);
        Mockito.verify(mockCityRepository).count();
    }
}
