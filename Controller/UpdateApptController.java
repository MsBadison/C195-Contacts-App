package App.Controller;

import App.DAO.AppointmentDB;
import App.DAO.ContactDB;
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
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

public class UpdateApptController {
    public TextField updateApptIDField;
    public TextField updateApptTitleField;
    public TextField updateApptDescField;
    public TextField updateApptLocField;
    public Button updateApptSaveButton;
    public Button updateApptCancelButton;
    public ComboBox<Contact> updateApptContactField;
    public ComboBox<AppointmentType> updateApptTypeField;
    public ComboBox<Customer> updateApptCustIDField;
    public TextField updateApptStartTimeField;
    public TextField updateApptEndTimeField;
    public DatePicker updateApptDateField;
    private static Appointment updatedAppt = null;

    /**
     * Gets the appointment to be updated
     * @param appointment the appointment
     */
    public static void updateAppt(Appointment appointment){
        updatedAppt = appointment;
    }

    /**
     * Updates the fields to contain the existing appointment information
     * @param appointment the appointment to use
     * @throws SQLException
     */
    private void updateApptFields(Appointment appointment) throws SQLException {
        CustomerDB.importCustomers();
        AppointmentDB.importContacts();

        ObservableList<Customer> customers = Customer.getAllCustomers();
        updateApptCustIDField.setItems(customers);

        ObservableList<AppointmentType> apptType = AppointmentType.getAllAppointmentTypes();
        updateApptTypeField.setItems(apptType);

        updateApptIDField.setText(String.valueOf(appointment.getAppointmentId()));
        updateApptTitleField.setText(appointment.getTitle());
        updateApptDescField.setText(appointment.getDescription());
        updateApptLocField.setText(appointment.getLocation());

        ObservableList<Contact> contact = Contact.getAllContacts();
        updateApptContactField.setItems(contact);

        for(Contact c : updateApptContactField.getItems()){
            if(ContactDB.convertContactNameToId(appointment.getContact()) == c.getId()){
                updateApptContactField.setValue(c);
            }
        }

        for(AppointmentType a : updateApptTypeField.getItems()){
            if(appointment.getType().equals(a.getType())){
                updateApptTypeField.setValue(a);
            }
        }

        for(Customer c : updateApptCustIDField.getItems()){
            if(appointment.getCustId().equals(c.getId())){
                updateApptCustIDField.setValue(c);
            }
        }

        updateApptDateField.setValue(appointment.getStartTime().toLocalDate());

        String startTimeRaw = appointment.getStartTime().toLocalTime().toString();
        String startTime = startTimeRaw.substring(0,2) + startTimeRaw.substring(3,5);
        updateApptStartTimeField.setText(startTime);

        String endTimeRaw = appointment.getEndTime().toLocalTime().toString();
        String endTime = endTimeRaw.substring(0,2) + endTimeRaw.substring(3,5);
        updateApptEndTimeField.setText(endTime);

    }

    /**
     * Cancels updating the appointment
     * @param actionEvent
     */
    public void updateApptCancel(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("");
        alert.setHeaderText(null);
        alert.setContentText("Discard changes?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent()) {
            if (result.get() == ButtonType.OK) {
                ((Stage) (updateApptCancelButton.getScene().getWindow())).close();
            } else {
                alert.close();
            }
        }
    }

    public void initialize() throws SQLException {
        updateApptFields(updatedAppt);
    }

