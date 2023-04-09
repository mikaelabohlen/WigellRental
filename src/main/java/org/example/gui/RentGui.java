package org.example.gui;


import javafx.beans.property.SimpleIntegerProperty;
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
import org.example.entities.*;

import java.util.List;
import java.util.Optional;

public class RentGui {
    private Controller controller;

    private TableView<Film> filmTable;
    private TableColumn<Film, Integer> idColumn;
    private TableColumn<Film, String> titleColumn;
    private TableColumn<Film, Integer> inStockColumn;
    private TableColumn<Film, Integer> totalStockColumn;

    private VBox centerVBox;

    public RentGui(Controller controller) {
        this.controller = controller;
    }

    public void setup() {

        setupFilmTable();
        updateFilmTableForStore(controller.getActiveStore());

        centerVBox = new VBox();
        centerVBox.setAlignment(Pos.TOP_CENTER);
        centerVBox.setPadding(new Insets(10, 10, 10, 10));
        centerVBox.setSpacing(10);
    }

    public void updateFilmTableForStore(Store store) {
        List<Film> filmsInStore;
        if (store.getStoreId() == 1) {
            filmsInStore = controller.getFilmsInStore1();
        } else {
            filmsInStore = controller.getFilmsInStore2();
        }
        ObservableList<Film> filmTableData = FXCollections.observableArrayList(filmsInStore);
        filmTable.setItems(filmTableData);
    }

    public Node setViewToRent() {
        centerVBox.getChildren().clear();
        centerVBox.getChildren().addAll(filmTable);
        return centerVBox;
    }

    public void buttonsAndEvents() {
        handleFilmTable();
    }

    private void setupFilmTable() {
        filmTable = new TableView<Film>();

        idColumn = new TableColumn<Film, Integer>("Id:");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("filmId"));

        titleColumn = new TableColumn<Film, String>("Titel:");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

        totalStockColumn = new TableColumn<Film, Integer>("Totalt antal:");
        totalStockColumn.setCellValueFactory(cellData -> {
            Integer totalStock = cellData.getValue().getTotalStock(controller.getActiveStore().getStoreId());
            return new SimpleIntegerProperty(totalStock).asObject();
        });

        inStockColumn = new TableColumn<Film, Integer>("Tillgängligt antal:");
        inStockColumn.setCellValueFactory(cellData -> {
            Integer inStock = cellData.getValue().getInStock(controller.getActiveStore().getStoreId());
            return new SimpleIntegerProperty(inStock).asObject();
        });

        filmTable.getColumns().add(idColumn);
        filmTable.getColumns().add(titleColumn);
        filmTable.getColumns().add(inStockColumn);
        filmTable.getColumns().add(totalStockColumn);
        //filmTable.getItems().addAll(rentObservableList);

        filmTable.setFocusTraversable(false);
        filmTable.setMaxWidth(800);
        filmTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    private void handleFilmTable() {
    filmTable.setOnMouseClicked(event ->
    {
        if (event.getClickCount() == 2) {
            Film selectedFilm =  filmTable.getSelectionModel().getSelectedItem();
            if (selectedFilm != null) {
                TextInputDialog dialog = new TextInputDialog();
                dialog.setTitle("Hyra ut ");
                dialog.setHeaderText("Hyra ut " + selectedFilm.getTitle());
                dialog.setContentText("Ange kundens ID:");
                Optional<String> result = dialog.showAndWait();
                result.ifPresent(customerId -> {
                    controller.rentFilm(customerId, selectedFilm);
                });
            }
        }
    });
}

}
