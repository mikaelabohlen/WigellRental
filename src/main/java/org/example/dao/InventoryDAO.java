package org.example.dao;

import org.example.entities.Actor;
import org.example.entities.Inventory;


import java.util.List;

public class InventoryDAO extends AbstractDAO<Inventory>{
    public InventoryDAO(){super(Inventory.class);}

    public List<Inventory> getInventoriesForFilm(int selectedFilmId) {
        try (Session session = getSession()) {
            session.beginTransaction();

//            Query query= session.createQuery("SELECT i FROM Inventory i INNER JOIN i.films f WHERE f.id = :filmId");
//            query.setParameter("filmId", selectedFilmId);
//            List<Inventory> inventories = query.getResultList();

            String queryString = "SELECT i FROM Inventory i WHERE i.film.id = :filmId";
            Query query = session.createQuery(queryString);
            query.setParameter("filmId", selectedFilmId);
            List<Inventory> inventories = query.getResultList();

            session.getTransaction().commit();
            return inventories;
        }
    }
}
