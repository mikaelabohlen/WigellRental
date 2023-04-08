package org.example.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import org.example.Controller;
import org.example.entities.Film;
import org.example.entities.Rental;

import java.util.Optional;

public class ReturnGui {
    private Controller controller;

    private TableView<Film> filmTable;
    private TableColumn<Film, Integer> idColumn;
    private TableColumn<Film, String> titleColumn;

    private ObservableList<Film> customerFilmList;
    private ObservableList<Rental> customerRentals;

    private VBox centerVBox;

    public ReturnGui(Controller controller) {
        this.controller = controller;
    }

    public void setup() {
        setupFilmTable();

        centerVBox = new VBox();
        centerVBox.setAlignment(Pos.TOP_CENTER);
        centerVBox.setPadding(new Insets(10, 10, 10, 10));
        centerVBox.setSpacing(10);
    }

    public Node setViewToReturn() {
        centerVBox.getChildren().clear();
        centerVBox.getChildren().addAll(filmTable);
        enterCustomerId();
        return centerVBox;
    }

    private void enterCustomerId() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Ange KundID");
        dialog.setContentText("Ange kundens ID:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(customerId -> {
            customerFilmList = FXCollections.observableArrayList(controller.getRentals(Integer.parseInt(customerId)));
            filmTable.getItems().clear();
            filmTable.getItems().addAll(customerFilmList);
        });
    }

    private void setupFilmTable() {
        filmTable = new TableView<Film>();

        idColumn = new TableColumn<Film, Integer>("Id:");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("filmId"));

        titleColumn = new TableColumn<Film, String>("Titel:");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));


        filmTable.getColumns().add(idColumn);
        filmTable.getColumns().add(titleColumn);


        filmTable.setFocusTraversable(false);
        filmTable.setMaxWidth(800);
        filmTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }
}
