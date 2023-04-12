package org.example.dao;

import org.example.entities.Address;
import org.example.entities.City;
import org.example.entities.Country;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class CityDAO extends AbstractDAO<City> {
    public CityDAO() {
        super(City.class);
    }

    //get city by country
    public List<City> getCitiesByCountry(Country country) {
        try (Session session = getSession()) {
            Query<City> query = session.createQuery("from City where country = :country", City.class);
            query.setParameter("country", country);
            return query.getResultList();
        }
    }

    //get all cities
    public List<City> getAllCities() {
        try (Session session = getSession()) {
            Query<City> query = session.createQuery("from City", City.class);
            return query.getResultList();
        }
    }


    //checks if city is in database
    public List<City> findCityByName(String city) {
        try (Session session = getSession()) {
            Query<City> query = session.createQuery("from City where city = :city", City.class);
            query.setParameter("city", city);
            return query.getResultList();
        }
    }

    public City getCityById(int id) {
        try (Session session = getSession()) {
            Query<City> query = session.createQuery("from City where id = :id", City.class);
            query.setParameter("id", id);
            return query.uniqueResult();
        }
    }

    public City getIdByCity(String city) {
        try (Session session = getSession()) {
            Query<City> query = session.createQuery("from City where city = :city", City.class);
            query.setParameter("city", city);
            return query.getSingleResult();
        }
    }

}
