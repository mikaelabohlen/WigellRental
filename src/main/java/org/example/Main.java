package org.example;

import javafx.application.Application;
import javafx.stage.Stage;
import org.example.dao.*;
import org.example.gui.MainGui;

public class Main extends Application {
    private MainGui mainGui;
    private Controller controller;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        controller = new Controller(new ActorDAO(), new AddressDAO(), new CategoryDAO(), new CityDAO(), new CustomerDAO(), new FilmDAO(), new InventoryDAO(), new LanguageDAO(), new PaymentDAO(), new RentalDAO(), new StaffDAO(), new StoreDAO());

        mainGui = new MainGui(primaryStage, controller);

        mainGui.launch();
    }
}