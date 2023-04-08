package org.example.dao;

import org.example.entities.Actor;
import org.example.entities.Film;
import org.example.entities.Store;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.TypedQuery;
import java.util.List;

public class FilmDAO extends AbstractDAO<Film>{
    public FilmDAO() {super(Film.class);}

    @Override
    public List<Film> getAll() {
        try (Session session = getSession()) {
            session.beginTransaction();
            List<Film> films = session.createQuery("FROM Film", Film.class).getResultList();
            for (Film film : films) {
                Hibernate.initialize(film.getActors());
            }
            session.getTransaction().commit();
            return films;
        }
    }

    public List<Film> getAllFilmsByStore(Store store) {
        try (Session session = getSession()) {
            session.beginTransaction();
            List<Film> films = session.createQuery("SELECT DISTINCT f FROM Film f JOIN FETCH f.inventories inv WHERE inv.store = :store", Film.class)
                    .setParameter("store", store)
                    .getResultList();
            for (Film film : films) {
                Hibernate.initialize(film.getActors());
            }
            session.getTransaction().commit();
            return films;
        }
    }

    public List<Film> getAllRentedFilmsForCustomer(int customerId) {
        try (Session session = getSession()) {
            session.beginTransaction();
            Query query = session.createQuery("SELECT f FROM Film f JOIN f.inventories i JOIN i.rentals r WHERE r.customer.customerId = :customerId");
            query.setParameter("customerId", customerId);
            List<Film> films = query.getResultList();
            session.getTransaction().commit();
            return films;
        }
    }

    public List<Film> getCurrentlyRentedFilmsForCustomer(int customerId) {
        try (Session session = getSession()) {
            session.beginTransaction();
            Query query = session.createQuery("SELECT f FROM Film f JOIN f.inventories i JOIN i.rentals r WHERE r.customer.customerId = :customerId AND r.rentalDate <= CURRENT_DATE() AND (r.returnDate IS NULL OR r.returnDate >= CURRENT_DATE())");
            query.setParameter("customerId", customerId);
            List<Film> films = query.getResultList();
            session.getTransaction().commit();
            return films;
        }
    }

}

