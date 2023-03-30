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
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        controller = new Controller();

        gui = new Gui(primaryStage, controller);

        gui.launch();
    }
}