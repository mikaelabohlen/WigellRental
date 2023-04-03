package org.example.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.Controller;
import org.example.dao.FilmDAO;
import org.example.entities.Actor;
import org.example.entities.Category;
import org.example.entities.Film;
import org.example.enums.Rating;
import org.example.utils.TimeUtil;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        private Label titleLabel, descriptionLabel, releaseYearLabel, lengthLabel, ratingLabel, specialFeaturesLabel, actorsLabel;
        private TextField titleTextField, releaseYearTextField, lengthTextField, ratingTextField, specialFeaturesTextField;
        private TextArea descriptionTextArea, actorsTextArea;
        private GridPane labelsTextFieldsGridPane;
        private VBox centerVBox;
        private TableView filmTable;
        private ObservableList<Film> filmObservableList;
        public void setupCenter() {
            centerLabel = new Label();
            titleLabel = new Label("Title:");
            descriptionLabel = new Label("Description:");
            releaseYearLabel = new Label("Release Year:");
            lengthLabel = new Label("Length:");
            ratingLabel = new Label("Rating:");
            specialFeaturesLabel = new Label("Special Features:");
            actorsLabel = new Label("Actors:");

            titleTextField = new TextField();
            releaseYearTextField = new TextField();
            lengthTextField = new TextField();
            ratingTextField = new TextField(); //TODO Detta skall vara en ChoiceBox
            specialFeaturesTextField = new TextField();

            descriptionTextArea = new TextArea();
            descriptionTextArea.setMaxHeight(100);
            descriptionTextArea.setMaxWidth(200);
            descriptionTextArea.setWrapText(true);

            actorsTextArea = new TextArea();
            actorsTextArea.setMaxHeight(100);
            actorsTextArea.setMaxWidth(200);
            actorsTextArea.setWrapText(true);

            labelsTextFieldsGridPane = new GridPane();
            labelsTextFieldsGridPane.setHgap(10);
            labelsTextFieldsGridPane.setVgap(10);
            labelsTextFieldsGridPane.add(titleLabel,0,0,1,1);
            labelsTextFieldsGridPane.add(titleTextField,1,0,1,1);
            labelsTextFieldsGridPane.add(releaseYearLabel, 2,0,1,1);
            labelsTextFieldsGridPane.add(releaseYearTextField, 3,0,1,1);
            labelsTextFieldsGridPane.add(lengthLabel, 4,0,1,1);
            labelsTextFieldsGridPane.add(lengthTextField, 5,0,1,1);
            labelsTextFieldsGridPane.add(ratingLabel, 6,0,1,1);
            labelsTextFieldsGridPane.add(ratingTextField, 7,0,1,1);
            labelsTextFieldsGridPane.add(specialFeaturesLabel, 0,1,1,1);
            labelsTextFieldsGridPane.add(specialFeaturesTextField, 1,1,2,1);
            labelsTextFieldsGridPane.add(descriptionLabel, 0,2,1,1);
            labelsTextFieldsGridPane.add(descriptionTextArea, 1,2,1,1);
            labelsTextFieldsGridPane.add(actorsLabel,2,2,1,1);
            labelsTextFieldsGridPane.add(actorsTextArea,3,2,1,1);

            FilmDAO filmDAO = new FilmDAO();

            List<Film> films = filmDAO.getAll();
            filmObservableList = FXCollections.observableList(films);

            filmTable = new TableView<Film>();
            filmTable.setFocusTraversable(false);
            TableColumn<Film, String> titleColumn = new TableColumn<Film, String>("Title:");
            titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
            TableColumn<Film, Integer> releaseYearColumn = new TableColumn<Film, Integer>("Release Year:");
            releaseYearColumn.setCellValueFactory(new PropertyValueFactory<>("releaseYear"));
            TableColumn<Film, Integer> lengthColumn = new TableColumn<Film, Integer>("Length:");
            lengthColumn.setCellValueFactory(new PropertyValueFactory<>("length"));
            lengthColumn.setCellFactory(column-> formatTimeInTableViewCell());
            TableColumn<Film, Rating> ratingColumn = new TableColumn<Film, Rating>("Rating:");
            ratingColumn.setCellValueFactory(new PropertyValueFactory<>("rating"));
            TableColumn<Film, Timestamp> timeStampColumn = new TableColumn<Film, Timestamp>("Last Update");
            timeStampColumn.setCellValueFactory(new PropertyValueFactory<>("lastUpdate"));
            TableColumn<Film, List<Category>> categoryColumn = new TableColumn<Film, List<Category>>("Kategori");
            categoryColumn.setCellValueFactory(new PropertyValueFactory<>("categories"));
            categoryColumn.setCellFactory(column -> new TableCell<Film, List<Category>>() {
                @Override
                protected void updateItem(List<Category> categories, boolean empty) {
                    super.updateItem(categories, empty);
                    if (empty || categories == null) {
                        setText("");
                    } else {
                        setText(categories.stream().map(Category::getName).collect(Collectors.joining(", ")));
                    }
                }
            });

            TableColumn<Film, List<Actor>> actorsColumn = new TableColumn<Film, List<Actor>>("Actors:");
            actorsColumn.setCellValueFactory(new PropertyValueFactory<>("actors"));
            actorsColumn.setCellFactory(column -> new TableCell<Film, List<Actor>>() {
                @Override
                protected void updateItem(List<Actor> actors, boolean empty) {
                    super.updateItem(actors, empty);
                    if (empty || actors == null) {
                        setText("");
                    } else {
                        setText(actors.stream().map(actor -> actor.getFirstName() + " " + actor.getLastName()).collect(Collectors.joining(", ")));
                    }
                }
            });

            filmTable.setMaxWidth(800);
            filmTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

            filmTable.getColumns().add(titleColumn);
            filmTable.getColumns().add(releaseYearColumn);
            filmTable.getColumns().add(lengthColumn);
            filmTable.getColumns().add(ratingColumn);
            filmTable.getColumns().add(timeStampColumn);
            filmTable.getColumns().add(categoryColumn);
            filmTable.getColumns().add(actorsColumn);
            filmTable.getItems().addAll(filmObservableList);

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
        mainScene = new Scene(mainPane, 1200, 1000);

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

        //CENTER
        handleFilmTable();
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
            center.centerVBox.getChildren().addAll(center.filmTable, center.labelsTextFieldsGridPane);
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

    private void handleFilmTable() {
        center.filmTable.setOnMousePressed(event -> {
            Film selectedFilm = (Film) center.filmTable.getSelectionModel().getSelectedItem();
            if (selectedFilm != null) {
                center.titleTextField.setText(selectedFilm.getTitle());
                center.releaseYearTextField.setText(String.valueOf(selectedFilm.getReleaseYear()));
                center.lengthTextField.setText(TimeUtil.formatTimeMinutesToHourMinutes(selectedFilm.getLength()));
                center.ratingTextField.setText(selectedFilm.getRating().toString());
                center.specialFeaturesTextField.setText(selectedFilm.getSpecialFeatures());
                center.descriptionTextArea.setText(selectedFilm.getDescription());
            }
        });
    }

    private TableCell<Film, Integer> formatTimeInTableViewCell() {
        return new TableCell<Film, Integer>() {
            @Override
            protected void updateItem(Integer minutes, boolean empty) {
                super.updateItem(minutes, empty);
                if(empty || minutes == null) {
                    setText(null);
                }
                else {
                    setText(TimeUtil.formatTimeMinutesToHourMinutes(minutes));
                }
            }
        };
    }
}
