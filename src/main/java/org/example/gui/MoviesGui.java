package org.example.gui;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import org.example.Controller;
import org.example.entities.*;
import org.example.enums.Rating;
import org.example.utils.TimeUtil;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;


public class MoviesGui {

    private Controller controller;

    private Label titleLabel, descriptionLabel, releaseYearLabel, languageLabel, lengthLabel, ratingLabel, categoryLabel, specialFeaturesLabel, actorsLabel;
    private Label actorFirstNameLabel, actorLastNameLabel, searchCategoryLabel, searchRatingLabel;
    private TextField titleTextField, releaseYearTextField, lengthTextField, specialFeaturesTextField;
    private TextField searchTitleTextField, searchActorTextField;
    private TextField actorFirstNameTextField, actorLastNameTextField;
    private TextArea descriptionTextArea, actorsTextArea;
    private ListView<String> actorsListView;
    private GridPane moviesGridPane, searchGridPane;
    private Button addMovieButton, deleteMovieButton, updateMovieButton, createMovieButton, addActorButton, clearSearchButton, updateListButton;
    private VBox centerVBox;
    private HBox searchHBox;
    private TableView<Film> filmTable;
    private ChoiceBox<Category> searchCategoryChoiceBox, categoryChoiceBox2;
    private ChoiceBox<Rating> searchRatingChoiceBox, ratingChoiceBox2;
    private ChoiceBox<Language> languageChoiceBox;
    private TableColumn<Film, String> titleColumn, categoryColumn;
    private TableColumn<Film, Integer> releaseYearColumn, lengthColumn, idColumn;
    private TableColumn<Film, Rating> ratingColumn;
    private ObservableList<Film> filmObservableList;
    private ObservableList<Language> languageObservableList;
    private ObservableList<Category> categoryObservableList;
    private FilteredList<Film> filteredList;

    public MoviesGui(Controller controller) {
        this.controller = controller;
    }