    /**
     * Updates the appointment information in the database
     * @param actionEvent
     * @throws SQLException
     */
    public void updateApptSave(ActionEvent actionEvent) throws SQLException {

        Integer apptId = updatedAppt.getAppointmentId();

        String title;
        String description;
        String location;
        Integer contact;
        String type;
        LocalDate date = null;
        LocalTime startTime = null;
        LocalTime endTime = null;
        Integer customer;

        if(updateApptTitleField.getText().equals("")){
            Utility.alertBox("Please enter an appointment title.");
            return;
        }
        else{
            title = updateApptTitleField.getText();
        }

        if(updateApptDescField.getText().equals("")){
            Utility.alertBox("Please enter an appointment description.");
            return;
        }
        else{
            description = updateApptDescField.getText();
        }

        if(updateApptLocField.getText().equals("")){
            Utility.alertBox("Please enter an appointment location.");
            return;
        }
        else{
            location = updateApptLocField.getText();
        }

        if(updateApptContactField.getSelectionModel().isEmpty()){
            Utility.alertBox("Please select a contact.");
            return;
        }
        else{
            contact = updateApptContactField.getSelectionModel().getSelectedItem().getId();
        }

        if(updateApptTypeField.getSelectionModel().isEmpty()){
            Utility.alertBox("Please select an appointment type.");
            return;
        }
        else{
            type = updateApptTypeField.getSelectionModel().getSelectedItem().getType();
        }

        if(updateApptDateField.getValue() == null) {
            Utility.alertBox("Please select an appointment date.");
        }
        else{
            date = updateApptDateField.getValue();
        }

        if(updateApptStartTimeField.getText().equals("")){
            Utility.alertBox("Please enter an appointment start time.");
            return;
        }

        try{
            Integer.parseInt(updateApptStartTimeField.getText());
        } catch (NumberFormatException e) {
            Utility.alertBox("Please enter a valid numerical start time in HHMM format.");
            return;
        }

        if(Integer.parseInt(updateApptStartTimeField.getText()) > 2359){
            Utility.alertBox("Start time must be between 0000 and 2359.");
            return;
        }

        try{
            String enteredStartTimeText = updateApptStartTimeField.getText();
            startTime = LocalTime.parse(enteredStartTimeText.substring(0,2) + ":" + enteredStartTimeText.substring(2,4));

        } catch (Exception e) {
            Utility.alertBox("Please enter a valid start time in HHMM format.");
        }

        if(updateApptEndTimeField.getText().equals("")){
            Utility.alertBox("Please enter an appointment end time");
            return;
        }

        try{
            Integer.parseInt(updateApptEndTimeField.getText());
        } catch (NumberFormatException e) {
            Utility.alertBox("Please enter a valid numerical end time in HHMM format.");
            return;
        }

        if(Integer.parseInt(updateApptEndTimeField.getText()) > 2359){
            Utility.alertBox("End time must be between 0000 and 2359.");
            return;
        }

        try{
            String enteredEndTimeText = updateApptEndTimeField.getText();
            endTime = LocalTime.parse(enteredEndTimeText.substring(0,2) + ":" + enteredEndTimeText.substring(2,4));

        } catch (Exception e) {
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


        if(updateApptCustIDField.getSelectionModel().isEmpty()){
            Utility.alertBox("Please select a customer.");
            return;
        }
        else{
            customer = updateApptCustIDField.getSelectionModel().getSelectedItem().getId();
        }

        if(!updatedAppt.getCustId().equals(customer)) {
            AppointmentDB.apptSameCustomer(customer);
            if (Utility.apptOverlapCheck(date, startTime, endTime)) {
                Utility.alertBox(CustomerDB.convertCustomerIDtoName(customer) + " already has an appointment scheduled for this time.");
                return;
            }
        }

        String user = User.getUser();
        Integer userId = AppointmentDB.convertUserToId(User.getUser());

        Instant now = Instant.now();
        Timestamp timeNow = Timestamp.from(now);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("");
        alert.setHeaderText(null);
        alert.setContentText("Update appointment?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent()) {
            if (result.get() == ButtonType.OK) {
                AppointmentDB.updateAppt(title, description, location, contact, type, startTimestamp, endTimeStamp, timeNow, user, customer, userId, apptId);
                Appointment.clearAppointmentList();
                AppointmentDB.importAppointments();
                ((Stage) (updateApptSaveButton.getScene().getWindow())).close();
            } else {
                alert.close();
            }
        }
    }
}
