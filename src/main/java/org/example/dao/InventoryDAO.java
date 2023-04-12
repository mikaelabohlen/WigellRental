package org.example.dao;

import org.example.entities.Inventory;
import org.hibernate.Session;
import org.hibernate.query.Query;


import java.util.List;

public class InventoryDAO extends AbstractDAO<Inventory>{
    public InventoryDAO(){super(Inventory.class);}

    public List<Inventory> getInventoriesForFilm(int selectedFilmId) {
        try (Session session = getSession()) {
            session.beginTransaction();

            String queryString = "SELECT i FROM Inventory i WHERE i.film.id = :filmId";
            Query query = session.createQuery(queryString);
            query.setParameter("filmId", selectedFilmId);
            List<Inventory> inventories = query.getResultList();

            session.getTransaction().commit();
            return inventories;
        }
    }

    public List<Inventory> getInventoriesForStore(int storeId) {
        try (Session session = getSession()) {
            session.beginTransaction();
            String queryString = "SELECT i FROM Inventory i "
                    + "WHERE i.store.id = :storeId "
                    + "AND i NOT IN (SELECT r.inventory FROM Rental r WHERE r.returnDate IS NULL)";
            Query query = session.createQuery(queryString);
            query.setParameter("storeId", storeId);
            List<Inventory> inventories = query.getResultList();
            session.getTransaction().commit();
            return inventories;
        }
    }


}
