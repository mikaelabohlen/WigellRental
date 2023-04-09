package org.example.gui;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import org.example.Controller;
import org.example.entities.Film;
import org.example.entities.Inventory;
import org.example.entities.Rental;

import java.time.LocalDateTime;
import java.util.*;

public class ReturnGui {
    private Controller controller;

    private TableView<Rental> rentalTable;
    private TableColumn<Rental, Integer> idColumn;
    private TableColumn<Rental, String> titleColumn;
    private TableColumn<Rental, LocalDateTime> returnDateColumn, rentDateColumn;

    private Button rentedButton, showAllButton, newCustomerButton;
    private Label customerLabel;
    private ObservableList<Rental> customerRentals;

    private VBox centerVBox;
    private HBox buttonHbox;

    public ReturnGui(Controller controller) {
        this.controller = controller;
    }

    public void setup() {
        customerLabel = new Label("Kundnummer: ");

        rentedButton = new Button("Visa Uthyrda");
        showAllButton = new Button("Visa Alla");
        newCustomerButton = new Button("Byt kund");

        setupRentalsTable();

        buttonHbox = new HBox();
        buttonHbox.setAlignment(Pos.CENTER);
        buttonHbox.setPadding(new Insets(10, 10, 10, 10));
        buttonHbox.setSpacing(10);
        buttonHbox.getChildren().addAll(customerLabel, newCustomerButton, rentedButton, showAllButton);

        centerVBox = new VBox();
        centerVBox.setAlignment(Pos.TOP_CENTER);
        centerVBox.setPadding(new Insets(10, 10, 10, 10));
        centerVBox.setSpacing(10);
    }

    public Node setViewToReturn() {
        centerVBox.getChildren().clear();
        centerVBox.getChildren().addAll(buttonHbox,rentalTable);
        enterCustomerId();
        return centerVBox;
    }

    private void enterCustomerId() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Ange KundID");
        dialog.setContentText("Ange kundens ID:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(customerId -> {
            customerRentals = FXCollections.observableArrayList(controller.getRentals(Integer.parseInt(customerId)));
            if(!customerRentals.isEmpty()) {
                rentalTable.getItems().clear();
            }
            rentalTable.getItems().addAll(customerRentals);
            customerLabel.setText("Kundnummer: " + customerId);
        });
    }

    public void buttonAndEvents() {
        handleRentalTable();
        handleSortedButton();
        handleShowAllButton();
        handleNewCustomerButton();
    }

    private void handleSortedButton() {
        rentedButton.setOnMouseClicked(event -> {
            if(customerRentals!=null) {
                ObservableList<Rental>sortedList = customerRentals.filtered(rental -> rental.getReturnDate() == null);
                rentalTable.setItems(sortedList);
                rentalTable.refresh();
            }
        });
    }

    private void handleShowAllButton() {
        showAllButton.setOnMouseClicked(event-> {
            rentalTable.setItems(customerRentals);
        });
    }

    private void handleNewCustomerButton() {
        newCustomerButton.setOnMouseClicked(event-> {
            rentalTable.setItems(customerRentals);
            enterCustomerId();
        });
    }
    private void setupRentalsTable() {
        rentalTable = new TableView<>();
        idColumn = new TableColumn<>("Uthyrnings-ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("rentalId"));

        titleColumn = new TableColumn<>("Filmtitel");
        titleColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Rental, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Rental, String> c) {
                Inventory inventory = c.getValue().getInventory();
                Film film = inventory.getFilm();
                return new SimpleStringProperty(film.getTitle());
            }
        });


        rentDateColumn = new TableColumn<>("Uthyrningsdatum");
        rentDateColumn.setCellValueFactory(new PropertyValueFactory<>("rentalDate"));

        returnDateColumn = new TableColumn<>("Återlämningsdatum");
        returnDateColumn.setCellValueFactory(new PropertyValueFactory<>("returnDate"));

        rentalTable.getColumns().add(idColumn);
        rentalTable.getColumns().add(titleColumn);
        rentalTable.getColumns().add(rentDateColumn);
        rentalTable.getColumns().add(returnDateColumn);

        rentalTable.setFocusTraversable(false);
        rentalTable.setMaxWidth(800);
        rentalTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    private void handleRentalTable() {
        rentalTable.setOnMouseClicked(event-> {
            if(event.getClickCount()==2) {
                Rental selectedRental = rentalTable.getSelectionModel().getSelectedItem();
                if(selectedRental != null && selectedRental.getReturnDate() == null) {
                    controller.returnFilm(selectedRental);
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Återlämnad");
                    alert.setHeaderText(rentalTable.getSelectionModel().getSelectedItem().getInventory().getFilm().getTitle());
                    alert.setContentText("Återlämnad");
                    alert.show();
                    rentalTable.refresh();
                }
            }
        });
    }
}

