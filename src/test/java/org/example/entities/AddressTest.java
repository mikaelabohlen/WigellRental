package org.example.entities;

import org.example.dao.AddressDAO;
import org.junit.jupiter.api.Test;

import java.util.List;

class AddressTest {
    AddressDAO addressDAO = new AddressDAO();
    @Test
    void getAllAddress() {
        List<Address> allAddresses = addressDAO.getAll();
        for(Address address: allAddresses){
            System.out.println(address.getLocation());
        }
    }
}