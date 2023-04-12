package org.example.dao;


import org.example.entities.Country;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class CountryDAO extends AbstractDAO<Country>{
    public CountryDAO(){super(Country.class);}
}
