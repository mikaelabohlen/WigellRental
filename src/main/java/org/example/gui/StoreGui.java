package org.example.gui;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.example.Controller;
import org.example.entities.Store;

public class StoreGui {

    private Controller controller;

    private VBox centerVBox;
    private HBox buttonHbox;
    private TableView<Store> storeTable;
    private TableColumn<Store, String> adressColumn, cityColumn;
    private TableColumn<Store, Integer> storeColumn;
    private ObservableList<Store> storeObservableList;
    private Button addStoreButton, removeStoreButton;

    public StoreGui(Controller controller) {
        this.controller = controller;
    }

    public void setup() {
        addStoreButton = new Button("Lägg till Butik");
        removeStoreButton = new Button("Ta bort Butik");

        storeObservableList = controller.getStoreObservableList();

        setupStoreTable();

        buttonHbox = new HBox();
        buttonHbox.setAlignment(Pos.CENTER);
        buttonHbox.setPadding(new Insets(10, 10, 10, 10));
        buttonHbox.setSpacing(10);
        buttonHbox.getChildren().addAll(addStoreButton, removeStoreButton);

        centerVBox = new VBox();
        centerVBox.setAlignment(Pos.TOP_CENTER);
        centerVBox.setPadding(new Insets(10, 10, 10, 10));
        centerVBox.setSpacing(10);
    }

    public Node setViewToStore() {
        centerVBox.getChildren().clear();
        centerVBox.getChildren().addAll(buttonHbox, storeTable);
        return centerVBox;
    }

    public void buttonAndEvents() {
        handleAddStoreButton();
        handleRemoveStoreButton();
    }

    private void setupStoreTable() {
        storeTable = new TableView<>();

        storeColumn = new TableColumn<>("Butik");
        storeColumn.setCellValueFactory(new PropertyValueFactory<Store, Integer>("storeId"));

        adressColumn = new TableColumn<>("Adress");
        adressColumn.setCellValueFactory(cellData-> {
            String adress = cellData.getValue().getAddress().getAddress();
            return new SimpleStringProperty(adress);
        });

        cityColumn = new TableColumn<>("Stad");
        cityColumn.setCellValueFactory(cellData-> {
            String city = cellData.getValue().getAddress().getCity().getCity();
            return new SimpleStringProperty(city);
        });

        storeTable.getColumns().add(storeColumn);
        storeTable.getColumns().add(adressColumn);
        storeTable.getColumns().add(cityColumn);
        storeTable.setItems(storeObservableList);

        storeTable.setFocusTraversable(false);
        storeTable.setMaxWidth(800);
        storeTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

    }
    private void handleAddStoreButton() {
        addStoreButton.setOnMouseClicked(event-> {

        });
    }

    private void handleRemoveStoreButton() {
        removeStoreButton.setOnMouseClicked(event-> {

        });
    }
}
