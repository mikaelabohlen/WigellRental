package org.example.dao;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import org.example.entities.Address;
import org.example.entities.City;
import org.example.entities.Country;
import org.example.entities.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.List;
public class CustomerDAOTest {

    private CustomerDAO customerDAO;
    private AddressDAO addressDAO;
    private CountryDAO countryDAO;
    private CityDAO cityDAO;

    @BeforeEach
    void setup(){
        customerDAO = new CustomerDAO();
        addressDAO = new AddressDAO();
        cityDAO = new CityDAO();
        countryDAO = new CountryDAO();
    }

    @Test
    void testCreate() {

        //address
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
        addressDAO.create(address);
        //customer
        Customer customer = new Customer();
        customer.setFirstName("Sein");
        customer.setLastName("Wood");
        customer.setEmail("SeinWood@gmail.com");
        customer.setActive(true);
        customer.setAddress(address);
        customer.setCreateDate(new Timestamp(System.currentTimeMillis()));
        customer.setLastUpdate(new Timestamp(System.currentTimeMillis()));
        customerDAO.create(customer);

    }

    @Test
    void testRead() {
        Customer customer = customerDAO.read(2);
        System.out.println(customer.getAddress());

    }

    @Test
    void getAll() {
        List<Customer> all = customerDAO.getAll();
        for (Customer customer : all) {
            System.out.println(customer.getAddress());

        }
    }
    @Test
    void testUpdate() {

    }

    @Test
    void testDelete() {

    }

}
