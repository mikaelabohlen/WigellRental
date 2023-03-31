package org.example.utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SessionFactoryUtil {

    private static SessionFactory sessionFactory;

    private SessionFactoryUtil() {}
    public static synchronized SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        }
        return sessionFactory;
    }
}
