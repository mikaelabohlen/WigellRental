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

    public Actor findActorByFirstAndLastName(String firstName, String lastName) {
        try (Session session = getSession()) {
            Query<Actor> query = session.createQuery("from Actor where firstName = :firstName and lastName = :lastName", Actor.class);
            query.setParameter("firstName", firstName);
            query.setParameter("lastName", lastName);
            return query.uniqueResult();
        }
    }
    public Actor findActorByName(String name) {
        try (Session session = getSession()) {
            String[] names = name.split(" ");
            String firstName = names[0];
            String lastName = names[1];
            Query<Actor> query = session.createQuery("from Actor where concat(firstName, ' ', lastName) like :name", Actor.class);
            query.setParameter("name", "%" + firstName + " " + lastName + "%");
            return query.uniqueResult();
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

    public void deleteAssociations(Actor actor, FilmDAO filmDAO) {
        for (Film film : actor.getFilms()) {
            film.getActors().remove(actor);
            filmDAO.update(film);
        }
        actor.getFilms().clear();
        update(actor);
    }
}
