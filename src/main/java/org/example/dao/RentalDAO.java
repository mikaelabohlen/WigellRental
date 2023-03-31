package org.example.dao;
import org.example.entities.Rental;
import org.hibernate.Session;
import org.hibernate.query.Query;
public class RentalDAO extends AbstractDAO<Rental>{
    public RentalDAO() {super(Rental.class);}
}
