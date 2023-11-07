package App.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.time.ZonedDateTime;

public class ApptOverlap {

    private Integer appointmentId;
    private String title;
    private String description;
    private String location;
    private String contact;
    private String type;
    private ZonedDateTime startTime;
    private ZonedDateTime endTime;
    private Integer custId;
    private Integer userId;
    private static final ObservableList<ApptOverlap> allAppointments = FXCollections.observableArrayList();

    public ApptOverlap(Integer appointmentId, String title, String description, String location, String contact,
                       String type, ZonedDateTime startTime, ZonedDateTime endTime, Integer custId, Integer userId ){
        this.appointmentId = appointmentId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.contact = contact;
        this.type = type;
        this.startTime = startTime;
        this.endTime = endTime;
        this.custId = custId;
        this.userId = userId;
    }

    /**
     * Sets appointment ID
     * @param appointmentId the appointment ID
     */
    public void setAppointmentId(Integer appointmentId) {
        this.appointmentId = appointmentId;
    }

    /**
     * Sets the title
     * @param title the title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Sets the description
     * @param description the description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Sets the location
     * @param location the location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Sets the contact
     * @param contact the contact
     */
    public void setContact(String contact) {
        this.contact = contact;
    }

    /**
     * Sets the type
     * @param type the type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Sets teh start time
     * @param startTime the start time
     */
    public void setStartTime(ZonedDateTime startTime) {
        this.startTime = startTime;
    }

    /**
     * Sets the end time
     * @param endTime the end time
     */
    public void setEndTime(ZonedDateTime endTime) {
        this.endTime = endTime;
    }

    /**
     * Sets the customer ID
     * @param custId the customer ID
     */
    public void setCustId(Integer custId) {
        this.custId = custId;
    }

    /**
     * Sets the user ID
     * @param userId the user ID
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * Gets the appointment ID
     * @return the appointment ID
     */
    public Integer getAppointmentId() {
        return appointmentId;
    }

    /**
     * Gets the title
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets the description
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the location
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Gets the contact
     * @return the contact
     */
    public String getContact() {
        return contact;
    }

    /**
     * Gets the type
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Gets the start time
     * @return the start time
     */
    public ZonedDateTime getStartTime() {
        return startTime;
    }

    /**
     * Gets the end time
     * @return the end time
     */
    public ZonedDateTime getEndTime() {
        return endTime;
    }

    /**
     * Gets the customer ID
     * @return the customer ID
     */
    public Integer getCustId() {
        return custId;
    }

    /**
     * Gets the user ID
     * @return the user ID
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * Adds an appointment to the list
     * @param newAppointment teh appointment to add
     */
    public static void addAppointment(ApptOverlap newAppointment){
        allAppointments.add(newAppointment);
    }

    /**
     * Gets all appointments from the lsit
     * @return the appointments
     */
    public static ObservableList<ApptOverlap> getAllAppointments(){
        return allAppointments;
    }

    public static void clearAppointmentList(){
        allAppointments.clear();
    }

}

