package org.example.gui;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.example.Controller;
import org.example.entities.Address;
import org.example.entities.Staff;

public class StaffGui {

    private Controller controller;

    private VBox centerVBox;
    private HBox buttonHbox;
    private TableView<Staff> staffTable;
    private TableColumn<Staff, String> firstNameColumn, lastNameColumn, emailColumn, userNameColumn;
    private TableColumn<Staff, Integer> storeColumn;
    private ObservableList<Staff> staffObservableList;
    private Button hireButton, fireButton;

    public StaffGui(Controller controller) {
        this.controller = controller;
    }

    public void setup() {
        hireButton = new Button("Lägg till personal");
        fireButton = new Button("Säg upp personal");

        staffObservableList = controller.getStaffObservableList();

        setupStaffTable();

        buttonHbox = new HBox();
        buttonHbox.setAlignment(Pos.CENTER);
        buttonHbox.setPadding(new Insets(10, 10, 10, 10));
        buttonHbox.setSpacing(10);
        buttonHbox.getChildren().addAll(hireButton, fireButton);

        centerVBox = new VBox();
        centerVBox.setAlignment(Pos.TOP_CENTER);
        centerVBox.setPadding(new Insets(10, 10, 10, 10));
        centerVBox.setSpacing(10);
    }

    public Node setViewToStaff() {
        centerVBox.getChildren().clear();
        centerVBox.getChildren().addAll(buttonHbox,staffTable);
        return centerVBox;
    }

    public void buttonAndEvents() {
        handleFireButton();
        handleHireButton();
    }

    private void setupStaffTable() {
        staffTable = new TableView<>();
        firstNameColumn = new TableColumn<>("Förnamn");
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<Staff, String>("firstName"));

        lastNameColumn = new TableColumn<>("Efternamn:");
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<Staff, String>("lastName"));

        emailColumn = new TableColumn<>("E-post:");
        emailColumn.setCellValueFactory(new PropertyValueFactory<Staff, String>("email"));

        userNameColumn = new TableColumn<>("Användarnamn:");
        userNameColumn.setCellValueFactory(new PropertyValueFactory<Staff, String>("username"));

        storeColumn = new TableColumn<>("Butik:");
        storeColumn.setCellValueFactory(cellData -> {
            int storeId = cellData.getValue().getStore().getStoreId();
            return new SimpleObjectProperty<>(storeId);
        });

        staffTable.getColumns().add(firstNameColumn);
        staffTable.getColumns().add(lastNameColumn);
        staffTable.getColumns().add(emailColumn);
        staffTable.getColumns().add(userNameColumn);
        staffTable.getColumns().add(storeColumn);
        staffTable.setItems(staffObservableList);

        staffTable.setFocusTraversable(false);
        staffTable.setMaxWidth(800);
        staffTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

    }
    private void handleHireButton() {
        hireButton.setOnMouseClicked(event-> {
            //TODO
        });
    }

    private void handleFireButton() {
        fireButton.setOnMouseClicked(event-> {
            //TODO
        });
    }
}
