package org.example.gui;



import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.example.Controller;
import org.example.entities.*;

import java.math.BigDecimal;
import java.util.Optional;

public class RentGui {
    private Controller controller;

    private TableView<Inventory> inventoryTable;
    private TableColumn<Inventory, Integer> idColumn;
    private TableColumn<Inventory, String> filmTitleColumn;
    private TableColumn<Inventory, BigDecimal> priceColumn;

    private ObservableList<Inventory> filmsToRent;
    private Button rentButton, clearCart;

    private HBox buttonHbox;
    private VBox centerVBox;


    public RentGui(Controller controller) {
        this.controller = controller;
    }

    public void setup() {
        rentButton = new Button("Hyr ut");
        clearCart = new Button("Rensa kundvagn");

        setupInventoryTable();

        buttonHbox = new HBox();
        buttonHbox.setAlignment(Pos.CENTER);
        buttonHbox.setPadding(new Insets(10, 10, 10, 10));
        buttonHbox.setSpacing(10);
        buttonHbox.getChildren().addAll(rentButton, clearCart);

        centerVBox = new VBox();
        centerVBox.setAlignment(Pos.TOP_CENTER);
        centerVBox.setPadding(new Insets(10, 10, 10, 10));
        centerVBox.setSpacing(10);
    }

    public Node setViewToRent() {
        centerVBox.getChildren().clear();
        centerVBox.getChildren().addAll(buttonHbox, inventoryTable);
        return centerVBox;
    }

    public void getCart() {
        filmsToRent = controller.getInventoryObservableList();
        inventoryTable.setItems(filmsToRent);
        System.out.println("HEJ");
        inventoryTable.refresh();
    }

    public void buttonsAndEvents() {
        handleRentButton();
        handleClearCartButton();
    }

    private void setupInventoryTable() {
        inventoryTable = new TableView<>();
        inventoryTable.setItems(filmsToRent);

        idColumn = new TableColumn<Inventory, Integer>("Id");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("inventoryId"));

        filmTitleColumn = new TableColumn<Inventory, String>("Film titel");
        filmTitleColumn.setCellValueFactory(cellData -> {
            Inventory inventory = cellData.getValue();
            Film film = inventory.getFilm();
            return new SimpleStringProperty(film.getTitle());
        });

        priceColumn = new TableColumn<Inventory, BigDecimal>("Pris");
        priceColumn.setCellValueFactory(cellData-> {
            Inventory inventory = cellData.getValue();
            Film film = inventory.getFilm();
            return new SimpleObjectProperty<>(film.getRentalRate());
        });

        inventoryTable.getColumns().add(idColumn);
        inventoryTable.getColumns().add(filmTitleColumn);
        inventoryTable.getColumns().add(priceColumn);
        inventoryTable.setFocusTraversable(false);
        inventoryTable.setMaxWidth(800);
        inventoryTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    private void handleRentButton() {
        rentButton.setOnMouseClicked(event-> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Hyra ut ");
            dialog.setHeaderText("Hyra ut");
            dialog.setContentText("Ange kundens ID:");
            Optional<String> result = dialog.showAndWait();
            result.ifPresent(customerId -> {
                controller.handleRentals(Integer.parseInt(customerId));

                BigDecimal cost = BigDecimal.ZERO;

                for (Inventory inventory : filmsToRent) {
                    cost = cost.add(inventory.getFilm().getRentalRate());
                }

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Hyrd");
                alert.setHeaderText("Du har hyrt " + filmsToRent.size() + " filmer");
                alert.setContentText("Till priset av " + cost + "dollar");
                alert.showAndWait();
                controller.getFilms().clear();
                getCart();
                inventoryTable.getItems().clear();
                inventoryTable.refresh();
            });

        });
    }

    private void handleClearCartButton() {
        clearCart.setOnMouseClicked(event-> {
            filmsToRent.clear();
        });
    }
}


