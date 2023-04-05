package org.example.gui;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.example.Controller;
import org.example.entities.Address;
import org.example.entities.City;
import org.example.entities.Country;
import org.example.entities.Customer;

import java.sql.Timestamp;

public class CustomersGui {

    private Controller controller;

    public CustomersGui(Controller controller) {
        this.controller = controller;
    }

    private TableView<Customer> customerTable;
    private TableColumn<Customer, Integer> idColumn;
    private TableColumn<Customer, String> firstNameColumn, lastNameColumn, emailColumn, addressColumn, districtColumn, cityColumn, countryColumn, postalCodeColumn, phoneColumn;
    private TableColumn<Customer, Timestamp> createDateColumn; //TODO behövs denna?
    private Label firstNameLabel, lastNameLabel, emailLabel, address1Label, address2Label, disctrictLabel, cityLabel, countryLabel, postalCodeLabel, phoneLabel;
    private TextField firstNameTextField, lastNameTextField, emailTextField, address1TextField, address2TextField, districtTextField, cityTextField, postalCodeTextField, phoneTextField;
    private VBox centerVBox;
    private GridPane customerGridPane;
    private ObservableList<Customer> customerObservableList;

    public void setupCustomers() {
        firstNameLabel = new Label("Förnamn:");
        lastNameLabel = new Label("Efternamn:");
        emailLabel = new Label("E-post:");
        address1Label = new Label("Nr:");
        address2Label = new Label("Adress:");
        disctrictLabel = new Label("Distrikt:");
        cityLabel = new Label("Stad:");
        countryLabel = new Label("Land:");
        postalCodeLabel = new Label("Postnummer:");
        phoneLabel = new Label("Telefon:");

        firstNameTextField = new TextField();
        lastNameTextField = new TextField();
        emailTextField = new TextField();
        address1TextField = new TextField();
        address2TextField = new TextField();
        districtTextField = new TextField();
        cityTextField = new TextField();
        postalCodeTextField = new TextField();
        phoneTextField = new TextField();

        customerTable = new TableView<>();

        customerGridPane = new GridPane();
        customerGridPane.setAlignment(Pos.CENTER);
        customerGridPane.setHgap(10);
        customerGridPane.setVgap(10);

        customerGridPane.add(firstNameLabel,0,0,1,1);
        customerGridPane.add(firstNameTextField,0,1,1,1);
        customerGridPane.add(lastNameLabel, 1, 0,1,1);
        customerGridPane.add(lastNameTextField, 1,1,1,1);

        //TODO SDKG F
        customerObservableList = FXCollections.observableList(controller.getCustomerDAO().getAll());

        setupCustomerTable();

        centerVBox = new VBox();
        centerVBox.setAlignment(Pos.TOP_CENTER);
        centerVBox.setPadding(new Insets(10, 10, 10, 10));
        centerVBox.setSpacing(10);

    }

    private void setupCustomerTable() {
        idColumn = new TableColumn<Customer, Integer>("Medlems nummer:");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        firstNameColumn = new TableColumn<Customer, String>("First name:");
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn = new TableColumn<Customer, String>("Last name");
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        emailColumn = new TableColumn<Customer, String>("E-Mail:");
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        addressColumn = new TableColumn<Customer, String>("Address:");
        addressColumn.setCellValueFactory(cellData -> {
            Address address = cellData.getValue().getAddress();
            String adressName = (address == null) ? "" : (address.getAddress() + " " + address.getAddress2());
            return new SimpleStringProperty(adressName);
        });

        districtColumn = new TableColumn<Customer, String>("District:");
        districtColumn.setCellValueFactory(cellData-> {
            Address address = cellData.getValue().getAddress();
            String districtName = (address == null) ? "" : (address.getDistrict());
            return new SimpleStringProperty(districtName);
        });

        cityColumn = new TableColumn<Customer, String>("City:");
        cityColumn.setCellValueFactory(cellData-> {
            City city = cellData.getValue().getAddress().city();
            String cityName = (city == null) ? "" : (city.getCity());
            return new SimpleStringProperty(cityName);
        });

        countryColumn = new TableColumn<Customer, String>("Country");
        countryColumn.setCellValueFactory(cellData-> {
            Country country = cellData.getValue().getAddress().city().getCountry();
            String countryName = (country == null) ? "" : (country.getCountry());
            return new SimpleStringProperty(countryName);
        });

        postalCodeColumn = new TableColumn<Customer, String>("Postal Code:");
        postalCodeColumn.setCellValueFactory(cellData-> {
            Address adress = cellData.getValue().getAddress();
            String postalCodeName = (adress == null) ? "" : (adress.getPostalCode());
            return new SimpleStringProperty(postalCodeName);
        });

        phoneColumn = new TableColumn<Customer, String>("Phone:");
        phoneColumn.setCellValueFactory(cellData-> {
            Address address = cellData.getValue().getAddress();
            String phoneName = (address == null) ? "" : (address.getPhone());
            return new SimpleStringProperty(phoneName);
        });

        customerTable.getColumns().add(idColumn);
        customerTable.getColumns().add(firstNameColumn);
        customerTable.getColumns().add(lastNameColumn);
        customerTable.getColumns().add(emailColumn);
        customerTable.getColumns().add(addressColumn);
        customerTable.getColumns().add(districtColumn);
        customerTable.getColumns().add(cityColumn);
        customerTable.getColumns().add(countryColumn);
        customerTable.getColumns().add(postalCodeColumn);
        customerTable.getColumns().add(phoneColumn);
        customerTable.getItems().addAll(customerObservableList);

        customerTable.setFocusTraversable(false);
        customerTable.setMaxWidth(1200);
        customerTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    public Node setViewToCustomers() {
        centerVBox.getChildren().clear();
        centerVBox.getChildren().addAll(customerTable, customerGridPane);
        return centerVBox;
    }
}
