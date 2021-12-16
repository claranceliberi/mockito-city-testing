package rw.ac.rca.termOneExam.controller;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import rw.ac.rca.termOneExam.domain.City;

import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CityControllerIntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void getAll_testSuccess() {
        ResponseEntity<String> response = restTemplate.getForEntity("/api/cities/all", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void findById_testSuccess() {
        ResponseEntity<String> response = restTemplate.getForEntity("/api/cities/id/103", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void findById_notFound() {
        ResponseEntity<String> response = restTemplate.getForEntity("/api/cities/id/203", String.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void create_existCity() {
        City theCity = new City("Kigali",10);
        ResponseEntity<String> response = restTemplate.postForEntity("/api/cities/add", theCity, String.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("{\"status\":false,\"message\":\"City name Kigali is registered already\"}", response.getBody());
    }
    @Test
    public void create_success() {
        City theCity = new City("Mumbai",10);
        ResponseEntity<String> response = restTemplate.postForEntity("/api/cities/add", theCity, String.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }
}