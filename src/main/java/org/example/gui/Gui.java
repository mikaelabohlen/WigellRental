package org.example.gui;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.Controller;

import java.util.ArrayList;

public class Gui {

    private Controller controller;

    private Stage primaryStage;

    private Scene mainScene;
    private BorderPane mainPane;

    private Top top;
    private Left left;
    public Gui(Stage primaryStage, Controller controller) {
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

            storeLabel = new Label("Store: ");

            choseStoreSubmitButton = new Button("Submit");

            choseStoreChoiceBox = new ChoiceBox<>();

            stores = choseStoreChoiceBox.getItems();
            stores.add("Store One");
            stores.add("Store Two");

            topHBox = new HBox();
            topHBox.setAlignment(Pos.CENTER);
            topHBox.setSpacing(10);
            topHBox.setPadding(new Insets(10,10,10,10));
            topHBox.getChildren().addAll(choseStoreChoiceBox, choseStoreSubmitButton);

            topVBox = new VBox();
            topVBox.setAlignment(Pos.CENTER);
            topVBox.setPadding(new Insets(10, 10, 10, 10));
            topVBox.getChildren().addAll(headerLabel,storeLabel, topHBox);

            mainPane.setTop(topVBox);

        }
    }

    private class Left {
        private Button moviesButton, rentButton, returnButton, customersButton, staffButton;
        private VBox buttonVBox;
        private ArrayList<Button> navButtons;
        public void setupLeft() {
            moviesButton = new Button("Movies");
            rentButton = new Button("Rent");
            returnButton = new Button("Return");
            customersButton = new Button("Customers");
            staffButton = new Button("Staff");

            navButtons = new ArrayList<>();
            navButtons.add(moviesButton);
            navButtons.add(rentButton);
            navButtons.add(returnButton);
            navButtons.add(customersButton);
            navButtons.add(staffButton);

            buttonVBox = new VBox();
            buttonVBox.setAlignment(Pos.TOP_LEFT);
            buttonVBox.setSpacing(20);
            buttonVBox.setPadding(new Insets(50,10,10,20));
            buttonVBox.getChildren().addAll(navButtons);

            mainPane.setLeft(buttonVBox);
        }
    }
    public void launch() {
        top = new Top();
        left = new Left();

        mainPane = new BorderPane();
        mainScene = new Scene(mainPane, 800, 800);

        top.setupTop();
        left.setupLeft();

        buttonActions();

        primaryStage.setResizable(false);
        primaryStage.setTitle("WigellsRental");
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    private void buttonActions() {
        //TOP
        handleChoseStoreSubmitButton();
    }

    private void handleChoseStoreSubmitButton() {
        //TODO CHANGE SUBMIT BUTTON TO CHOICEBOX EVENTHANDLER
        top.choseStoreSubmitButton.setOnMouseClicked(event-> {
            if(top.choseStoreChoiceBox.getValue()==null) {
                return;
            }
            if(top.choseStoreChoiceBox.getValue().equals("Store One")) {
                top.storeLabel.setText("Store: 'Store One'");
            }
            else {
                top.storeLabel.setText("Store: 'Store Two'");
            }
        });
    }
}
