package org.example.dao;

import org.example.entities.Address;
import org.example.entities.City;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class AdressDAO extends AbstractDAO<Address> {
    public AdressDAO() {
        super(Address.class);
    }

    //checks if address is in database
    public List<Address> findAddressByName(String address) {
        try (Session session = getSession()) {
            Query<Address> query = session.createQuery("from Address where address = :address", Address.class);
            query.setParameter("address", address);
            return query.getResultList();
        }
    }

    //get all addresses
    public List<Address> getAllAddresses() {
        try (Session session = getSession()) {
            Query<Address> query = session.createQuery("FROM Address", Address.class);
            return query.getResultList();
        }
    }

    //get the postal code for specific address
    public String findPostalCodeByAddress(String address) {
        try (Session session = getSession()) {
            Query<String> query = session.createQuery("select a.postalCode from Address a where a.address = :address", String.class);
            query.setParameter("address", address);
            return query.getSingleResult();
        }
    }

    //get address by addressID
    public Address getAddressById(int id) {
        try (Session session = getSession()) {
            Query<Address> query = session.createQuery("from Address where id = :id", Address.class);
            query.setParameter("id", id);
            return query.uniqueResult();
        }
    }

    // AddressID by address
    public int getAddressIdByAddress(String address) {
        try (Session session = getSession()) {
            Query<Address> query = session.createQuery("from Address where address = :address", Address.class);
            query.setParameter("address", address);
            return query.getSingleResult().getId();
        }
    }


}
