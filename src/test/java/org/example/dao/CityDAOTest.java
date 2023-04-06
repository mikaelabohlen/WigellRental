package org.example.dao;

import org.example.entities.City;
import org.example.entities.Country;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.List;
public class CityDAOTest {

    private CityDAO cityDAO;
    private CountryDAO countryDAO;
    @BeforeEach
    void setup(){cityDAO = new CityDAO();
    countryDAO = new CountryDAO();}

    @Test
    void testCreate() {
        Country country = new Country();
        country.setCountry("Sweden");
        country.setLastUpdate(new Timestamp(System.currentTimeMillis()));
        countryDAO.create(country);
        City city = new City();
        city.setCity("Baku");
        city.setCountry(country);
        city.setLastUpdate(new Timestamp(System.currentTimeMillis()));
        cityDAO.create(city);

    }

    @Test
    void testRead() {
        City city = cityDAO.read(1);
        System.out.println(city.getCity());
    }

    @Test
    void getAll() {
        List<City> all = cityDAO.getAll();
        for (City city : all) {
            System.out.println(city.getCity());

        }
    }

    @Test
    void testUpdate() {

    }

    @Test
    void testDelete() {

    }

}
