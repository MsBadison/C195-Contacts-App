package App.Helper;

import App.Model.Appointment;
import App.Model.ApptOverlap;
import App.Model.Division;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import java.time.*;
import java.util.TimeZone;

public class Utility {

    /**
     * Displays a pop-up alert on the screen
     *
     * @param message the string to be displayed
     */
    public static void alertBox(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Creates a list of divisions with the specified country ID
     *
     * @param selectedCountry the selected country
     * @return the divisions in that country
     */
    public static ObservableList<Division> filterDivisions(Integer selectedCountry){
        ObservableList<Division> filteredDivisions = FXCollections.observableArrayList();
        ObservableList<Division> divisionsList = Division.getAllDivisions();
        for (Division searchDivison: divisionsList){
            if (searchDivison.getDivisionCountry().equals(selectedCountry)){
                filteredDivisions.add(searchDivison);
            }
        }
        return filteredDivisions;
    }

    /**
     * Checks if there are any appointments within 15 minutes
     */
    public static void appointmentCheck() {
        ZonedDateTime timeNow = ZonedDateTime.now().withZoneSameInstant(ZoneId.of("UTC"));
        ZonedDateTime timeIn16Minutes = timeNow.plusMinutes(16);
        String message = "";
        int appts = 0;
        for (Appointment appt : Appointment.getAllAppointments()) {
            if (appt.getStartTime().isAfter(timeNow) && appt.getStartTime().isBefore(timeIn16Minutes)) {
                appts ++;
                message += "\nAppointment ID: "+appt.getAppointmentId()+" at " + appt.getStartTime();
            }
        }
        if (appts == 0) {
            message = "There are no appointments within 15 minutes.";
        }
        Utility.alertBox(message);
    }

    /**
     * Checks if there are overlapping appointments
     * @param date the date
     * @param startTime teh start time
     * @param endTime the end time
     * @return boolean
     */
    public static boolean apptOverlapCheck(LocalDate date, LocalTime startTime, LocalTime endTime) {
        int appts = 0;
        ZoneId localZoneId = ZoneId.of(TimeZone.getDefault().getID());
        ZonedDateTime start = ZonedDateTime.of(date, startTime, localZoneId);
        ZonedDateTime end = ZonedDateTime.of(date, endTime, localZoneId);
        for (ApptOverlap appt : ApptOverlap.getAllAppointments()) {
            if (appt.getStartTime().isAfter(start) && appt.getEndTime().isBefore(end)) {
                appts ++;
            }
            if (appt.getStartTime().isBefore(start) && appt.getEndTime().isAfter(end)) {
                appts ++;
            }
            if (appt.getStartTime().isBefore(start) && appt.getEndTime().isAfter(start)) {
                appts ++;
            }
            if (appt.getStartTime().isBefore(end) && appt.getEndTime().isAfter(end)) {
                appts ++;
            }
            if (appt.getStartTime().isEqual(start) && appt.getEndTime().isBefore(end)) {
                appts ++;
            }
            if (appt.getStartTime().isEqual(start) && appt.getEndTime().isEqual(end)) {
                appts ++;
            }
            if (appt.getStartTime().isEqual(start) && appt.getEndTime().isAfter(end)) {
                appts ++;
            }
            if (appt.getStartTime().isBefore(start) && appt.getEndTime().isEqual(end)) {
                appts ++;
            }
            if (appt.getStartTime().isAfter(start) && appt.getEndTime().isEqual(end)) {
                appts ++;
            }
        }
        if (appts == 0) {
            return false;
        }
        else{
            return true;
        }
    }
}
