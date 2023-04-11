package org.example.gui;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import javafx.beans.property.SimpleObjectProperty;
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
import org.example.entities.Address;
import org.example.entities.City;
import org.example.entities.Country;
import org.example.entities.Staff;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public class StaffGui {

    private Controller controller;

    private VBox centerVBox;
    private HBox buttonHbox;
    private TableView<Staff> staffTable;
    private TableColumn<Staff, String> firstNameColumn, lastNameColumn, emailColumn, userNameColumn;
    private TableColumn<Staff, Integer> storeColumn;
    private ObservableList<Staff> staffObservableList;
    private Button hireButton, fireButton;

    //DIALOG PANE
    private Label firstNameLabel, lastNameLabel, emailLabel, usernameLabel, addressLabel, districtLabel, cityLabel, phoneLabel, countryLabel, postalCodeLabel;
    private TextField firstNameTextField, lastNameTextField, emailTextField, usernameTextField, addressTextField, districtTextField, cityTextField, phoneTextField, postalCodeTextField;
    private GridPane dialogGridPane;
    private ChoiceBox<Country> countryChoiceBox;

    public StaffGui(Controller controller) {
        this.controller = controller;
    }

    public void setup() {
        firstNameLabel = new Label("Förnamn:");
        lastNameLabel = new Label("Efternamn:");
        emailLabel = new Label("E-post:");
        usernameLabel = new Label("Användarnamn:");
        addressLabel = new Label("Adress:");
        districtLabel = new Label("Distrikt:");
        cityLabel = new Label("Stad:");
        phoneLabel = new Label("Telefon:");
        countryLabel = new Label("Land:");
        postalCodeLabel = new Label("Postnummer:");

        firstNameTextField = new TextField();
        lastNameTextField = new TextField();
        emailTextField = new TextField();
        usernameTextField = new TextField();
        addressTextField = new TextField();
        cityTextField = new TextField();
        districtTextField = new TextField();
        phoneTextField = new TextField();
        postalCodeTextField = new TextField();

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
        dialogGridPane.add(firstNameLabel, 0, 0);
        dialogGridPane.add(firstNameTextField, 1, 0);
        dialogGridPane.add(lastNameLabel, 0, 1);
        dialogGridPane.add(lastNameTextField, 1, 1);
        dialogGridPane.add(emailLabel, 0, 2);
        dialogGridPane.add(emailTextField, 1, 2);
        dialogGridPane.add(usernameLabel, 0, 3);
        dialogGridPane.add(usernameTextField, 1, 3);
        dialogGridPane.add(addressLabel,0,4);
        dialogGridPane.add(addressTextField,1,4);
        dialogGridPane.add(districtLabel,0,5);
        dialogGridPane.add(districtTextField,1,5);
        dialogGridPane.add(cityLabel,0,6);
        dialogGridPane.add(cityTextField,1,6);
        dialogGridPane.add(postalCodeLabel,0,7);
        dialogGridPane.add(postalCodeTextField,1,7);
        dialogGridPane.add(phoneLabel,0,8);
        dialogGridPane.add(phoneTextField,1,8);
        dialogGridPane.add(countryLabel,0,9);
        dialogGridPane.add(countryChoiceBox,1,9);

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
        handleStaffTable();
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
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Ange användarinformation");
            dialog.setHeaderText("Fyll i användarinformationen nedan:");

            ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

            dialog.getDialogPane().setContent(dialogGridPane);

            Optional<ButtonType> result = dialog.showAndWait();
            if (result.isPresent() && result.get() == okButtonType) {
                Staff staff = new Staff();
                Address addressObject = new Address();
                City cityObject = new City();

                String firstName = firstNameTextField.getText();
                String lastName = lastNameTextField.getText();
                String email = emailTextField.getText();
                String username = usernameTextField.getText();
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


                staff.setFirstName(firstName);
                staff.setLastName(lastName);
                staff.setEmail(email);
                staff.setUsername(username);
                staff.setAddressId(addressObject);
                staff.setLastUpdate(new Timestamp(System.currentTimeMillis()));
                controller.createStaff(staff);
            }
        });
    }
    private void handleStaffTable() {
        staffTable.setOnMouseClicked(event-> {
            if(event.getClickCount()==2) {
                firstNameTextField.clear();
                lastNameTextField.clear();
                emailTextField.clear();
                usernameTextField.clear();
                addressTextField.clear();
                districtTextField.clear();
                cityTextField.clear();
                postalCodeTextField.clear();
                phoneTextField.clear();

                Staff staff = staffTable.getSelectionModel().getSelectedItem();

                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.setTitle("Uppdatera informationen");
                dialog.setHeaderText("Fyll i önskat värde");

                ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
                dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

                dialog.getDialogPane().setContent(dialogGridPane);
                firstNameTextField.setText(staff.getFirstName());
                lastNameTextField.setText(staff.getLastName());
                emailTextField.setText(staff.getEmail());
                usernameTextField.setText(staff.getUsername());
                addressTextField.setText(staff.getAddress().getAddress());
                districtTextField.setText(staff.getAddress().getDistrict());
                cityTextField.setText(staff.getAddress().getCity().getCity());
                postalCodeTextField.setText(staff.getAddress().getPostalCode());
                phoneTextField.setText(staff.getAddress().getPhone());
                countryChoiceBox.setValue(staff.getAddress().getCity().getCountry());

                Optional<ButtonType> result = dialog.showAndWait();
                if (result.isPresent() && result.get() == okButtonType) {
                    String firstName = firstNameTextField.getText();
                    String lastName = lastNameTextField.getText();
                    String email = emailTextField.getText();
                    String username = usernameTextField.getText();
                    String address = addressTextField.getText();
                    String district = districtTextField.getText();
                    String city = cityTextField.getText();
                    String postal = postalCodeTextField.getText();
                    String phone = phoneTextField.getText();
                    Country country = countryChoiceBox.getValue();

                    staff.setFirstName(firstName);
                    staff.setLastName(lastName);
                    staff.setEmail(email);
                    staff.setUsername(username);
                    staff.getAddress().setAddress(address);
                    staff.getAddress().setDistrict(district);
                    staff.getAddress().getCity().setCity(city);
                    staff.getAddress().setPostalCode(postal);
                    staff.getAddress().setPhone(phone);
                    staff.getAddress().getCity().setCountry(country);

                    controller.updateStaff(staff);
                }
            }
        });
    }

    private void handleFireButton() {
        fireButton.setOnMouseClicked(event-> {
            Staff staff = staffTable.getSelectionModel().getSelectedItem();
            controller.deleteSelectedStaff(staff);
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
}
