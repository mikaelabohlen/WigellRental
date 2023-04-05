package org.example.gui;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
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
import org.example.dao.CategoryDAO;
import org.example.dao.LanguageDAO;
import org.example.entities.Actor;
import org.example.entities.Category;
import org.example.entities.Film;
import org.example.entities.Language;
import org.example.enums.Rating;
import org.example.utils.TimeUtil;
import org.hibernate.Hibernate;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public class MoviesGui {
    private Controller controller;

    //private FilmDAO filmDAO;

    private Label titleLabel, descriptionLabel, releaseYearLabel, languageLabel, lengthLabel, ratingLabel, categoryLabel, specialFeaturesLabel, actorsLabel;
    private Label actorFirstNameLabel, actorLastNameLabel;
    private TextField titleTextField, releaseYearTextField, lengthTextField, specialFeaturesTextField;
    private TextField searchTextFieldTitle;
    private TextField actorFirstNameTextField, actorLastNameTextField;
    private TextArea descriptionTextArea, actorsTextArea;
    private ListView<String> actorsListView;
    private GridPane moviesGridPane;
    private Button addMovieButton, deleteMovieButton, updateMovieButton, createMovieButton, addActorButton, listByActorButton;
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
    private ObservableList<Film> filmObservableList;
    private ObservableList<Language> languageObservableList;
    private ObservableList<Category> categoryObservableList;
    private TextInputDialog actorSearchTextInputDialog;

    public MoviesGui(Controller controller) {
        this.controller = controller;
    }
    public void setupMovies() {
        titleLabel = new Label("Titel:");
        descriptionLabel = new Label("Beskriving:");
        releaseYearLabel = new Label("Utgivningsår:");
        lengthLabel = new Label("Längd:");
        categoryLabel = new Label("Kategori:");
        specialFeaturesLabel = new Label("Specialfunktioner:");
        actorsLabel = new Label("Skådespelare:");
        ratingLabel = new Label("Åldersgräns:");
        languageLabel = new Label("Språk:");
        actorFirstNameLabel = new Label("Förnamn:");
        actorLastNameLabel = new Label("Efternamn:");

        addMovieButton = new Button("Lägg till film");
        updateMovieButton = new Button("Uppdatera film");
        deleteMovieButton = new Button("Radera film");
        createMovieButton = new Button("Skapa ny film");
        addActorButton = new Button("Lägg till skådespelare");
        listByActorButton = new Button("Sök Skådespelare");

        titleTextField = new TextField();
        releaseYearTextField = new TextField();
        lengthTextField = new TextField();
        specialFeaturesTextField = new TextField();
        searchTextFieldTitle = new TextField();
        searchTextFieldTitle.setFocusTraversable(false);
        actorFirstNameTextField = new TextField();
        actorLastNameTextField = new TextField();

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

        actorSearchTextInputDialog = new TextInputDialog("Sök på skådespelare");
        actorSearchTextInputDialog.setTitle("Sök skådespelare");
        actorSearchTextInputDialog.setHeaderText("Ange namnet på den skådespelare du vill söka på");

        //filmDAO = new FilmDAO();

        CategoryDAO categoryDAO = new CategoryDAO();
        LanguageDAO languageDAO = new LanguageDAO();

        List<Category> categoryList = controller.getCategoryDAO().getAll();
        List<Film> films = controller.getFilmDAO().getAll();
        List<Language> languages = languageDAO.getAll();

        filmObservableList = FXCollections.observableList(films);
        languageObservableList = FXCollections.observableList(languages);
        categoryObservableList = FXCollections.observableList(categoryList);

        for (Language language : languages) {
            languageChoiceBox.getItems().add(language.getName());
        }

        categoryChoiceBox.getItems().add("Alla kategorier");
        for (Category category : categoryList) {
            categoryChoiceBox.getItems().add(category.getName());
        }
        categoryChoiceBox.setValue("Alla kategorier");

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
        ratingChoiceBox.setConverter(new StringConverter<Rating>() {
            @Override
            public String toString(Rating rating) {
                if (rating == Rating.ALL) {
                    return "Alla åldersgränser";
                } else {
                    return rating.toString();
                }
            }
            @Override
            public Rating fromString(String string) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
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
        moviesGridPane.add(updateMovieButton, 0, 5, 1, 1);
        moviesGridPane.add(createMovieButton,0,6,1,1);
        moviesGridPane.add(actorFirstNameLabel,2,3,1,1);
        moviesGridPane.add(actorFirstNameTextField,3,3,1,1);
        moviesGridPane.add(actorLastNameLabel,2,4,1,1);
        moviesGridPane.add(actorLastNameTextField,3,4,1,1);
        moviesGridPane.add(addActorButton,3,5,1,1);

        searchHBox = new HBox();
        searchHBox.setAlignment(Pos.CENTER);
        searchHBox.setPadding(new Insets(10, 10, 10, 10));
        searchHBox.setSpacing(10);
        searchHBox.getChildren().addAll(listByActorButton, searchTextFieldTitle,  ratingChoiceBox, categoryChoiceBox);

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

    public void moviesButtonsAndEvents() {
        //BUTTONS
        handleAddMovieButton();
        handleUpdateMovieButton();
        handleDeleteMovieButton();
        handleAddActorButton();
        handleListByActorButton();

        //EVENTS
        handleFilmTable();
        filterFilmTableTitleCategoryRating();
    }

    private void setupFilmTable() {
        filmTable = new TableView<Film>();

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

    private void handleAddMovieButton() {
        //TODO KAN DENNA LÖSAS SNYGGARE/BÄTTRE????? ANTAGLIGEN MEN HUR
        addMovieButton.setOnMouseClicked(event-> {
            String selectedTitle = titleTextField.getText();
            String selectedDescription = descriptionTextArea.getText();
            int selectedReleaseYear = Integer.parseInt(releaseYearTextField.getText());
            String languageString = languageChoiceBox.getValue();
            String categoryString = categoryChoiceBox2.getValue();

            Category selectedCategory = null;
            Language selectedLanguage = null;
            Rating selectedRating = ratingChoiceBox2.getValue();
            System.out.println(ratingChoiceBox2.getValue().getRating());

            System.out.println(actorsListView.getItems());

            double defaultRentalRate = 4.99;
            double defaultReplacementCost = 19.99;
            for(Category category : categoryObservableList) {
                if(category.getName().equals(categoryString)) {
                    selectedCategory = category;
                    break;
                }
            }

            for(Language language : languageObservableList) {
                if(language.getName().equals(languageString)) {
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
            controller.getFilmDAO().create(film);

            updateFilmTable();
            filterFilmTableTitleCategoryRating();
        });
    }

    private void handleUpdateMovieButton() {
        updateMovieButton.setOnMouseClicked(event-> {
            //TODO Fixa detta
        });
    }

    private void handleDeleteMovieButton() {
        deleteMovieButton.setOnMouseClicked(event-> {
            //TODO fixa detta
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

    private void handleListByActorButton() {
        listByActorButton.setOnMouseClicked(event -> {
            filmTable.setItems(filmObservableList);
            Optional<String> searchInput = actorSearchTextInputDialog.showAndWait();
            searchInput.ifPresent(name -> {
                FilteredList<Film> filteredList = new FilteredList<>(filmObservableList, film -> {
                    boolean actorMatch = false;
                    for (Actor actor : film.getActors()) {
                        if (actor.getFirstName().toLowerCase().contains(name.toLowerCase()) || actor.getLastName().toLowerCase().contains(name.toLowerCase()) ) {
                            actorMatch = true;
                            break;
                        }
                    }
                    return actorMatch;
                });
                filmTable.setItems(filteredList);
            });
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
                categoryChoiceBox2.setValue(selectedFilm.getCategory().getName());
                ratingChoiceBox2.setValue(selectedFilm.getRating());
                languageChoiceBox.setValue(selectedFilm.getLanguage().getName());
                specialFeaturesTextField.setText(selectedFilm.getSpecialFeatures());
                descriptionTextArea.setText(selectedFilm.getDescription());
                actorsListView.getItems().clear();
                for (Actor actor : actorList) {
                    actorsListView.getItems().add(actor.getFirstName() + " " + actor.getLastName());
                }
            }
        });
    }

    private void filterFilmTableTitleCategoryRating() {
        FilteredList<Film> filteredList = new FilteredList<>(filmObservableList, p -> true);
        searchTextFieldTitle.setPromptText("Sök efter titel...");
        searchTextFieldTitle.setOnKeyReleased(keyEvent -> {
            String searchTitle = searchTextFieldTitle.getText().toLowerCase();
            String searchCategory = categoryChoiceBox.getSelectionModel().getSelectedItem().toLowerCase();
            Rating searchRating = ratingChoiceBox.getSelectionModel().getSelectedItem();
            filteredList.setPredicate(film -> {
                boolean titleMatch = film.getTitle().toLowerCase().startsWith(searchTitle);
                boolean categoryMatch = searchCategory.isEmpty() || searchCategory.equalsIgnoreCase("alla kategorier") || (film.getCategory() != null && film.getCategory().getName().toLowerCase().contains(searchCategory.toLowerCase()));
                boolean ratingMatch = searchRating.equals(Rating.ALL) || film.getRating().equals(searchRating);
                return titleMatch && categoryMatch && ratingMatch;
            });
        });

        categoryChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            String searchTitle = searchTextFieldTitle.getText().toLowerCase();
            String searchCategory = newValue.toLowerCase();
            Rating searchRating = ratingChoiceBox.getSelectionModel().getSelectedItem();
            filteredList.setPredicate(film -> {
                boolean titleMatch = film.getTitle().toLowerCase().startsWith(searchTitle);
                boolean categoryMatch = searchCategory.isEmpty() || searchCategory.equalsIgnoreCase("alla kategorier") || (film.getCategory() != null && film.getCategory().getName().toLowerCase().contains(searchCategory.toLowerCase()));
                boolean ratingMatch = searchRating.equals(Rating.ALL) || film.getRating().equals(searchRating);
                return titleMatch && categoryMatch && ratingMatch;
            });
        });

        ratingChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            String searchTitle = searchTextFieldTitle.getText().toLowerCase();
            String searchCategory = categoryChoiceBox.getSelectionModel().getSelectedItem().toLowerCase();
            Rating searchRating = newValue;
            filteredList.setPredicate(film -> {
                boolean titleMatch = film.getTitle().toLowerCase().startsWith(searchTitle);
                boolean categoryMatch = searchCategory.isEmpty() || searchCategory.equalsIgnoreCase("alla kategorier") || (film.getCategory() != null && film.getCategory().getName().toLowerCase().contains(searchCategory.toLowerCase()));
                boolean ratingMatch = searchRating.equals(Rating.ALL) || film.getRating().equals(searchRating);
                return titleMatch && categoryMatch && ratingMatch;
            });
        });

        filmTable.setItems(filteredList);
    }

    private void updateFilmTable() {
        filmObservableList = FXCollections.observableList(controller.getFilmDAO().getAll());
        filmTable.setItems(filmObservableList);
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

