package App.Controller;

import App.DAO.AppointmentDB;
import App.DAO.CustomerDB;
import App.Helper.Time;
import App.Helper.Utility;
import App.Model.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.util.Optional;

public class AddApptController {
    public TextField addApptIDField;
    public TextField addApptTitleField;
    public TextField addApptDescField;
    public TextField addApptLocField;
    public Button addApptSaveButton;
    public Button addApptCancelButton;
    public ComboBox<Customer> addApptCustIDField;
    public DatePicker addApptDateField;
    public TextField addApptStartTimeField;
    public TextField addApptEndTimeField;
    public ComboBox<Contact> addApptContactField;
    public ComboBox<AppointmentType> addApptTypeField;

    /**
     * Creates pop-up confirming the user wants to cancel adding an appointment
     * @param actionEvent
     */
    public void addApptCancel(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("");
        alert.setHeaderText(null);
        alert.setContentText("Discard changes?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent()) {
            if (result.get() == ButtonType.OK) {
                ((Stage) (addApptCancelButton.getScene().getWindow())).close();
            } else {
                alert.close();
            }
        }
    }

    /**
     * Imports the customer and contact information
     * Populates the appropriate fields
     * @throws SQLException
     */
    public void initialize() throws SQLException {
        CustomerDB.importCustomers();
        AppointmentDB.importContacts();
        ObservableList<Customer> customers = Customer.getAllCustomers();
        addApptCustIDField.setItems(customers);
        ObservableList<AppointmentType> apptType = AppointmentType.getAllAppointmentTypes();
        addApptTypeField.setItems(apptType);
        ObservableList<Contact> contact = Contact.getAllContacts();
        addApptContactField.setItems(contact);
    }

    /**
     * Adds the entered appointment information to the database
     * @param actionEvent
     * @throws SQLException
     */
    public void addApptSave(ActionEvent actionEvent) throws SQLException {

        String title;
        String description;
        String location;
        Integer contact;
        String type;
        LocalDate date = null;
        LocalTime startTime = null;
        LocalTime endTime = null;
        Integer customer;

        if(addApptTitleField.getText().equals("")){
            Utility.alertBox("Please enter an appointment title.");
            return;
        }
        else{
            title = addApptTitleField.getText();
        }

        if(addApptDescField.getText().equals("")){
            Utility.alertBox("Please enter an appointment description.");
            return;
        }
        else{
            description = addApptDescField.getText();
        }

        if(addApptLocField.getText().equals("")){
            Utility.alertBox("Please enter an appointment location.");
            return;
        }
        else{
            location = addApptLocField.getText();
        }

        if(addApptContactField.getSelectionModel().isEmpty()){
            Utility.alertBox("Please select a contact.");
            return;
        }
        else{
            contact = addApptContactField.getSelectionModel().getSelectedItem().getId();
        }

        if(addApptTypeField.getSelectionModel().isEmpty()){
            Utility.alertBox("Please select an appointment type.");
            return;
        }
        else{
            type = addApptTypeField.getSelectionModel().getSelectedItem().getType();
        }

        if(addApptDateField.getValue() == null) {
            Utility.alertBox("Please select an appointment date.");
        }
        else{
            date = addApptDateField.getValue();
        }

        if(addApptStartTimeField.getText().equals("")){
            Utility.alertBox("Please enter an appointment start time");
            return;
        }

        try{
            Integer.parseInt(addApptStartTimeField.getText());
        } catch (NumberFormatException e) {
            Utility.alertBox("Please enter a valid numerical start time in HHMM format.");
            return;
        }

        if(Integer.parseInt(addApptStartTimeField.getText()) > 2359){
            Utility.alertBox("Start time must be between 0000 and 2359.");
            return;
        }

        try{
            String enteredStartTimeText = addApptStartTimeField.getText();
            startTime = LocalTime.parse(enteredStartTimeText.substring(0,2) + ":" + enteredStartTimeText.substring(2,4));

        } catch (Exception e) {
            Utility.alertBox("Please enter a valid start time in HHMM format.");
        }

        if(addApptEndTimeField.getText().equals("")){
            Utility.alertBox("Please enter an appointment end time");
            return;
        }

        try{
            Integer.parseInt(addApptEndTimeField.getText());
        } catch (NumberFormatException e) {
            Utility.alertBox("Please enter a valid numerical end time in HHMM format.");
            return;
        }

        if(Integer.parseInt(addApptEndTimeField.getText()) > 2359){
            Utility.alertBox("End time must be between 0000 and 2359.");
            return;
        }

        try{
            String enteredEndTimeText = addApptEndTimeField.getText();
            endTime = LocalTime.parse(enteredEndTimeText.substring(0,2) + ":" + enteredEndTimeText.substring(2,4));

        } catch (RuntimeException e) {
            Utility.alertBox("Please enter a valid end time in HHMM format.");
        }

        Timestamp startTimestamp = Time.convertLocalToUPCTimestamp(startTime, date);
        Timestamp endTimeStamp = Time.convertLocalToUPCTimestamp(endTime, date);

        if(Time.checkStartEndTime(startTimestamp, endTimeStamp)){
            Utility.alertBox("The start time can not be after the end time.");
            return;
        }

        if(!Time.checkBusHours(startTime, endTime, date)){
            Utility.alertBox("The meeting must be within business hours of 10AM and 8PM EST");
            return;
        }


        if(addApptCustIDField.getSelectionModel().isEmpty()){
            Utility.alertBox("Please select a customer.");
            return;
        }
        else{
            customer = addApptCustIDField.getSelectionModel().getSelectedItem().getId();
        }

        AppointmentDB.apptSameCustomer(customer);
        if(Utility.apptOverlapCheck(date, startTime, endTime)){
            Utility.alertBox(CustomerDB.convertCustomerIDtoName(customer) + " already has an appointment scheduled for this time.");
            return;
        }

        String user = User.getUser();
        Integer userId = AppointmentDB.convertUserToId(User.getUser());

        Instant now = Instant.now();
        Timestamp timeNow = Timestamp.from(now);


        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("");
        alert.setHeaderText(null);
        alert.setContentText("Add appointment?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent()) {
            if (result.get() == ButtonType.OK) {
        AppointmentDB.insertAppointment(title, description, location, type, startTimestamp,
                endTimeStamp, timeNow, user, customer, userId, contact);
        AppointmentDB.importAppointments();
        ((Stage) (addApptSaveButton.getScene().getWindow())).close();
            } else {
                alert.close();
            }
        }
    }
}
