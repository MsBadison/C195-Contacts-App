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
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

public class UpdateCustomerController {
    public Text updateCustStateLabel;
    public TextField updateCustIDField;
    public TextField updateCustNameField;
    public TextField updateCustAddressField;
    public TextField updateCustZipField;
    public ComboBox<Country> updateCustCountryField;
    public ComboBox<Division> updateCustStateField;
    public TextField updateCustPhoneField;
    public Button updateCustSaveButton;
    public Button updateCustCancelButton;
    private static Customer updatedCustomer = null;

    /**
     * Gets the customer to be updated
     * @param customer
     */
    public static void updateCustomer(Customer customer){
        updatedCustomer = customer;
    }

    /**
     * Sets the field values to match the existing customer information
     * @param customer the customer to use
     * @throws SQLException
     */
    private void setCustomerFields(Customer customer) throws SQLException {
        updateCustIDField.setText(String.valueOf(customer.getId()));
        updateCustNameField.setText(String.valueOf(customer.getName()));
        updateCustAddressField.setText(String.valueOf(customer.getAddress()));
        updateCustZipField.setText(String.valueOf(customer.getPostalcode()));

        for(Country c : updateCustCountryField.getItems()){
            if(CustomerDB.convertCountryNameToId(customer.getCountry()) == c.getCountryId()){
                updateCustCountryField.setValue(c);
            }
        }
        for(Division d : updateCustStateField.getItems()){
            if(CustomerDB.convertStateToDivisionID(customer.getState()) == d.getDivisionId()){
                updateCustStateField.setValue(d);
            }
        }
        updateCustPhoneField.setText(String.valueOf(customer.getPhone()));
    }

    /**
     * Cancel updating customer
     * @param actionEvent
     */
    public void updateCustCancel(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("");
        alert.setHeaderText(null);
        alert.setContentText("Discard changes?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent()) {
            if (result.get() == ButtonType.OK) {
                ((Stage) (updateCustCancelButton.getScene().getWindow())).close();
            } else {
                alert.close();
            }
        }
    }

    public void initialize() throws SQLException {
        CustomerDB.importDivisions();
        CustomerDB.importCountries();
        ObservableList<Country> countries = Country.getAllCountries();
        updateCustCountryField.setItems(countries);
        ObservableList<Division> divisions = Division.getAllDivisions();
        updateCustStateField.setItems(divisions);
        setCustomerFields(updatedCustomer);
    }

    /**
     * Sets the first-level division combobox value
     * @param actionEvent
     */
    public void updateCountrySel(ActionEvent actionEvent) {
        Integer countrySelection = null;
        if (updateCustCountryField.getSelectionModel().getSelectedIndex() == 0) {
            countrySelection = 1;
            ObservableList<Division> division = Utility.filterDivisions(countrySelection);
            updateCustStateField.setItems(division);
        }
        if (updateCustCountryField.getSelectionModel().getSelectedIndex() == 1) {
            countrySelection = 2;
            ObservableList<Division> division = Utility.filterDivisions(countrySelection);
            updateCustStateField.setItems(division);
        }
        if (updateCustCountryField.getSelectionModel().getSelectedIndex() == 2) {
            countrySelection = 3;
            ObservableList<Division> division = Utility.filterDivisions(countrySelection);
            updateCustStateField.setItems(division);
        }
    }

    public void updateStateSel(MouseEvent mouseEvent) {
        Integer countrySelection = null;
        if (updateCustCountryField.getSelectionModel().getSelectedIndex() == 0) {
            countrySelection = 1;
            ObservableList<Division> division = Utility.filterDivisions(countrySelection);
            updateCustStateField.setItems(division);
        }
        if (updateCustCountryField.getSelectionModel().getSelectedIndex() == 1) {
            countrySelection = 2;
            ObservableList<Division> division = Utility.filterDivisions(countrySelection);
            updateCustStateField.setItems(division);

        }
        if (updateCustCountryField.getSelectionModel().getSelectedIndex() == 2) {
            countrySelection = 3;
            ObservableList<Division> division = Utility.filterDivisions(countrySelection);
            updateCustStateField.setItems(division);
        }
    }

    /**
     * Updates the customer information in the database
     * @param actionEvent
     * @throws SQLException
     */
    public void updateCust(ActionEvent actionEvent) throws SQLException {
        Integer custId = Integer.valueOf(updateCustIDField.getText());
        String custName;
        String custAddress;
        String custZipcode;
        String custPhone;
        Integer custDivision;

        if (updateCustNameField.getText().equals("")) {
            Utility.alertBox("Please enter a customer name.");
            return;
        }
        else {
            custName = updateCustNameField.getText();
        }

        if(updateCustAddressField.getText().equals("")) {
            Utility.alertBox("Please enter an address.");
            return;
        }
        else {
            custAddress = updateCustAddressField.getText();
        }

        if(updateCustZipField.getText().equals("")) {
            Utility.alertBox("Please enter a Zipcode");
            return;
        }
        else {
            custZipcode = updateCustZipField.getText();
        }

        if(updateCustCountryField.getSelectionModel().isEmpty()){
            Utility.alertBox("Please select a country.");
            return;
        }

        if(updateCustStateField.getSelectionModel().isEmpty()){
            Utility.alertBox("Please select a state.");
            return;
        }
        else{
            custDivision = updateCustStateField.getSelectionModel().getSelectedItem().getDivisionId();
        }

        if(updateCustPhoneField.getText().equals("")) {
            Utility.alertBox("Please enter a phone number");
            return;
        }
        else {
            custPhone = updateCustPhoneField.getText();
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("");
        alert.setHeaderText(null);
        alert.setContentText("Update " + updatedCustomer.getName() + "?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent()) {
            if (result.get() == ButtonType.OK) {
                String user = User.getUser();
                Instant now = Instant.now();
                Timestamp timeNow = Timestamp.from(now);
                CustomerDB.updateCustomer(custName, custAddress, custZipcode, custDivision, custPhone, timeNow, user,custId);
                Customer.clearCustomerList();
                CustomerDB.importCustomers();
                ((Stage) (updateCustSaveButton.getScene().getWindow())).close();
            } else {
                alert.close();
            }
        }
    }
}
