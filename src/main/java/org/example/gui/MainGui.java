package org.example.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.Controller;
import org.example.dao.FilmDAO;
import org.example.entities.*;
import org.example.enums.Rating;
import org.example.utils.TimeUtil;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class MainGui {

    private Controller controller;

    private Stage primaryStage;

    private Scene mainScene;
    private BorderPane mainPane;

    private Top top;
    private Left left;
    private CustomersGui customersGui;
    private MoviesGui moviesGui;


    public MainGui(Stage primaryStage, Controller controller) {
        this.primaryStage = primaryStage;
        this.controller = controller;
    }

    private class Top {
        //TOP MAIN
        private Label headerLabel, storeLabel;
        private VBox topVBox;
        private HBox topHBox;
        private ChoiceBox<String> choseStoreChoiceBox;
        private ObservableList<String> stores;
        private Button choseStoreSubmitButton;

        public void setupTop() {
            //TOP MAIN
            headerLabel = new Label("Wigell Rental");
            headerLabel.setStyle("-fx-font-size: 40");

            storeLabel = new Label("Butik: ");

            choseStoreSubmitButton = new Button("Välj");
            choseStoreSubmitButton.setFocusTraversable(false);

            choseStoreChoiceBox = new ChoiceBox<>();
            choseStoreChoiceBox.setFocusTraversable(false);

            stores = choseStoreChoiceBox.getItems();
            stores.add("Butik 1");
            stores.add("Butik 2");

            topHBox = new HBox();
            topHBox.setAlignment(Pos.CENTER);
            topHBox.setSpacing(10);
            topHBox.setPadding(new Insets(10, 10, 10, 10));
            topHBox.getChildren().addAll(choseStoreChoiceBox, choseStoreSubmitButton);

            topVBox = new VBox();
            topVBox.setAlignment(Pos.CENTER);
            topVBox.setPadding(new Insets(10, 10, 10, 10));
            topVBox.getChildren().addAll(headerLabel, storeLabel, topHBox);

            mainPane.setTop(topVBox);

        }
    }

    private class Left {
        private Button moviesButton, rentButton, returnButton, customersButton, staffButton;
        private VBox buttonVBox;
        private ArrayList<Button> navButtons;

        public void setupLeft() {
            moviesButton = new Button("Filmer");
            moviesButton.setFocusTraversable(false);
            rentButton = new Button("Hyr");
            rentButton.setFocusTraversable(false);
            returnButton = new Button("Lämna tillbaka");
            returnButton.setFocusTraversable(false);
            customersButton = new Button("Kunder");
            customersButton.setFocusTraversable(false);
            staffButton = new Button("Personal");
            staffButton.setFocusTraversable(false);

            navButtons = new ArrayList<>();
            navButtons.add(moviesButton);
            navButtons.add(rentButton);
            navButtons.add(returnButton);
            navButtons.add(customersButton);
            navButtons.add(staffButton);

            buttonVBox = new VBox();
            buttonVBox.setAlignment(Pos.TOP_LEFT);
            buttonVBox.setSpacing(20);
            buttonVBox.setPadding(new Insets(50, 10, 10, 20));
            buttonVBox.getChildren().addAll(navButtons);

            mainPane.setLeft(buttonVBox);
        }
    }

    public void launch() {
        top = new Top();
        left = new Left();
        customersGui = new CustomersGui(controller);
        moviesGui = new MoviesGui(controller);

        mainPane = new BorderPane();
        mainScene = new Scene(mainPane, 1200, 1000);
        mainScene.getStylesheets().add("style.css");

        top.setupTop();
        left.setupLeft();
        buttonActions();
        disableNavButtons();

        primaryStage.setResizable(false);
        primaryStage.setMaximized(true);
        primaryStage.setTitle("WigellsRental");
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    private void buttonActions() {
        //TOP
        handleChoseStoreSubmitButton();

        //LEFT
        handleMoviesButton();
        handleRentButton();
        handleReturnButton();
        handleStaffButton();
        handleCustomerButton();

    }

    private void handleCustomerButton() {
        left.customersButton.setOnMouseClicked(event -> {
            mainPane.setCenter(null);
            enableNavButtons();
            left.customersButton.setDisable(true);
            mainPane.setCenter(customersGui.setViewToCustomers());
        });
    }

    private void handleStaffButton() {
        left.staffButton.setOnMouseClicked(event -> {
            enableNavButtons();
            left.staffButton.setDisable(true);
        });
    }

    private void handleReturnButton() {
        left.returnButton.setOnMouseClicked(event -> {
            enableNavButtons();
            left.returnButton.setDisable(true);
        });
    }

    private void handleRentButton() {
        left.rentButton.setOnMouseClicked(event -> {
            enableNavButtons();
            left.rentButton.setDisable(true);
        });
    }

    private void handleMoviesButton() {
        left.moviesButton.setOnMouseClicked(event -> {
            mainPane.setCenter(null);
            enableNavButtons();
            left.moviesButton.setDisable(true);
            mainPane.setCenter(moviesGui.setViewToMovies());
        });
    }

    private void enableNavButtons() {
        left.moviesButton.setDisable(false);
        left.rentButton.setDisable(false);
        left.returnButton.setDisable(false);
        left.customersButton.setDisable(false);
        left.staffButton.setDisable(false);
    }

    private void disableNavButtons() {
        left.moviesButton.setDisable(true);
        left.rentButton.setDisable(true);
        left.returnButton.setDisable(true);
        left.customersButton.setDisable(true);
        left.staffButton.setDisable(true);
    }

    private void handleChoseStoreSubmitButton() {
        //TODO CHANGE SUBMIT BUTTON TO CHOICEBOX EVENTHANDLER
        top.choseStoreSubmitButton.setOnMouseClicked(event -> {
            if (top.choseStoreChoiceBox.getValue() == null) {
                return;
            }
            if (top.choseStoreChoiceBox.getValue().equals("Butik 1")) {
                top.storeLabel.setText("Butik: 'Butik 1'");
                controller.setActiveStore("1");
            } else {
                top.storeLabel.setText("Butik: 'Butik 2'");
                controller.setActiveStore("2");
            }
            enableNavButtons();
            mainPane.setCenter(null);
            initiate();
        });
    }

    private void initiate() {
        moviesGui.setupMovies();
        customersGui.setupCustomers();
        moviesGui.moviesButtonsAndEvents();
        customersGui.customerButtonsAndEvents();
    }
}
