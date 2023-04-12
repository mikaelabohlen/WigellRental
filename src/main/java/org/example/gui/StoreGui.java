package org.example.gui;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import org.example.Controller;
import org.example.dao.CountryDAO;
import org.example.entities.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public class StoreGui {

    private Controller controller;

    private VBox centerVBox;
    private HBox buttonHbox;
    private TableView<Store> storeTable;
    private TableColumn<Store, String> adressColumn, cityColumn;
    private TableColumn<Store, Integer> storeColumn;
    private ObservableList<Store> storeObservableList;
    private Button addStoreButton, removeStoreButton;

    //DIALOG PANE
    private Label addressLabel, districtLabel, cityLabel, phoneLabel, countryLabel, postalCodeLabel, staffLabel;
    private TextField addressTextField, districtTextField, cityTextField, phoneTextField, postalCodeTextField;
    private GridPane dialogGridPane;
    private ChoiceBox<Country> countryChoiceBox;
    private ChoiceBox<Staff> staffChoiceBox;


    public StoreGui(Controller controller) {
        this.controller = controller;
    }

    public void setup() {
        addressLabel = new Label("Adress:");
        districtLabel = new Label("Distrikt:");
        cityLabel = new Label("Stad:");
        phoneLabel = new Label("Telefon:");
        countryLabel = new Label("Land:");
        postalCodeLabel = new Label("Postnummer:");
        staffLabel = new Label("Butiksansvarig:");

        addressTextField = new TextField();
        cityTextField = new TextField();
        districtTextField = new TextField();
        phoneTextField = new TextField();
        postalCodeTextField = new TextField();

        addStoreButton = new Button("Lägg till Butik");
        removeStoreButton = new Button("Ta bort Butik");

        storeObservableList = controller.getStoreObservableList();

        List<Staff> listOfStaff;
        listOfStaff = controller.getStaffDAO().getAll();

        staffChoiceBox = new ChoiceBox<>();
        staffChoiceBox.setConverter(createStaffStringConverter());

        for(Staff staff : listOfStaff) {
            staffChoiceBox.getItems().add(staff);
        }

        CountryDAO countryDAO = new CountryDAO();
        List<Country> countries = countryDAO.getAll();

        countryChoiceBox = new ChoiceBox<>();
        countryChoiceBox.setConverter(createCountryStringConverter());

        for(Country country : countries) {
            countryChoiceBox.getItems().add(country);
        }

        dialogGridPane = new GridPane();
        dialogGridPane.setHgap(10);
        dialogGridPane.setVgap(10);
        dialogGridPane.setPadding(new Insets(20, 10, 10, 10));
        dialogGridPane.add(staffLabel,0,1);
        dialogGridPane.add(staffChoiceBox,1,1);
        dialogGridPane.add(addressLabel,0,2);
        dialogGridPane.add(addressTextField,1,2);
        dialogGridPane.add(districtLabel,0,3);
        dialogGridPane.add(districtTextField,1,3);
        dialogGridPane.add(cityLabel,0,4);
        dialogGridPane.add(cityTextField,1,4);
        dialogGridPane.add(postalCodeLabel,0,5);
        dialogGridPane.add(postalCodeTextField,1,5);
        dialogGridPane.add(phoneLabel,0,6);
        dialogGridPane.add(phoneTextField,1,6);
        dialogGridPane.add(countryLabel,0,7);
        dialogGridPane.add(countryChoiceBox,1,7);

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

    public void updateStaffList() {
        staffChoiceBox.getItems().clear();

        List<Staff> listOfStaff;
        listOfStaff = controller.getStaffDAO().getAll();

        staffChoiceBox.setConverter(createStaffStringConverter());

        for(Staff staff : listOfStaff) {
            staffChoiceBox.getItems().add(staff);
        }
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
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Ange Butiksinformationen");
            dialog.setHeaderText("Fyll i butiksinformationen nedan:");

            ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

            dialog.getDialogPane().setContent(dialogGridPane);

            Optional<ButtonType> result = dialog.showAndWait();
            if (result.isPresent() && result.get() == okButtonType) {
                Store store = new Store();
                Address addressObject = new Address();
                City cityObject = new City();

                String address = addressTextField.getText();
                String district = districtTextField.getText();
                String city = cityTextField.getText();
                String postal = postalCodeTextField.getText();
                String phone = phoneTextField.getText();
                cityObject.setCity(city);
                cityObject.setCountry(countryChoiceBox.getValue());
                cityObject.setLastUpdate(new Timestamp(System.currentTimeMillis()));

                addressObject.setAddress(address);
                addressObject.setAddress2(null);
                addressObject.setDistrict(district);
                addressObject.setCity(cityObject);
                addressObject.setPostalCode(postal);
                addressObject.setPhone(phone);
                GeometryFactory gf = new GeometryFactory();
                Coordinate coord = new Coordinate(10,20);
                Point point = gf.createPoint(coord);
                addressObject.setLocation(point);
                addressObject.setLastUpdate(new Timestamp(System.currentTimeMillis()));

                store.setAddressId(addressObject);
                store.setLastUpdate(new Timestamp(System.currentTimeMillis()));
                store.setManagerStaff(staffChoiceBox.getValue());
                controller.createNewStore(store);
            }
        });
    }

    private void handleRemoveStoreButton() {
        removeStoreButton.setOnMouseClicked(event-> {

        });
    }

    private StringConverter<Country> createCountryStringConverter() {
        return new StringConverter<Country>() {
            @Override
            public String toString(Country country) {
                return country.getCountry();
            }

            @Override
            public Country fromString(String string) {
                return null;
            }
        };
    }

    private StringConverter<Staff> createStaffStringConverter() {
        return new StringConverter<Staff>() {
            @Override
            public String toString(Staff Staff) {
                return Staff.getFirstName() + " " + Staff.getLastName();
            }

            @Override
            public Staff fromString(String string) {
                return null;
            }
        };
    }

}


