package org.example.gui;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
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
import javafx.util.Callback;
import org.example.Controller;
import org.example.entities.Customer;
import org.example.entities.Film;
import org.example.entities.Inventory;
import org.example.entities.Rental;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class ReturnGui {
    private Controller controller;

    private TableView<Film> filmTable;
    private TableColumn<Film, Integer> idColumn;
    private TableColumn<Film, String> titleColumn;
    private TableColumn<Film, LocalDateTime> rentalDateColumn;
    private TableColumn<Film, LocalDateTime> rentalReturnColumn;

    private ObservableList<Film> customerFilmList;
    private ObservableList<Rental> customerRentals;

    private VBox centerVBox;

    private int currentCustomerId;
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
            currentCustomerId = Integer.parseInt(customerId);
            filmTable.getItems().clear();
            filmTable.getItems().addAll(customerFilmList);
        });
    }

    public void buttonAndEvents() {
        handleFilmTable();
    }


    private void setupFilmTable() {
        filmTable = new TableView<Film>();

        idColumn = new TableColumn<Film, Integer>("Id:");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("filmId"));

        titleColumn = new TableColumn<Film, String>("Titel:");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

        rentalDateColumn = new TableColumn<Film, LocalDateTime>("Uthyrningsdatum:");
        rentalDateColumn.setCellValueFactory(c -> {
            List<Inventory> inventories = new ArrayList<>(c.getValue().getInventories());
            for (Inventory inventory : inventories) {
                List<Rental> rentals = new ArrayList<>(inventory.getRentals());
                for (Rental rental : rentals) {
                    if (rental != null) {
                        return new SimpleObjectProperty<>(rental.getRentalDate());
                    }
                }
            }
            return null;
        });

        rentalReturnColumn = new TableColumn<Film, LocalDateTime>("Returdatum:");
        rentalReturnColumn.setCellValueFactory(c -> {
            List<Inventory> inventories = new ArrayList<>(c.getValue().getInventories());
            for (Inventory inventory : inventories) {
                List<Rental> rentals = new ArrayList<>(inventory.getRentals());
                for (Rental rental : rentals) {
                    if (rental != null && rental.getReturnDate() != null && rental.getCustomer().getCustomerId() == currentCustomerId) {
                        return new SimpleObjectProperty<>(rental.getReturnDate());
                    }
                }
            }
            return null;
        });

        filmTable.getColumns().add(idColumn);
        filmTable.getColumns().add(titleColumn);
        filmTable.getColumns().add(rentalDateColumn);
        filmTable.getColumns().add(rentalReturnColumn);

        filmTable.setFocusTraversable(false);
        filmTable.setMaxWidth(800);
        filmTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }
    private void handleFilmTable() {
        //TODO funkar inte än...
        filmTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Film selectedFilm = filmTable.getSelectionModel().getSelectedItem();
                Customer customer = controller.getCustomerDAO().read(currentCustomerId);
                if (selectedFilm != null) {
                    Rental rental = null;
                    for (Inventory inventory : selectedFilm.getInventories()) {
                        for (Rental r : inventory.getRentals()) {
                            if (r.getCustomer().equals(customer) && r.getReturnDate() == null) {
                                rental = r;
                                break;
                            }
                        }
                        if (rental != null) {
                            rental.setReturnDate(LocalDateTime.now());
                            System.out.println(rental.getReturnDate());
                            controller.getRentalDAO().update(rental);
                            filmTable.refresh();
                            break;
                        }
                    }
                }
            }
        });
    }
}