    public void setup() {
        titleLabel = new Label("Titel:");
        descriptionLabel = new Label("Beskrivning:");
        releaseYearLabel = new Label("Utgivningsår:");
        lengthLabel = new Label("Längd:");
        categoryLabel = new Label("Kategori:");
        specialFeaturesLabel = new Label("Specialfunktioner:");
        actorsLabel = new Label("Skådespelare:");
        ratingLabel = new Label("Åldersgräns:");
        languageLabel = new Label("Språk:");
        actorFirstNameLabel = new Label("Förnamn:");
        actorLastNameLabel = new Label("Efternamn:");

        searchCategoryLabel = new Label("Kategori:");
        searchRatingLabel = new Label("Åldersgräns:");

        addMovieButton = new Button("Lägg till film");
        updateMovieButton = new Button("Uppdatera film");
        deleteMovieButton = new Button("Radera film");
        createMovieButton = new Button("Skapa ny film");
        addActorButton = new Button("Lägg till skådespelare");
        clearSearchButton = new Button("Rensa sökning");
        updateListButton = new Button("Uppdatera listan");

        titleTextField = new TextField();
        releaseYearTextField = new TextField();
        lengthTextField = new TextField();
        specialFeaturesTextField = new TextField();
        actorFirstNameTextField = new TextField();
        actorLastNameTextField = new TextField();

        searchTitleTextField = new TextField();
        searchTitleTextField.setPromptText("Sök på titel");
        searchTitleTextField.setFocusTraversable(false);
        searchActorTextField = new TextField();
        searchActorTextField.setFocusTraversable(false);
        searchActorTextField.setPromptText("Sök på skådespelare");

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

        searchCategoryChoiceBox = new ChoiceBox<>();
        categoryChoiceBox2 = new ChoiceBox<>();
        searchRatingChoiceBox = new ChoiceBox<>();
        ratingChoiceBox2 = new ChoiceBox<>();
        languageChoiceBox = new ChoiceBox<>();

        filmObservableList = controller.getFilmObservableList();
        languageObservableList = controller.getLanguageObservableList();
        categoryObservableList = controller.getCategoryObservableList();

        languageChoiceBox.setItems(languageObservableList);

        searchCategoryChoiceBox.setItems(categoryObservableList);

        categoryChoiceBox2.setItems(categoryObservableList);

        filteredList = new FilteredList<>(filmObservableList, p -> true);

        searchRatingChoiceBox.getItems().add(Rating.G);
        searchRatingChoiceBox.getItems().add(Rating.PG);
        searchRatingChoiceBox.getItems().add(Rating.PG13);
        searchRatingChoiceBox.getItems().add(Rating.NC17);
        searchRatingChoiceBox.getItems().add(Rating.R);

        searchRatingChoiceBox.setConverter(new StringConverter<Rating>() {
            @Override
            public String toString(Rating rating) {
                return rating.toString();
            }
            @Override
            public Rating fromString(String string) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        });

        searchActorTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            updateFilteredList(newValue.toLowerCase(), searchTitleTextField.getText().toLowerCase(),
                    searchCategoryChoiceBox.getValue(), searchRatingChoiceBox.getValue());
            filmTable.setItems(filteredList);
        });

        searchTitleTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            updateFilteredList(searchActorTextField.getText().toLowerCase(), newValue.toLowerCase(),
                    searchCategoryChoiceBox.getValue(), searchRatingChoiceBox.getValue());
            filmTable.setItems(filteredList);
        });

        searchCategoryChoiceBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            updateFilteredList(searchActorTextField.getText().toLowerCase(), searchTitleTextField.getText().toLowerCase(),
                    newValue, searchRatingChoiceBox.getValue());
            filmTable.setItems(filteredList);
        });

        searchRatingChoiceBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            updateFilteredList(searchActorTextField.getText().toLowerCase(), searchTitleTextField.getText().toLowerCase(),
                    searchCategoryChoiceBox.getValue(), newValue);
            filmTable.setItems(filteredList);
        });

        ratingChoiceBox2.getItems().add(Rating.G);
        ratingChoiceBox2.getItems().add(Rating.PG);
        ratingChoiceBox2.getItems().add(Rating.PG13);
        ratingChoiceBox2.getItems().add(Rating.NC17);
        ratingChoiceBox2.getItems().add(Rating.R);

        setupFilmTable();

        moviesGridPane = new GridPane();
        moviesGridPane.setAlignment(Pos.CENTER);
        moviesGridPane.setHgap(10);
        moviesGridPane.setVgap(10);
        moviesGridPane.add(titleLabel, 0, 0, 1, 1);
        moviesGridPane.add(titleTextField, 1, 0, 1, 1);
        moviesGridPane.add(releaseYearLabel, 2, 0, 1, 1);
        moviesGridPane.add(releaseYearTextField, 3, 0, 1, 1);
        moviesGridPane.add(lengthLabel, 4, 0, 1, 1);
        moviesGridPane.add(lengthTextField, 5, 0, 1, 1);
        moviesGridPane.add(categoryLabel, 6, 0, 1, 1);
        moviesGridPane.add(categoryChoiceBox2, 7, 0, 1, 1);
        moviesGridPane.add(languageLabel, 4, 1, 1, 1);
        moviesGridPane.add(languageChoiceBox, 5, 1, 1, 1);
        moviesGridPane.add(ratingLabel, 6, 1, 1, 1);
        moviesGridPane.add(ratingChoiceBox2, 7, 1, 1, 1);
        moviesGridPane.add(specialFeaturesLabel, 0, 1, 1, 1);
        moviesGridPane.add(specialFeaturesTextField, 1, 1, 2, 1);
        moviesGridPane.add(descriptionLabel, 0, 2, 1, 1);
        moviesGridPane.add(descriptionTextArea, 1, 2, 1, 1);
        moviesGridPane.add(actorsLabel, 2, 2, 1, 1);
        moviesGridPane.add(actorsListView, 3, 2, 1, 1);
        moviesGridPane.add(addMovieButton, 0, 3, 1, 1);
        moviesGridPane.add(deleteMovieButton, 0, 4, 1, 1);
        moviesGridPane.add(updateMovieButton, 1, 3, 1, 1);
        moviesGridPane.add(createMovieButton,1,4,1,1);

        moviesGridPane.add(actorFirstNameLabel,2,3,1,1);
        moviesGridPane.add(actorFirstNameTextField,3,3,1,1);
        moviesGridPane.add(actorLastNameLabel,2,4,1,1);
        moviesGridPane.add(actorLastNameTextField,3,4,1,1);
        moviesGridPane.add(addActorButton,3,5,1,1);

        searchGridPane = new GridPane();
        searchGridPane.setVgap(10);
        searchGridPane.setHgap(10);

        searchGridPane.add(searchTitleTextField,0,0,1,1);
        searchGridPane.add(searchActorTextField,0,1,1,1);
        searchGridPane.add(searchCategoryLabel,1,0,1,1);
        searchGridPane.add(searchCategoryChoiceBox,2,0,1,1);
        searchGridPane.add(searchRatingLabel, 1,1,1,1);
        searchGridPane.add(searchRatingChoiceBox, 2,1,1,1);
        searchGridPane.add(updateListButton,3,0,1,1);
        searchGridPane.add(clearSearchButton,3,1,1,1);

        searchHBox = new HBox();
        searchHBox.setAlignment(Pos.CENTER);
        searchHBox.setPadding(new Insets(10, 10, 10, 10));
        searchHBox.setSpacing(10);
        searchHBox.getChildren().addAll(searchGridPane);

        centerVBox = new VBox();
        centerVBox.setAlignment(Pos.TOP_CENTER);
        centerVBox.setPadding(new Insets(10, 10, 10, 10));
        centerVBox.setSpacing(10);
    }

    public Node setViewToMovies() {
        centerVBox.getChildren().clear();
        centerVBox.getChildren().addAll(searchHBox, filmTable, moviesGridPane);
        return centerVBox;
    }

    public void buttonsAndEvents() {
        handleAddMovieButton();
        handleUpdateMovieButton();
        handleDeleteMovieButton();
        handleCreateMovieButton();
        handleAddActorButton();
        handleClearSearchButton();
        handleUpdateListButton();
        handleFilmTable();
        filterFilms();

    }

    private void setupFilmTable() {
        filmTable = new TableView<Film>();

        idColumn = new TableColumn<Film, Integer>("Id:");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("filmId"));

        titleColumn = new TableColumn<Film, String>("Titel:");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

        releaseYearColumn = new TableColumn<Film, Integer>("Utgivningsår:");
        releaseYearColumn.setCellValueFactory(new PropertyValueFactory<>("releaseYear"));

        lengthColumn = new TableColumn<Film, Integer>("Längd:");
        lengthColumn.setCellValueFactory(new PropertyValueFactory<>("length"));
        lengthColumn.setCellFactory(column-> formatTimeInTableViewCell());

        ratingColumn = new TableColumn<Film, Rating>("Åldersgräns:");
        ratingColumn.setCellValueFactory(new PropertyValueFactory<>("rating"));

        categoryColumn = new TableColumn<Film, String>("Kategori:");
        categoryColumn.setCellValueFactory(cellData -> {
            Category category = cellData.getValue().getCategory();
            String categoryName = (category == null) ? "" : category.getName();
            return new SimpleStringProperty(categoryName);
        });

        filmTable.getColumns().add(idColumn);
        filmTable.getColumns().add(titleColumn);
        filmTable.getColumns().add(releaseYearColumn);
        filmTable.getColumns().add(lengthColumn);
        filmTable.getColumns().add(ratingColumn);
        filmTable.getColumns().add(categoryColumn);
        filmTable.getItems().addAll(filmObservableList);

        filmTable.setFocusTraversable(false);
        filmTable.setMaxWidth(800);
        filmTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }


    //BUTTONS METHODS
    private void handleCreateMovieButton() {
        createMovieButton.setOnMouseClicked(event-> {
            titleTextField.clear();
            specialFeaturesTextField.clear();
            releaseYearTextField.clear();
            lengthTextField.clear();
            descriptionTextArea.clear();
            actorsListView.getItems().clear();
            categoryChoiceBox2.getSelectionModel().clearSelection();
            languageChoiceBox.getSelectionModel().clearSelection();
            ratingChoiceBox2.getSelectionModel().clearSelection();
            filmTable.getSelectionModel().clearSelection();
        });
    }

    private void handleAddMovieButton() {
        //TODO KAN DENNA LÖSAS SNYGGARE/BÄTTRE????? ANTAGLIGEN MEN HUR
        addMovieButton.setOnMouseClicked(event-> {
            if(!allFilledOut()) {
                return;
            }
            String selectedTitle = titleTextField.getText();
            String selectedDescription = descriptionTextArea.getText();
            int selectedReleaseYear = Integer.parseInt(releaseYearTextField.getText());
            int selectedLength = TimeUtil.formatHourMinutesToMinutes(lengthTextField.getText());
            Category selectedCategory = categoryChoiceBox2.getValue();
            Language selectedLanguage = languageChoiceBox.getValue();
            Rating selectedRating = ratingChoiceBox2.getValue();

            double defaultRentalRate = 4.99;
            double defaultReplacementCost = 19.99;
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Lägg till film");
            alert.setHeaderText("Lägga till");
            alert.setContentText("Vill du lägga till " + selectedTitle + " i databasen?");

            ButtonType yesButton = new ButtonType("Ja");
            ButtonType noButton = new ButtonType("Nej");

            alert.getButtonTypes().setAll(yesButton, noButton);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == yesButton) {
                Film film = new Film();
                film.setTitle(selectedTitle);
                film.setDescription(selectedDescription);
                film.setReleaseYear(selectedReleaseYear);
                film.setLanguage(selectedLanguage);
                film.setLength(selectedLength);
                film.setRentalRate(BigDecimal.valueOf(defaultRentalRate));
                film.setReplacementCost(BigDecimal.valueOf(defaultReplacementCost));
                film.setLastUpdate(new Timestamp(System.currentTimeMillis()));
                film.setRating(selectedRating);
                film.setCategory(selectedCategory);
                film.setSpecialFeatures(specialFeaturesTextField.getText());
                film.setLastUpdate(new Timestamp(System.currentTimeMillis()));
                //TODO HURFAN FÅR MAN TILL MED SETACTORS?
                controller.createNewFilm(film);
            }
        });
    }

    private void handleUpdateMovieButton() {
        updateMovieButton.setOnMouseClicked(event-> {
            if (!isFilmSelected()) {
                return;
            }

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Uppdatera");
            alert.setContentText("Är du säker på att du vill uppdatera vald film?");

            ButtonType yesButton = new ButtonType("Ja");
            ButtonType noButton = new ButtonType("Nej");

            alert.getButtonTypes().setAll(yesButton, noButton);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == yesButton) {
                Film selectedFilm = filmTable.getSelectionModel().getSelectedItem();
                String selectedTitle = titleTextField.getText();
                String selectedDescription = descriptionTextArea.getText();
                int selectedReleaseYear = Integer.parseInt(releaseYearTextField.getText());
                int selectedLength = TimeUtil.formatHourMinutesToMinutes(lengthTextField.getText());
                Category selectedCategory = categoryChoiceBox2.getValue();
                Language selectedLanguage = languageChoiceBox.getValue();
                Rating selectedRating = ratingChoiceBox2.getValue();
                selectedFilm.setTitle(selectedTitle);
                selectedFilm.setDescription(selectedDescription);
                selectedFilm.setReleaseYear(selectedReleaseYear);
                selectedFilm.setLanguage(selectedLanguage);
                selectedFilm.setLength(selectedLength);
                selectedFilm.setRating(selectedRating);
                selectedFilm.setCategory(selectedCategory);
                selectedFilm.setSpecialFeatures(specialFeaturesTextField.getText());
                selectedFilm.setLastUpdate(new Timestamp(System.currentTimeMillis()));
                controller.updateSelectedFilm(selectedFilm);
            }
        });
    }

    private void handleDeleteMovieButton() {
        deleteMovieButton.setOnMouseClicked(event-> {
            if (!isFilmSelected()) {
                return;
            }

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Radera");
            alert.setHeaderText("Uppdatera");
            alert.setContentText("Är du säker på att du vill RADERA vald film?");

            ButtonType yesButton = new ButtonType("Ja");
            ButtonType noButton = new ButtonType("Nej");

            alert.getButtonTypes().setAll(yesButton, noButton);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == yesButton) {
                Film selectedFilm = filmTable.getSelectionModel().getSelectedItem();
                controller.deleteSelectedFilm(selectedFilm);
            }
        });
    }

    private void handleAddActorButton() {
        addActorButton.setOnMouseClicked(event -> {
            Actor actor = new Actor();
            String firstName = actorFirstNameTextField.getText().toUpperCase();
            String lastName = actorLastNameTextField.getText().toUpperCase();
            String fullName = firstName + " " + lastName;
            actor.setFirstName(firstName);
            actor.setLastName(lastName);

            if (!firstName.isEmpty() && !lastName.isEmpty()) {
                boolean actorAlreadyExists = false;
                for (String actorName : actorsListView.getItems()) {
                    if (actorName.equals(fullName)) {
                        actorAlreadyExists = true;
                        break;
                    }
                }
                if (!actorAlreadyExists) {
                    actorsListView.getItems().add(fullName);
                }
                //TODO hantera actor objet i kontrollern spara
            }
        });
    }

    private void handleClearSearchButton() {
        clearSearchButton.setOnMouseClicked(event-> {
            searchRatingChoiceBox.getSelectionModel().clearSelection();
            searchCategoryChoiceBox.getSelectionModel().clearSelection();
            searchActorTextField.clear();
            searchTitleTextField.clear();
        });
    }

    private void handleUpdateListButton() {
        updateListButton.setOnMouseClicked(event-> {
            controller.updateFilmList();
            updateFilmTable();
        });
    }


    private void filterFilms() {
        String searchName = searchActorTextField.getText().toLowerCase();
        String searchTitle = searchTitleTextField.getText().toLowerCase();
        Category searchCategory = searchCategoryChoiceBox.getValue();
        Rating searchRating = searchRatingChoiceBox.getValue();

        filteredList.setPredicate(film -> {
            if (!searchName.isEmpty()) {
                boolean actorMatch = false;
                for (Actor actor : film.getActors()) {
                    String fullName = actor.getFirstName().toLowerCase() + " " + actor.getLastName().toLowerCase();
                    if (fullName.contains(searchName)) {
                        actorMatch = true;
                        break;
                    }
                }
                if (!actorMatch) {
                    return false;
                }
            }

            if (!searchTitle.isEmpty() && !film.getTitle().toLowerCase().contains(searchTitle)) {
                return false;
            }


            if (searchCategory != null && !film.getCategory().getName().equals(searchCategory.toString())) {
                return false;
            }


            if (searchRating != null && !film.getRating().getRating().equals(searchRating.toString())) {
                return false;
            }

            return true;
        });
    }

    private void handleFilmTable() {
        filmTable.setOnMousePressed(event -> {
            Film selectedFilm = filmTable.getSelectionModel().getSelectedItem();
            selectedFilm.setActors(controller.getActors(selectedFilm));
            List<Actor> actorList = selectedFilm.getActors();
            if (selectedFilm != null) {
                String actors = "";
                for (int i = 0; i < selectedFilm.getActors().size(); i++) {
                    actors += selectedFilm.getActors().get(i).getFirstName() + " " + selectedFilm.getActors().get(i).getLastName() + "\n";
                }
                titleTextField.setText(selectedFilm.getTitle());
                releaseYearTextField.setText(String.valueOf(selectedFilm.getReleaseYear()));
                lengthTextField.setText(TimeUtil.formatTimeMinutesToHourMinutes(selectedFilm.getLength()));
                setCategoryAndLanguageValues(selectedFilm);
                ratingChoiceBox2.setValue(selectedFilm.getRating());
                specialFeaturesTextField.setText(selectedFilm.getSpecialFeatures());
                descriptionTextArea.setText(selectedFilm.getDescription());
                actorsListView.getItems().clear();
                for (Actor actor : actorList) {
                    actorsListView.getItems().add(actor.getFirstName() + " " + actor.getLastName());
                }
            }
        });
    }

    private void updateFilmTable() {
        filmObservableList = controller.getFilmObservableList();
        filmTable.setItems(filmObservableList);
        filmTable.refresh();
    }

    private void updateFilteredList(String searchName, String searchTitle, Category searchCategory, Rating searchRating) {
        filteredList.setPredicate(film -> {
            if (!searchName.isEmpty()) {
                boolean actorMatch = false;
                for (Actor actor : film.getActors()) {
                    String fullName = actor.getFirstName().toLowerCase() + " " + actor.getLastName().toLowerCase();
                    if (fullName.contains(searchName)) {
                        actorMatch = true;
                        break;
                    }
                }
                if (!actorMatch) {
                    return false;
                }
            }

            if (!searchTitle.isEmpty() && !film.getTitle().toLowerCase().contains(searchTitle)) {
                return false;
            }

            if (searchCategory != null && !film.getCategory().getName().equals(searchCategory.toString())) {
                return false;
            }

            if (searchRating != null && !film.getRating().getRating().equals(searchRating.toString())) {
                return false;
            }

            return true;
        });
    }

    private void setCategoryAndLanguageValues(Film selectedFilm) {
        if (selectedFilm != null) {
            Category selectedCategory = selectedFilm.getCategory();
            if (selectedCategory != null) {
                for (Category category : categoryChoiceBox2.getItems()) {
                    if (category.getName().equals(selectedCategory.getName())) {
                        categoryChoiceBox2.setValue(category);
                        break;
                    }
                }
            }

            Language selectedLanguage = selectedFilm.getLanguage();
            if (selectedLanguage != null) {
                for (Language language : languageChoiceBox.getItems()) {
                    if (language.getName().equals(selectedLanguage.getName())) {
                        languageChoiceBox.setValue(language);
                        break;
                    }
                }
            }
        }
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

    private boolean isFilmSelected() {
        if (filmTable.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Välj film i listan");
            alert.setHeaderText("Välj film i listan");
            alert.setContentText("Du måste välja en film i listan");
            alert.show();
            return false;
        }
        return true;
    }

    private boolean allFilledOut() {
        if(titleTextField.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Du måste fylla i all information");
            alert.setHeaderText("Du måste fylla i titel");
            alert.setContentText("Ange en titel");
            alert.show();
            return false;
        }
        if(releaseYearTextField.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Du måste fylla i all information");
            alert.setHeaderText("Du måste fylla i utgivningsår");
            alert.setContentText("Du måste fylla i utgivningsår");
            alert.show();
            return false;
        }
        if(lengthTextField.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Du måste fylla i all information");
            alert.setHeaderText("Du måste fylla i längd");
            alert.setContentText("Du måste fylla i längd");
            alert.show();
            return false;
        }
        if(categoryChoiceBox2.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Du måste fylla i all information");
            alert.setHeaderText("Du måste välja kategori");
            alert.setContentText("Du måste välja kategori");
            alert.show();
            return false;
        }
        if(languageChoiceBox.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Du måste fylla i all information");
            alert.setHeaderText("Du måste välja språk");
            alert.setContentText("Du måste välja språk");
            alert.show();
            return false;
        }
        if(ratingChoiceBox2.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Du måste fylla i all information");
            alert.setHeaderText("Du måste välja åldersgräns");
            alert.setContentText("Du måste välja åldersgräns");
            alert.show();
            return false;
        }
        if(specialFeaturesTextField.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Du måste fylla i all information");
            alert.setHeaderText("Du måste fylla i specialeffekter");
            alert.setContentText("Du måste fylla i specialeffekter");
            alert.show();
            return false;
        }
        if(descriptionTextArea.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Du måste fylla i all information");
            alert.setHeaderText("Du måste fylla i beskrivning");
            alert.setContentText("Du måste fylla i beskrivning");
            alert.show();
            return false;
        }
       /* if(actorsListView.getItems()==null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Du måste fylla i all information");
            alert.setHeaderText("Du måste lägga till skådespelare");
            alert.setContentText("Du måste lägga till skådespelare");
            alert.show();
            return false;
        }*/

        return true;

    }
}