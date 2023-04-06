package org.example.dao;

import org.example.entities.Actor;
import org.example.entities.Film;
import org.hibernate.Hibernate;
import org.hibernate.Session;

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
}

