package org.example.dao;

import org.example.entities.Actor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.List;

class ActorDAOTest {

    private ActorDAO actorDAO;
    @BeforeEach
    void setUp() {
        actorDAO = new ActorDAO();
    }

    @Test
    void testCreate() {
        Actor actor = new Actor();
        actor.setFirstName("HULKEN");
        actor.setLastName("BANNER");
        actor.setLastUpdate(new Timestamp(System.currentTimeMillis()));
        actorDAO.create(actor);
    }

    @Test
    void testRead() {
        Actor actor = actorDAO.read(1);
        System.out.println(actor.getFirstName());
    }

    @Test
    void getAll() {
        List<Actor> all = actorDAO.getAll();
        for (Actor actor : all) {
            System.out.println(actor.getFirstName());

        }
    }
    @Test
    void testUpdate() {

    }

    @Test
    void testDelete() {

    }
}