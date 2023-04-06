package org.example.dao;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import org.example.entities.Actor;
import org.example.entities.Address;
import org.example.entities.City;
import org.example.entities.Country;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.sql.Timestamp;
import java.util.List;
public class AddressDAOTest {

    private AddressDAO adressDAO;
    private CityDAO cityDAO;
    private CountryDAO countryDAO;

    @BeforeEach
    void setup(){adressDAO = new AddressDAO();
        cityDAO = new CityDAO();
        countryDAO = new CountryDAO();}

    @Test
    void testCreate() {
        City city = new City();

        // create a Geometry object to represent the location
        GeometryFactory geometryFactory = new GeometryFactory();
        Coordinate coord = new Coordinate(10, 20); // set the latitude and longitude. I guess this is in like middle of Africa like Tchad but euhm, it passes the test
        Point point = geometryFactory.createPoint(coord);

        Country country = new Country();
        country.setCountry("Anguilla");
        country.setLastUpdate(new Timestamp(System.currentTimeMillis()));
        countryDAO.create(country);
        //set the country stuff

       city.setCountry(country);
       city.setCity("Akron");
        city.setLastUpdate(new Timestamp(System.currentTimeMillis()));
        cityDAO.create(city);
        Address address = new Address();
        address.setAddress("Gatansgata 9");
        address.setPostalCode("62777");
        address.setDistrict("Texas");
        address.setPhone("0707877888");
        address.setLocation(point);
        address.setCity(city);
        address.setLastUpdate(new Timestamp(System.currentTimeMillis()));
        adressDAO.create(address);

    }

    @Test
    void testRead() {
        Address address = adressDAO.read(2);
        System.out.println(address.getAddress());

    }

    @Test
    void getAll() {
        List<Address> all = adressDAO.getAll();
        for (Address address : all) {
            System.out.println(address.getAddress());

        }
    }
    @Test
    void testUpdate() {

    }

    @Test
    void testDelete() {

    }

}
