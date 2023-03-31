package org.example.dao;

import org.example.entities.Actor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        actorDAO.create(actor);
    }

    @Test
    void testRead() {
        Actor actor = actorDAO.read(1);
        System.out.println(actor.getFirstName());
    }

    @Test
    void testUpdate() {

    }

    @Test
    void testDelete() {

    }
}