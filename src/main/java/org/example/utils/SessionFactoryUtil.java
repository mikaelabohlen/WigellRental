package org.example.utils;

import com.sun.beans.TypeResolver;
import com.vividsolutions.jts.geom.GeometryFactory;
import org.example.entities.Address;
import org.geolatte.geom.GeometryType;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import org.hibernate.usertype.UserType;



public class SessionFactoryUtil {

    private static SessionFactory sessionFactory;

    private SessionFactoryUtil() {}
    public static synchronized SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            /////////////////////////// Testkod nedn:
//            Configuration config = new Configuration();
//            config.addAnnotatedClass(Address.class);
//            config.setProperty("hibernate.dialect", "org.hibernate.spatial.dialect.postgis.PostgisDialect");
//            config.setProperty("hibernate.spatial.enabled", "true");
//            config.setProperty("hibernate.spatial.use_implicit_casting", "true");
//            config.setProperty("hibernate.hbm2ddl.auto", "create-drop");
//            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
//                    .applySettings(config.getProperties())
//                    .build();
//            SessionFactory sessionFactory = config.buildSessionFactory(serviceRegistry);
            //////////////////////////Testkod ovanför

            sessionFactory = new Configuration().configure().buildSessionFactory();
        }
        return sessionFactory;
    }
//    static {
//        GeometryFactory geometryFactory = new GeometryFactory();
//        GeometryType type = GeometryType.GEOMETRY_TYPE;//GeometryType customGeometryType = new GeometryType(geometryFactory);
//        customGeometryType.setRegistrationKeys("myGeometry", "myPoint", "myPolygon");
//        TypeResolver.getDefault().registerTypeOverride(customGeometryType);
//    }
}

