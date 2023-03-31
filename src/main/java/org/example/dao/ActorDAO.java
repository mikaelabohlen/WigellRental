package org.example.dao;

import org.example.entities.Actor;
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
}
