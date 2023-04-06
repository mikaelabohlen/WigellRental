package org.example.dao;

import org.example.entities.City;
import org.example.entities.Country;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class CountryDAO extends AbstractDAO{
    public CountryDAO(){super(Country.class);}

    public Country findById(int id) {
        try (Session session = getSession()) {
            Query<Country> query = session.createQuery("from Country where id = :id", Country.class);
            query.setParameter("id", id);
            return query.uniqueResult();
        }
    }
}
