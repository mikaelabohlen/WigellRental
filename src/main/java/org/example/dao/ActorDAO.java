package org.example.dao;

import org.example.entities.Actor;
import org.example.entities.Film;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class ActorDAO extends AbstractDAO<Actor> {

    public ActorDAO() {
        super(Actor.class);
    }

    public List<Actor> findActorByFirstName(String firstName) {
        try (Session session = getSession()) {
            Query<Actor> query = session.createQuery("from Actor where firstName = :firstName", Actor.class);
            query.setParameter("firstName", firstName);
            return query.getResultList();
        }
    }

    public List<Actor> getActorsForFilm(int selectedFilmId) {
        try (Session session = getSession()) {
            session.beginTransaction();
            Query query= session.createQuery("SELECT a FROM Actor a INNER JOIN a.films f WHERE f.id = :filmId");
            query.setParameter("filmId", selectedFilmId);
            List<Actor> actors = query.getResultList();
            session.getTransaction().commit();
            return actors;
        }
    }
}
