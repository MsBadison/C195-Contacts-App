package App.Controller;

import App.DAO.CustomerDB;
import App.Helper.Utility;
import App.Model.Country;
import App.Model.Customer;
import App.Model.Division;
import App.Model.User;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

public class AddCustomerController {
    public TextField addCustIDField;
    public TextField addCustNameField;
    public TextField addCustAddressField;
    public TextField addCustZipField;
    public ComboBox<Country> addCustCountryField;
    public ComboBox<Division> addCustStateField;
    public TextField addCustPhoneField;
    public Button addCustSaveButton;
    public Button addCustCancelButton;
    public Text addCustStateLabel;

    /**
     * Creates pop-up asking the user to confirm canceling adding a customer
     * @param actionEvent
     */
    public void addCustCancel(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("");
        alert.setHeaderText(null);
        alert.setContentText("Discard changes?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent()) {
            if (result.get() == ButtonType.OK) {
                ((Stage) (addCustCancelButton.getScene().getWindow())).close();
            } else {
                alert.close();
            }
        }
    }

    /**
     * Saves the entered customer information to the database
     * @param actionEvent
     * @throws SQLException
     */
    public void addCustSave(ActionEvent actionEvent) throws SQLException {

        String custName;
        String custAddress;
        String custZipcode;
        String custPhone;
        Integer custDivision;

        if (addCustNameField.getText().equals("")) {
            Utility.alertBox("Please enter a customer name.");
            return;
        }
        else {
            custName = addCustNameField.getText();
        }

        if(addCustAddressField.getText().equals("")) {
            Utility.alertBox("Please enter an address.");
            return;
        }
        else {
            custAddress = addCustAddressField.getText();
        }

        if(addCustZipField.getText().equals("")) {
            Utility.alertBox("Please enter a Zipcode");
            return;
        }
        else {
            custZipcode = addCustZipField.getText();
        }

        if(addCustCountryField.getSelectionModel().isEmpty()){
            Utility.alertBox("Please select a country.");
            return;
        }

        if(addCustStateField.getSelectionModel().isEmpty()){
            Utility.alertBox("Please select a state.");
            return;
        }
        else{
            custDivision = addCustStateField.getSelectionModel().getSelectedItem().getDivisionId();
        }

        if(addCustPhoneField.getText().equals("")) {
            Utility.alertBox("Please enter a phone number");
            return;
        }
        else {
            custPhone = addCustPhoneField.getText();
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("");
        alert.setHeaderText(null);
        alert.setContentText("Add new customer?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent()) {
            if (result.get() == ButtonType.OK) {
                Instant now = Instant.now();
                Timestamp timeNow = Timestamp.from(now);
                CustomerDB.insertCustomer(custName, custAddress, custZipcode, custDivision, custPhone, User.getUser(), timeNow);
                Customer.clearCustomerList();
                CustomerDB.importCustomers();
                ((Stage) (addCustSaveButton.getScene().getWindow())).close();
            } else {
                alert.close();
            }
        }
    }

    public void initialize() throws SQLException {
        CustomerDB.importDivisions();
        CustomerDB.importCountries();
        ObservableList<Country> countries = Country.getAllCountries();
        addCustCountryField.setItems(countries);



    }

    /**
     * Sets the first-level division fields
     * @param actionEvent
     */
    public void addCountrySel(ActionEvent actionEvent) {
        Integer countrySelection = null;
        if (addCustCountryField.getSelectionModel().getSelectedIndex() == 0) {
            countrySelection = 1;
            ObservableList<Division> division = Utility.filterDivisions(countrySelection);
            addCustStateField.setItems(division);
            addCustStateField.setPromptText("Select State");
            addCustStateLabel.setText("State");
        }
        if (addCustCountryField.getSelectionModel().getSelectedIndex() == 1) {
            countrySelection = 2;
            ObservableList<Division> division = Utility.filterDivisions(countrySelection);
            addCustStateField.setItems(division);
            addCustStateField.setPromptText("Select Region");
            addCustStateLabel.setText("Region");
        }
        if (addCustCountryField.getSelectionModel().getSelectedIndex() == 2) {
            countrySelection = 3;
            ObservableList<Division> division = Utility.filterDivisions(countrySelection);
            addCustStateField.setItems(division);
            addCustStateField.setPromptText("Select Province");
            addCustStateLabel.setText("Province");
        }
    }
}
