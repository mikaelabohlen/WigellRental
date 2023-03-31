package org.example.dao;

import org.example.utils.SessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public abstract class AbstractDAO<T> {

    private final Class<T> entityClass;

    public AbstractDAO(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public T create(T entity) {
        try (Session session = getSession()) {
            session.beginTransaction();
            session.save(entity);
            session.getTransaction().commit();
        }

        return entity;
    }

    public T read(int id) {
        try (Session session = getSession()) {
            session.beginTransaction();
            T entity = session.get(entityClass, id);
            session.getTransaction().commit();
            return entity;
        }
    }

    public List<T> getAll() {
        try (Session session = getSession()) {
            session.beginTransaction();
            List<T> entities = session.createQuery("FROM " + entityClass.getName(), entityClass).getResultList();
            session.getTransaction().commit();
            return entities;
        }
    }

    public T update(T entity) {
        try (Session session = getSession()) {
            session.beginTransaction();
            session.update(entity);
            session.getTransaction().commit();
        }
        return entity;
    }

    public void delete(int id) {
        try (Session session = getSession()) {
            session.beginTransaction();
            T entity = session.get(entityClass, id);
            session.delete(entity);
            session.getTransaction().commit();
        }
    }

    protected Session getSession() {
        SessionFactory sessionFactory = SessionFactoryUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        return session;
    }
}

