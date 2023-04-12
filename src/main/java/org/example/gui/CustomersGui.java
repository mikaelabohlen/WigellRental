package org.example.gui;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import org.example.Controller;
import org.example.dao.CountryDAO;
import org.example.entities.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CustomersGui {

    private Controller controller;

    public CustomersGui(Controller controller) {
        this.controller = controller;
    }

    private TableView<Customer> customerTable;
    private TableColumn<Customer, Integer> idColumn;
    private TableColumn<Customer, String> firstNameColumn, lastNameColumn, emailColumn, addressColumn, districtColumn/*, cityColumn*//*, countryColumn*/, postalCodeColumn/*,phoneColumn*/;
    private TableColumn<Customer, Address> phoneColumn;
    private TableColumn<Customer, Country> countryColumn;
    private TableColumn<Customer, City> cityColumn;
    private TableColumn<Customer, Timestamp> createDateColumn; //TODO behövs denna?
    private Label firstNameLabel, lastNameLabel, emailLabel, address1Label, disctrictLabel, cityLabel, countryLabel, postalCodeLabel, phoneLabel;
    private TextField firstNameTextField, lastNameTextField, emailTextField, address1TextField, districtTextField, cityTextField,countryTextField, postalCodeTextField, phoneTextField;
    private VBox centerVBox;
    private GridPane customerGridPane;
    private ObservableList<Customer> customerObservableList;
    private Button addCustomerButton, deleteCustomerButton, updateCustomerButton, updateCustomerTableButton;
    private ChoiceBox<Country> countryChoiceBox;
    private List<Customer> filteredCustomers;

    public void setup() {
        firstNameLabel = new Label("Förnamn:");
        lastNameLabel = new Label("Efternamn:");
        emailLabel = new Label("E-post:");
        address1Label = new Label("Adress:");
        disctrictLabel = new Label("Distrikt:");
        cityLabel = new Label("Stad:");
        countryLabel = new Label("Land:");
        postalCodeLabel = new Label("Postnummer:");
        phoneLabel = new Label("Telefon:");

        firstNameTextField = new TextField();
        lastNameTextField = new TextField();
        emailTextField = new TextField();
        address1TextField = new TextField();
        districtTextField = new TextField();
        cityTextField = new TextField();
        countryTextField = new TextField();
        postalCodeTextField = new TextField();
        phoneTextField = new TextField();

        addCustomerButton = new Button("Lägg till kund");
        deleteCustomerButton = new Button("Ta bort kund");
        updateCustomerButton = new Button("Uppdatera kund");
        updateCustomerTableButton = new Button("Uppdatera listan");

        CountryDAO countryDao = new CountryDAO();
        List<Country> countries = countryDao.getAll();

        countryChoiceBox = new ChoiceBox<>();
        countryChoiceBox.setConverter(createCountryStringConverter());

        for(Country country : countries) {
            countryChoiceBox.getItems().add(country);
        }

        customerTable = new TableView<>();

        customerGridPane = new GridPane();
        customerGridPane.setAlignment(Pos.CENTER);
        customerGridPane.setHgap(10);
        customerGridPane.setVgap(10);

        customerGridPane.add(firstNameLabel,0,0,1,1);
        customerGridPane.add(firstNameTextField,1,0,1,1);
        customerGridPane.add(lastNameLabel, 2, 0,1,1);
        customerGridPane.add(lastNameTextField, 3,0,1,1);
        customerGridPane.add(emailLabel, 4,0,1,1);
        customerGridPane.add(emailTextField,5,0,1,1);
        customerGridPane.add(phoneLabel,6,0,1,1);
        customerGridPane.add(phoneTextField,7,0,1,1);
        customerGridPane.add(address1Label,0,1,1,1);
        customerGridPane.add(address1TextField,1,1,1,1);
        customerGridPane.add(disctrictLabel,2,1,1,1);
        customerGridPane.add(districtTextField,3,1,1,1);
        customerGridPane.add(countryLabel,0,2,1,1);
        customerGridPane.add(countryChoiceBox,1,2,1,1);
        customerGridPane.add(postalCodeLabel,2,2,1,1);
        customerGridPane.add(postalCodeTextField,3,2,1,1);
        customerGridPane.add(cityLabel,4,2,1,1);
        customerGridPane.add(cityTextField,5,2,1,1);
        customerGridPane.add(addCustomerButton,0,3,1,1);
        customerGridPane.add(deleteCustomerButton,0,4,1,1);
        customerGridPane.add(updateCustomerButton,0,5,1,1);
        customerGridPane.add(updateCustomerTableButton,0,6,1,1);

        customerObservableList = controller.getCustomerObservableList();

        setupCustomerTable();

        centerVBox = new VBox();
        centerVBox.setAlignment(Pos.TOP_CENTER);
        centerVBox.setPadding(new Insets(10, 10, 10, 10));
        centerVBox.setSpacing(10);

    }

    public Node setViewToCustomers() {
        centerVBox.getChildren().clear();
        centerVBox.getChildren().addAll(customerTable, customerGridPane);
        return centerVBox;
    }

    public void buttonsAndEvents() {
        handleAddCustomerButton();
        handleDeleteCustomerButton();
        handleUpdateCustomerButton();
        handleCustomerTable();
        handleUpdateTableButton();
    }

    private void setupCustomerTable() {
        idColumn = new TableColumn<Customer, Integer>("Medlemsnummer:");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        firstNameColumn = new TableColumn<Customer, String>("Förnamn:");
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn = new TableColumn<Customer, String>("Efternamn");
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        emailColumn = new TableColumn<Customer, String>("E-Post:");
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        addressColumn = new TableColumn<Customer, String>("Adress:");
        addressColumn.setCellValueFactory(cellData -> {
            Address address = cellData.getValue().getAddress();
            String adressName = (address == null) ? "" : (address.getAddress());
            return new SimpleStringProperty(adressName);
        });

        districtColumn = new TableColumn<Customer, String>("Distrikt:");
        districtColumn.setCellValueFactory(cellData-> {
            Address address = cellData.getValue().getAddress();
            String districtName = (address == null) ? "" : (address.getDistrict());
            return new SimpleStringProperty(districtName);
        });

        cityColumn = new TableColumn<Customer, City>("Stad:");
        cityColumn.setCellValueFactory(cellData-> {
            City city = cellData.getValue().getAddress().getCity();
            return new SimpleObjectProperty<City>(city);
        });

        countryColumn = new TableColumn<Customer, Country>("Land");
        countryColumn.setCellValueFactory(cellData-> {
            Country country = cellData.getValue().getAddress().getCity().getCountry();
            return new SimpleObjectProperty<Country>(country);
        });

        postalCodeColumn = new TableColumn<Customer, String>("Postnummer:");
        postalCodeColumn.setCellValueFactory(cellData-> {
            Address adress = cellData.getValue().getAddress();
            String postalCodeName = (adress == null) ? "" : (adress.getPostalCode());
            return new SimpleStringProperty(postalCodeName);
        });
        phoneColumn = new TableColumn<Customer, Address>("Telefon");
        phoneColumn.setCellValueFactory(cellData -> {
            return new SimpleObjectProperty<>(cellData.getValue().getAddress());
        });

        phoneColumn.setCellFactory(column -> {
            return new TableCell<Customer, Address>() {
                @Override
                protected void updateItem(Address address, boolean empty) {
                    super.updateItem(address, empty);

                    if (address == null || empty) {
                        setText(null);
                    } else {
                        setText(address.getPhone());
                    }
                }
            };
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

        filteredCustomers = customerObservableList.stream()
                .filter(customer -> customer.getStore().getStoreId() == controller.getActiveStore().getStoreId())
                .collect(Collectors.toList());

        customerTable.getItems().addAll(filteredCustomers);
        customerTable.setFocusTraversable(false);
        customerTable.setMaxWidth(1200);
        customerTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    private void handleCustomerTable() {
        customerTable.setOnMouseClicked(event-> {
            Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
            if(selectedCustomer!=null) {
                firstNameTextField.setText(selectedCustomer.getFirstName());
                lastNameTextField.setText(selectedCustomer.getLastName());
                emailTextField.setText(selectedCustomer.getEmail());
                phoneTextField.setText(selectedCustomer.getAddress().getPhone());
                address1TextField.setText(selectedCustomer.getAddress().getAddress());
                districtTextField.setText(selectedCustomer.getAddress().getDistrict());
                countryChoiceBox.setValue(selectedCustomer.getAddress().getCity().getCountry());
                postalCodeTextField.setText(selectedCustomer.getAddress().getPostalCode());
                cityTextField.setText(selectedCustomer.getAddress().getCity().getCity());
            }
        });
    }

    private void handleUpdateTableButton() {
        updateCustomerTableButton.setOnMouseClicked(event-> {
            controller.updateCustomerList();
            updateCustomerTable();
        });
    }

    private void handleAddCustomerButton() {
        addCustomerButton.setOnMouseClicked(event-> {
            //TODO inte klart
            City city = new City();
            city.setCity(cityTextField.getText());
            city.setCountry(countryChoiceBox.getValue());
            city.setLastUpdate(new Timestamp(System.currentTimeMillis()));

            Address address = new Address();
            address.setAddress(address1TextField.getText());
            address.setAddress2(null);
            address.setDistrict(districtTextField.getText());
            address.setCity(city);
            address.setPostalCode(postalCodeTextField.getText());
            address.setPhone(phoneTextField.getText());
            GeometryFactory gf = new GeometryFactory();
            Coordinate coord = new Coordinate(10,20);
            Point point = gf.createPoint(coord);
            address.setLocation(point);
            address.setLastUpdate(new Timestamp(System.currentTimeMillis()));

            Customer customer = new Customer();
            customer.setFirstName(firstNameTextField.getText().toUpperCase());
            customer.setLastName(lastNameTextField.getText().toUpperCase());
            customer.setEmail(emailTextField.getText().toUpperCase());
            customer.setAddress(address);
            customer.setCreateDate(new Timestamp(System.currentTimeMillis()));

            controller.createNewCustomer(customer);

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Kund tillagd");
            alert.setHeaderText(customer.getFirstName() + " " + customer.getLastName() + " tillagd");
            alert.show();

        });
    }

    private void handleDeleteCustomerButton() {
        deleteCustomerButton.setOnMouseClicked(event-> {
            Customer customer = customerTable.getSelectionModel().getSelectedItem();
            controller.deleteSelectedCustomer(customer);

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Kund borttagen");
            alert.setHeaderText(customer.getFirstName() + " " + customer.getLastName() + " borttagen");
            alert.show();
        });
    }

    private void handleUpdateCustomerButton() {
        updateCustomerButton.setOnMouseClicked(event-> {
            Customer customer = customerTable.getSelectionModel().getSelectedItem();
            if(customer==null) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Du måste välja en kund ifrån listan");
                alert.setHeaderText("Ingen kund vald");
                alert.setTitle("Fel vid uppdatering");
                alert.showAndWait();
                return;
            }
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Uppdatera");
            alert.setContentText("Är du säker på att du vill uppdatera vald kund?");

            ButtonType yesButton = new ButtonType("Ja");
            ButtonType noButton = new ButtonType("Nej");

            alert.getButtonTypes().setAll(yesButton, noButton);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == yesButton) {
                customer.setFirstName(firstNameTextField.getText().toUpperCase());
                customer.setLastName(lastNameTextField.getText().toUpperCase());
                customer.setEmail(emailTextField.getText().toUpperCase());
                customer.getAddress().getCity().setCountry(countryChoiceBox.getValue());
                customer.getAddress().setPhone(phoneTextField.getText());
                customer.getAddress().setAddress(address1TextField.getText());
                customer.getAddress().setDistrict(districtTextField.getText());
                customer.getAddress().setPostalCode(postalCodeTextField.getText());
                customer.getAddress().getCity().setCity(cityTextField.getText());

                controller.updateCustomer(customer);
            }

        });
    }

    private void updateCustomerTable() {
        controller.updateCustomerList();
        customerObservableList = controller.getCustomerObservableList();
        filteredCustomers = customerObservableList.stream()
                .filter(customer -> customer.getStore().getStoreId() == controller.getActiveStore().getStoreId())
                .collect(Collectors.toList());
        customerTable.getItems().clear();
        customerTable.getItems().addAll(filteredCustomers);
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
}
