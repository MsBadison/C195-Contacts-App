package App.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AppointmentType {

    private String type;
    private static final ObservableList<AppointmentType> ALL_APPOINTMENT_TYPES = FXCollections.observableArrayList();

    public AppointmentType(String type){
        this.type = type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    /**
     * Adds an appointment type to the list
     * @param newAppointmentType the appointment type to add
     */
    public static void addAppointmentType(AppointmentType newAppointmentType){
        ALL_APPOINTMENT_TYPES.add(newAppointmentType);
    }

    /**
     * Gets the appointment type from the list
     * @return the appointments
     */
    public static ObservableList<AppointmentType> getAllAppointmentTypes(){
        return ALL_APPOINTMENT_TYPES;
    }

    /**
     * Sets the format for combo boxes
     * @return the type
     */
    public String toString(){
        return (type);
    }
}
