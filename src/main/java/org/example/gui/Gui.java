package org.example.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.Controller;
import org.example.dao.FilmDAO;
import org.example.entities.Film;

import java.util.ArrayList;
import java.util.List;

public class Gui {

    private Controller controller;

    private Stage primaryStage;

    private Scene mainScene;
    private BorderPane mainPane;

    private Top top;
    private Left left;
    private Center center;

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
            choseStoreSubmitButton.setFocusTraversable(false);

            choseStoreChoiceBox = new ChoiceBox<>();
            choseStoreChoiceBox.setFocusTraversable(false);

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
            moviesButton.setFocusTraversable(false);
            rentButton = new Button("Rent");
            rentButton.setFocusTraversable(false);
            returnButton = new Button("Return");
            returnButton.setFocusTraversable(false);
            customersButton = new Button("Customers");
            customersButton.setFocusTraversable(false);
            staffButton = new Button("Staff");
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
            buttonVBox.setPadding(new Insets(50,10,10,20));
            buttonVBox.getChildren().addAll(navButtons);

            mainPane.setLeft(buttonVBox);
        }
    }

    private class Center {
        private Label centerLabel;
        private VBox centerVBox;
        private TableView table;
        private ObservableList<Film> observableList;
        public void setupCenter() {
            centerLabel = new Label();
            FilmDAO filmDAO = new FilmDAO();

            List<Film> films = filmDAO.getAll();
            observableList = FXCollections.observableList(films);

            for(int i=0; i<films.size(); i++) {
                System.out.println(films.get(i).getTitle());
            }

            table = new TableView<Film>();
            TableColumn<Film, String> titleColumn = new TableColumn<Film, String>("Title:");
            titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
            TableColumn<Film, Integer> releaseYearColumn = new TableColumn<Film, Integer>("Release Year:");
            releaseYearColumn.setCellValueFactory(new PropertyValueFactory<>("releaseYear"));
            table.getColumns().add(titleColumn);
            table.getColumns().add(releaseYearColumn);
            table.getItems().addAll(observableList);

            centerVBox = new VBox();
            centerVBox.setAlignment(Pos.TOP_CENTER);
            centerVBox.setPadding(new Insets(10,10,10,10));
            centerVBox.setSpacing(10);
            center.centerVBox.getChildren().add(centerLabel);

            mainPane.setCenter(centerVBox);
        }
    }

    public void launch() {
        top = new Top();
        left = new Left();
        center = new Center();

        mainPane = new BorderPane();
        mainScene = new Scene(mainPane, 800, 800);

        top.setupTop();
        left.setupLeft();
        center.setupCenter();

        disableNavButtons();
        buttonActions();

        primaryStage.setResizable(false);
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
        left.customersButton.setOnMouseClicked(event-> {
            enableNavButtons();
            left.customersButton.setDisable(true);
            center.centerLabel.setText("Customer");
        });
    }

    private void handleStaffButton() {
        left.staffButton.setOnMouseClicked(event-> {
            enableNavButtons();
            left.staffButton.setDisable(true);
            center.centerLabel.setText("Staff");
        });
    }

    private void handleReturnButton() {
        left.returnButton.setOnMouseClicked(event-> {
            enableNavButtons();
            left.returnButton.setDisable(true);
            center.centerLabel.setText("Return");
        });
    }

    private void handleRentButton() {
        left.rentButton.setOnMouseClicked(event-> {
            enableNavButtons();
            left.rentButton.setDisable(true);
            center.centerLabel.setText("Rent");
        });
    }

    private void handleMoviesButton() {
        left.moviesButton.setOnMouseClicked(event-> {
            enableNavButtons();
            left.moviesButton.setDisable(true);
            center.centerVBox.getChildren().clear();
            center.centerVBox.getChildren().add(center.table);
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
        top.choseStoreSubmitButton.setOnMouseClicked(event-> {
            if(top.choseStoreChoiceBox.getValue()==null) {
                return;
            }
            if(top.choseStoreChoiceBox.getValue().equals("Store One")) {
                top.storeLabel.setText("Store: 'Store One'");
                enableNavButtons();
            }
            else {
                top.storeLabel.setText("Store: 'Store Two'");
                enableNavButtons();
            }
        });
    }
}
