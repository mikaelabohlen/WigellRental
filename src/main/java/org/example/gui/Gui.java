package org.example.gui;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.Controller;
import org.example.dao.ActorDAO;
import org.example.dao.CategoryDAO;
import org.example.dao.FilmDAO;
import org.example.dao.LanguageDAO;
import org.example.entities.Actor;
import org.example.entities.Category;
import org.example.entities.Film;
import org.example.entities.Language;
import org.example.enums.Rating;
import org.example.utils.TimeUtil;

import java.math.BigDecimal;
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

    private FilmDAO filmDAO;

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
        private Label titleLabel, descriptionLabel, releaseYearLabel, languageLabel, lengthLabel, ratingLabel, categoryLabel, specialFeaturesLabel, actorsLabel;
        private TextField titleTextField, releaseYearTextField, lengthTextField, specialFeaturesTextField;
        private TextField searchTextFieldTitle, searchTextFieldActor;
        private TextArea descriptionTextArea, actorsTextArea;
        private ListView<String> actorsListView;
        private GridPane labelsTextFieldsGridPane;
        private Button addMovieButton, deleteMovieButton, updateMovieButton;
        private VBox centerVBox;
        private HBox searchHBox;
        private TableView<Film> filmTable;
        private ChoiceBox<String> categoryChoiceBox;
        private ChoiceBox<String> categoryChoiceBox2;
        private ChoiceBox<Rating> ratingChoiceBox;
        private ChoiceBox<Rating> ratingChoiceBox2;
        private ChoiceBox<String> languageChoiceBox;
        private TableColumn<Film, String> titleColumn;
        private TableColumn<Film, Integer> releaseYearColumn, lengthColumn;
        private TableColumn<Film, Rating> ratingColumn;
        private TableColumn<Film, String> categoryColumn;
        private TableColumn<Film, List<Actor>> actorsColumn;
        private ObservableList<Film> filmObservableList;
        private ObservableList<Language> languageObservableList;
        private ObservableList<Category> categoryObservableList;

        public void setupCenter() {
            centerLabel = new Label();
            titleLabel = new Label("Title:");
            descriptionLabel = new Label("Description:");
            releaseYearLabel = new Label("Release Year:");
            lengthLabel = new Label("Length:");
            categoryLabel = new Label("Category:");
            specialFeaturesLabel = new Label("Special Features:");
            actorsLabel = new Label("Actors:");
            ratingLabel = new Label("Rating:");
            languageLabel = new Label("Language:");

            addMovieButton = new Button("Add Movie");
            updateMovieButton = new Button("Update Movie");
            deleteMovieButton = new Button("Delete Movie");

            titleTextField = new TextField();
            releaseYearTextField = new TextField();
            lengthTextField = new TextField();
            specialFeaturesTextField = new TextField();
            searchTextFieldTitle = new TextField();
            searchTextFieldTitle.setFocusTraversable(false);
            searchTextFieldActor = new TextField();
            searchTextFieldActor.setFocusTraversable(false);

            descriptionTextArea = new TextArea();
            descriptionTextArea.setMaxHeight(100);
            descriptionTextArea.setMaxWidth(200);
            descriptionTextArea.setWrapText(true);

            actorsTextArea = new TextArea();
            actorsTextArea.setMaxHeight(100);
            actorsTextArea.setMaxWidth(200);
            actorsTextArea.setWrapText(true);

            actorsListView = new ListView<>();
            actorsListView.setMaxHeight(100);
            actorsListView.setMaxWidth(200);
            actorsListView.setEditable(true);
            actorsListView.setCellFactory(TextFieldListCell.forListView());

            categoryChoiceBox = new ChoiceBox<>();
            categoryChoiceBox2 = new ChoiceBox<>();
            ratingChoiceBox = new ChoiceBox<>();
            ratingChoiceBox2 = new ChoiceBox<>();
            languageChoiceBox = new ChoiceBox<>();

            filmDAO = new FilmDAO();

            CategoryDAO categoryDAO = new CategoryDAO();
            LanguageDAO languageDAO = new LanguageDAO();

            List<Category> categoryList = categoryDAO.getAll();
            List<Film> films = filmDAO.getAll();
            List<Language> languages = languageDAO.getAll();

            filmObservableList = FXCollections.observableList(films);
            languageObservableList = FXCollections.observableList(languages);
            categoryObservableList = FXCollections.observableList(categoryList);

            for (Language language : languages) {
                languageChoiceBox.getItems().add(language.getName());
            }

            categoryChoiceBox.getItems().add("All Categories");
            for (Category category : categoryList) {
                categoryChoiceBox.getItems().add(category.getName());
            }
            categoryChoiceBox.setValue("All Categories");

            for (Category category : categoryList) {
                categoryChoiceBox2.getItems().add(category.getName());
            }

            ratingChoiceBox.getItems().add(Rating.ALL);
            ratingChoiceBox.getItems().add(Rating.G);
            ratingChoiceBox.getItems().add(Rating.PG);
            ratingChoiceBox.getItems().add(Rating.PG13);
            ratingChoiceBox.getItems().add(Rating.NC17);
            ratingChoiceBox.getItems().add(Rating.R);
            ratingChoiceBox.setValue(Rating.ALL);

            ratingChoiceBox2.getItems().add(Rating.G);
            ratingChoiceBox2.getItems().add(Rating.PG);
            ratingChoiceBox2.getItems().add(Rating.PG13);
            ratingChoiceBox2.getItems().add(Rating.NC17);
            ratingChoiceBox2.getItems().add(Rating.R);

            filmTable = new TableView<Film>();
            filmTable.setFocusTraversable(false);

            setupFilmTable();

            filmTable.getColumns().add(titleColumn);
            filmTable.getColumns().add(releaseYearColumn);
            filmTable.getColumns().add(lengthColumn);
            filmTable.getColumns().add(ratingColumn);
            filmTable.getColumns().add(categoryColumn);
            filmTable.getColumns().add(actorsColumn);
            filmTable.getItems().addAll(filmObservableList);

            filmTable.setMaxWidth(800);
            filmTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

            labelsTextFieldsGridPane = new GridPane();
            labelsTextFieldsGridPane.setAlignment(Pos.CENTER);
            labelsTextFieldsGridPane.setHgap(10);
            labelsTextFieldsGridPane.setVgap(10);
            labelsTextFieldsGridPane.add(titleLabel, 0, 0, 1, 1);
            labelsTextFieldsGridPane.add(titleTextField, 1, 0, 1, 1);
            labelsTextFieldsGridPane.add(releaseYearLabel, 2, 0, 1, 1);
            labelsTextFieldsGridPane.add(releaseYearTextField, 3, 0, 1, 1);
            labelsTextFieldsGridPane.add(lengthLabel, 4, 0, 1, 1);
            labelsTextFieldsGridPane.add(lengthTextField, 5, 0, 1, 1);
            labelsTextFieldsGridPane.add(categoryLabel, 6, 0, 1, 1);
            labelsTextFieldsGridPane.add(categoryChoiceBox2, 7, 0, 1, 1);
            labelsTextFieldsGridPane.add(languageLabel, 4, 1, 1, 1);
            labelsTextFieldsGridPane.add(languageChoiceBox, 5, 1, 1, 1);
            labelsTextFieldsGridPane.add(ratingLabel, 6, 1, 1, 1);
            labelsTextFieldsGridPane.add(ratingChoiceBox2, 7, 1, 1, 1);
            labelsTextFieldsGridPane.add(specialFeaturesLabel, 0, 1, 1, 1);
            labelsTextFieldsGridPane.add(specialFeaturesTextField, 1, 1, 2, 1);
            labelsTextFieldsGridPane.add(descriptionLabel, 0, 2, 1, 1);
            labelsTextFieldsGridPane.add(descriptionTextArea, 1, 2, 1, 1);
            labelsTextFieldsGridPane.add(actorsLabel, 2, 2, 1, 1);
            labelsTextFieldsGridPane.add(actorsListView, 3, 2, 1, 1);
            labelsTextFieldsGridPane.add(addMovieButton, 0, 3, 1, 1);
            labelsTextFieldsGridPane.add(deleteMovieButton, 0, 4, 1, 1);
            labelsTextFieldsGridPane.add(updateMovieButton, 0, 5, 1, 1);

            searchHBox = new HBox();
            searchHBox.setAlignment(Pos.CENTER);
            searchHBox.setPadding(new Insets(10, 10, 10, 10));
            searchHBox.setSpacing(10);
            searchHBox.getChildren().addAll(searchTextFieldTitle, searchTextFieldActor, ratingChoiceBox, categoryChoiceBox);

            centerVBox = new VBox();
            centerVBox.setAlignment(Pos.TOP_CENTER);
            centerVBox.setPadding(new Insets(10, 10, 10, 10));
            centerVBox.setSpacing(10);
            center.centerVBox.getChildren().add(centerLabel);


            mainPane.setCenter(centerVBox);
        }

        private void setupFilmTable() {
            titleColumn = new TableColumn<Film, String>("Title:");
            titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

            releaseYearColumn = new TableColumn<Film, Integer>("Release Year:");
            releaseYearColumn.setCellValueFactory(new PropertyValueFactory<>("releaseYear"));

            lengthColumn = new TableColumn<Film, Integer>("Length:");
            lengthColumn.setCellValueFactory(new PropertyValueFactory<>("length"));
            lengthColumn.setCellFactory(column-> formatTimeInTableViewCell());

            ratingColumn = new TableColumn<Film, Rating>("Rating:");
            ratingColumn.setCellValueFactory(new PropertyValueFactory<>("rating"));

            categoryColumn = new TableColumn<Film, String>("Category:");
            categoryColumn.setCellValueFactory(cellData -> {
                Category category = cellData.getValue().getCategory();
                String categoryName = (category == null) ? "" : category.getName();
                return new SimpleStringProperty(categoryName);
            });

            actorsColumn = new TableColumn<Film, List<Actor>>("Actors:");
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
        filterTable();

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

        //CENTER
        handleFilmTable();
        handleAddMovieButton();
        handleDeleteMovieButton();
        handleUpdateMovieButton();
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
            center.centerVBox.getChildren().addAll(center.searchHBox,center.filmTable, center.labelsTextFieldsGridPane);
        });
    }

    private void handleAddMovieButton() {
        //TODO KAN DENNA LÖSAS SNYGGARE/BÄTTRE????? ANTAGLIGEN MEN HUR
        center.addMovieButton.setOnMouseClicked(event-> {
            String selectedTitle = center.titleTextField.getText();
            String selectedDescription = center.descriptionTextArea.getText();
            int selectedReleaseYear = Integer.parseInt(center.releaseYearTextField.getText());
            String languageChoiceBox = center.languageChoiceBox.getValue();
            String categoryChoiceBox = center.categoryChoiceBox2.getValue();

            Category selectedCategory = null;
            Language selectedLanguage = null;
            Rating selectedRating = center.ratingChoiceBox2.getValue();
            System.out.println(center.ratingChoiceBox2.getValue().getRating());

            double defaultRentalRate = 4.99;
            double defaultReplacementCost = 19.99;
            /*BigDecimal defaultRentalRate = new BigDecimal("4.99");
            BigDecimal defaultReplacementCost = new BigDecimal("19.99");*/

            for(Category category : center.categoryObservableList) {
                if(category.getName().equals(categoryChoiceBox)) {
                    selectedCategory = category;
                    break;
                }
            }

            for(Language language : center.languageObservableList) {
                if(language.getName().equals(languageChoiceBox)) {
                    selectedLanguage = language;
                    break;
                }
            }

            Film film = new Film();
            film.setTitle(selectedTitle);
            film.setDescription(selectedDescription);
            film.setReleaseYear(selectedReleaseYear);
            film.setLanguage(selectedLanguage);
            film.setRentalRate(BigDecimal.valueOf(defaultRentalRate));
            film.setReplacementCost(BigDecimal.valueOf(defaultReplacementCost));
            film.setLastUpdate(new Timestamp(System.currentTimeMillis()));
            film.setRating(selectedRating);
            film.setCategory(selectedCategory);
            film.setLastUpdate(new Timestamp(System.currentTimeMillis()));
            //TODO HURFAN FÅR MAN TILL MED SETACTORS?
            filmDAO.create(film);


            updateFilmTable();
            filterTable();
        });
    }

    private void handleUpdateMovieButton() {
        center.updateMovieButton.setOnMouseClicked(event-> {
            //TODO Fixa detta
        });
    }

    private void handleDeleteMovieButton() {
        center.deleteMovieButton.setOnMouseClicked(event-> {
            //TODO fixa detta
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
            Film selectedFilm = center.filmTable.getSelectionModel().getSelectedItem();
            List<Actor> actorList = selectedFilm.getActors();
            if (selectedFilm != null) {
                String actors="";
                for(int i=0; i<selectedFilm.getActors().size(); i++) {
                    actors += selectedFilm.getActors().get(i).getFirstName() + " " + selectedFilm.getActors().get(i).getLastName() + "\n";
                }
                center.titleTextField.setText(selectedFilm.getTitle());
                center.releaseYearTextField.setText(String.valueOf(selectedFilm.getReleaseYear()));
                center.lengthTextField.setText(TimeUtil.formatTimeMinutesToHourMinutes(selectedFilm.getLength()));
                center.categoryChoiceBox2.setValue(selectedFilm.getCategory().getName());
                center.ratingChoiceBox2.setValue(selectedFilm.getRating());
                center.languageChoiceBox.setValue(selectedFilm.getLanguage().getName());
                center.specialFeaturesTextField.setText(selectedFilm.getSpecialFeatures());
                center.descriptionTextArea.setText(selectedFilm.getDescription());
                center.actorsListView.getItems().clear();
                for (Actor actor : actorList) {
                    center.actorsListView.getItems().add(actor.getFirstName() + " " + actor.getLastName());
                }
            }
        });
    }

    private void filterTable() {
        FilteredList<Film> filteredList = new FilteredList<>(center.filmObservableList, p -> true);
        center.searchTextFieldTitle.setPromptText("Search by title...");
        center.searchTextFieldTitle.setOnKeyReleased(keyEvent -> {
            String searchTitle = center.searchTextFieldTitle.getText().toLowerCase();
            String searchActor = center.searchTextFieldActor.getText().toLowerCase();
            String searchCategory = center.categoryChoiceBox.getSelectionModel().getSelectedItem().toLowerCase();
            Rating searchRating = center.ratingChoiceBox.getSelectionModel().getSelectedItem();
            filteredList.setPredicate(film -> {
                boolean titleMatch = film.getTitle().toLowerCase().startsWith(searchTitle);
                boolean actorMatch = film.getActors().stream().anyMatch(actor ->
                        (actor.getFirstName() + " " + actor.getLastName()).toLowerCase().startsWith(searchActor)
                );
                boolean categoryMatch = searchCategory.isEmpty() || searchCategory.equalsIgnoreCase("all categories") || (film.getCategory() != null && film.getCategory().getName().toLowerCase().contains(searchCategory.toLowerCase()));
                boolean ratingMatch = searchRating.equals(Rating.ALL) || film.getRating().equals(searchRating);
                return titleMatch && actorMatch && categoryMatch && ratingMatch;
            });
        });

        center.searchTextFieldActor.setPromptText("Search by actor...");
        center.searchTextFieldActor.setOnKeyReleased(keyEvent -> {
            String searchTitle = center.searchTextFieldTitle.getText().toLowerCase();
            String searchActor = center.searchTextFieldActor.getText().toLowerCase();
            String searchCategory = center.categoryChoiceBox.getSelectionModel().getSelectedItem().toLowerCase();
            Rating searchRating = center.ratingChoiceBox.getSelectionModel().getSelectedItem();
            filteredList.setPredicate(film -> {
                boolean titleMatch = film.getTitle().toLowerCase().startsWith(searchTitle);
                boolean actorMatch = film.getActors().stream().anyMatch(actor ->
                        (actor.getFirstName() + " " + actor.getLastName()).toLowerCase().startsWith(searchActor)
                );
                boolean categoryMatch = searchCategory.isEmpty() || searchCategory.equalsIgnoreCase("all categories") || (film.getCategory() != null && film.getCategory().getName().toLowerCase().contains(searchCategory.toLowerCase()));
                boolean ratingMatch = searchRating.equals(Rating.ALL) || film.getRating().equals(searchRating);
                return titleMatch && actorMatch && categoryMatch && ratingMatch;
            });
        });

        center.categoryChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            String searchTitle = center.searchTextFieldTitle.getText().toLowerCase();
            String searchActor = center.searchTextFieldActor.getText().toLowerCase();
            String searchCategory = newValue.toLowerCase();
            Rating searchRating = center.ratingChoiceBox.getSelectionModel().getSelectedItem();
            filteredList.setPredicate(film -> {
                boolean titleMatch = film.getTitle().toLowerCase().startsWith(searchTitle);
                boolean actorMatch = film.getActors().stream().anyMatch(actor ->
                        (actor.getFirstName() + " " + actor.getLastName()).toLowerCase().startsWith(searchActor)
                );
                boolean categoryMatch = searchCategory.isEmpty() || searchCategory.equalsIgnoreCase("all categories") || (film.getCategory() != null && film.getCategory().getName().toLowerCase().contains(searchCategory.toLowerCase()));
                boolean ratingMatch = searchRating.equals(Rating.ALL) || film.getRating().equals(searchRating);
                return titleMatch && actorMatch && categoryMatch && ratingMatch;
            });
        });

        center.ratingChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            String searchTitle = center.searchTextFieldTitle.getText().toLowerCase();
            String searchActor = center.searchTextFieldActor.getText().toLowerCase();
            String searchCategory = center.categoryChoiceBox.getSelectionModel().getSelectedItem().toLowerCase();
            Rating searchRating = newValue;
            filteredList.setPredicate(film -> {
                boolean titleMatch = film.getTitle().toLowerCase().startsWith(searchTitle);
                boolean actorMatch = film.getActors().stream().anyMatch(actor ->
                        (actor.getFirstName() + " " + actor.getLastName()).toLowerCase().startsWith(searchActor)
                );
                boolean categoryMatch = searchCategory.isEmpty() || searchCategory.equalsIgnoreCase("all categories") || (film.getCategory() != null && film.getCategory().getName().toLowerCase().contains(searchCategory.toLowerCase()));
                boolean ratingMatch = searchRating.equals(Rating.ALL) || film.getRating().equals(searchRating);
                return titleMatch && actorMatch && categoryMatch && ratingMatch;
            });
        });

        center.filmTable.setItems(filteredList);
    }

    private void updateFilmTable() {
        center.filmObservableList = FXCollections.observableList(filmDAO.getAll());
        center.filmTable.setItems(center.filmObservableList);
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
