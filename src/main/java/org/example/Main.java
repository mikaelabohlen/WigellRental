package org.example;

import javafx.application.Application;
import javafx.stage.Stage;
import org.example.entities.Film;
import org.example.gui.Gui;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Iterator;

public class Main extends Application {
    private Gui gui;
    private Controller controller;
    public static void main(String[] args) {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        Film film = session.get(Film.class, 1);

        Iterator<String> itr = film.getSpecialFeatures().iterator();

        while(itr.hasNext()) {
            System.out.println(itr.next());
        }

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        controller = new Controller();

        gui = new Gui(primaryStage, controller);

        gui.launch();
    }
}