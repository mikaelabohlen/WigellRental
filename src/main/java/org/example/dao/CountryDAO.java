package org.example.dao;

import org.example.entities.Country;

public class CountryDAO extends AbstractDAO<Country> {
    public CountryDAO() {
        super(Country.class);
    }
}
