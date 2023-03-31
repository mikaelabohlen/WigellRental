package org.example.dao;
import org.example.entities.Customer;
import org.hibernate.Session;
import org.hibernate.query.Query;
public class CustomerDAO extends AbstractDAO<Customer>{
    public CustomerDAO(){super(Customer.class);}
}
