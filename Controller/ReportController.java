package App.Controller;

import App.DAO.AppointmentDB;
import App.Helper.Utility;
import App.Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.sql.SQLException;
import java.time.ZonedDateTime;

public class ReportController {
    public Button reportCloseButton;
    public TextArea reportText;
    public ComboBox<String> reportMonth;
    public ComboBox<String> reportType;
    public TextField reportCountBox;
    public Button timeMonthRunButton;
    public ComboBox<Contact> reportContact;
    public Button contactRunButton;
    public ComboBox<String> reportLocation;
    public Button locationRunButton;
    public TextField reportLocationBox;
    interface getResults {
        String reportUpdate(int apptId, String title, String type, String desc, ZonedDateTime start, ZonedDateTime end, int custID);
    }

    /**
     * Imports appointments and contacts, sets the combobox values
     * @throws SQLException
     */
    public void initialize() throws SQLException {
        AppointmentDB.importAppointments();
        AppointmentDB.importContacts();

        ObservableList<String> type = FXCollections.observableArrayList();
        for(AppointmentType apptType : AppointmentType.getAllAppointmentTypes()){
            type.add(apptType.getType());
        }
        reportType.setItems(type);

        ObservableList<String> months = FXCollections.observableArrayList();
        months.add("January");
        months.add("February");
        months.add("March");
        months.add("April");
        months.add("May");
        months.add("June");
        months.add("July");
        months.add("August");
        months.add("September");
        months.add("October");
        months.add("November");
        months.add("December");
        reportMonth.setItems(months);

        ObservableList<Contact> contact = Contact.getAllContacts();
        reportContact.setItems(contact);

        ObservableList<String> location = FXCollections.observableArrayList();
        for(Appointment appt : Appointment.getAllAppointments()){
            if(!location.contains(appt.getLocation())) {
                location.add(appt.getLocation());
            }
        }
        reportLocation.setItems(location);


    }

    /**
     * Closes the appointment window
     * @param actionEvent
     */
    public void reportClose(ActionEvent actionEvent) {
        ((Stage) (reportCloseButton.getScene().getWindow())).close();
    }

    /**
     * Searches appointments for the specified month and type
     * @param actionEvent
     */
    public void timeMonthRun(ActionEvent actionEvent) {
        if(reportMonth.getSelectionModel().isEmpty() || reportType.getSelectionModel().isEmpty()){
            Utility.alertBox("Please select a month and appointment type.");
            return;
        }
        String month = reportMonth.getSelectionModel().getSelectedItem();
        String type = reportType.getSelectionModel().getSelectedItem();
        int count = 0;
        for (Appointment appt : Appointment.getAllAppointments()) {
            if((appt.getMonthName().equalsIgnoreCase(month)) && (appt.getType().equalsIgnoreCase(type))){
                count ++;
            }
        }
        reportCountBox.setText("Result: " + count);

    }

    /**
     * Searches the specified contact and displays their appointments
     * I chose to use a lambda in this method as it is a neater way to construct the result string
     * @param actionEvent
     */
    public void contactRun(ActionEvent actionEvent) {
        reportText.clear();
        if(reportContact.getSelectionModel().isEmpty()){
            Utility.alertBox("Please select a contact.");
            return;
        }
        StringBuilder resultBuilder = new StringBuilder("Appointment ID | Title | Type | Description | Start Time | End Time | Customer ID" + System.lineSeparator());
        String contact = reportContact.getSelectionModel().getSelectedItem().toString();
        for (Appointment appt : Appointment.getAllAppointments()) {
            if(appt.getContact().equals(contact)){
                Integer apptID = appt.getAppointmentId();
                String title = appt.getTitle();
                String type = appt.getType();
                String desc = appt.getDescription();
                ZonedDateTime start = appt.getStartTime();
                ZonedDateTime end = appt.getEndTime();
                Integer custID = appt.getCustId();

                getResults getReport = (a, b, c, d, e, f, g) ->
                    a + " | " + b + " | " + c + " | " +
                            d + " | " + e + " | " + f + " | " + g + System.lineSeparator();

                resultBuilder.append(getReport.reportUpdate(apptID, title, type, desc, start, end, custID));
                }
            }
        reportText.setText(resultBuilder.toString());
        }

    /**
     * Gets the specified location and counts how many appointments take place there
     * @param actionEvent
     */
    public void locationRun(ActionEvent actionEvent) {
        if(reportLocation.getSelectionModel().isEmpty()){
            Utility.alertBox("Please select a location.");
            return;
        }
        String location = reportLocation.getSelectionModel().getSelectedItem();
        int count = 0;
        for (Appointment appt : Appointment.getAllAppointments()) {
            if((appt.getLocation().equalsIgnoreCase(location))){
                count ++;
            }
        }
        reportLocationBox.setText("Result: " + count);
    }
}

