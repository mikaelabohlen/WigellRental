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
}

