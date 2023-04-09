package org.example.dao;
import org.example.entities.Inventory;
import org.example.entities.Rental;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class RentalDAO extends AbstractDAO<Rental>{
    public RentalDAO() {super(Rental.class);}


    public List<Rental> getRentalsForInventory(int inventoryId) {
        try (Session session = getSession()) {
            session.beginTransaction();

            String queryString = "SELECT r FROM Rental r WHERE r.inventory.id = :inventoryId";
            Query query = session.createQuery(queryString);
            query.setParameter("inventoryId", inventoryId);
            List<Rental> rentals = query.getResultList();

            session.getTransaction().commit();
            return rentals;
        }
    }

    public List<Rental> getRentalsForCustomer(int customerId) {
        try (Session session = getSession()) {
            session.beginTransaction();

            String queryString = "SELECT r FROM Rental r WHERE r.customer.id = :customerId";
            Query query = session.createQuery(queryString);
            query.setParameter("customerId", customerId);
            List<Rental> rentals = query.getResultList();

            session.getTransaction().commit();
            return rentals;
        }
    }

}
