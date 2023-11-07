package App.Model;

import App.Helper.Time;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.time.ZonedDateTime;

public class Appointment {

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
    private static final ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
    private static final ObservableList<Appointment> weekAppointments = FXCollections.observableArrayList();
    private static final ObservableList<Appointment> monthAppointments = FXCollections.observableArrayList();

    public Appointment(Integer appointmentId, String title, String description, String location, String contact,
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
     * Sets the appointment ID
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
     * Sets the start time
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
     * Sets teh customer ID
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
     * Gets the appointment title
     * @return the appointment title
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
     * Adds an appointment to list
     * @param newAppointment the appointment to add
     */
    public static void addAppointment(Appointment newAppointment){
        allAppointments.add(newAppointment);
    }

    /**
     * Gets all appointments in the list
     * @return the appointments
     */
    public static ObservableList<Appointment> getAllAppointments(){
        return allAppointments;
    }

    /**
     * Clear the appointment lsit
     */
    public static void clearAppointmentList(){
        allAppointments.clear();
    }

    /**
     * Adds an appointment to the week appointment list
     * @param newAppointment the appointment to add
     */
    public static void addWeekAppointment(Appointment newAppointment){
        weekAppointments.add(newAppointment);
    }

    /**
     * Gets the appointments from the week appointment list
     * @return the appointments
     */
    public static ObservableList<Appointment> getWeekAppointments(){
        return weekAppointments;
    }

    /**
     * Clears the week appointment list
     */
    public static void clearWeekAppointmentList(){
        weekAppointments.clear();
    }

    /**
     * Adds an appointment to the month appointment lsit
     * @param newAppointment the appointment to add
     */
    public static void addMonthAppointment(Appointment newAppointment){
        monthAppointments.add(newAppointment);
    }

    /**
     * Gets the appointments from the month appointment list
     * @return the appointments
     */
    public static ObservableList<Appointment> getMonthAppointments(){
        return monthAppointments;
    }

    /**
     * Clears the month appointment lsit
     */
    public static void clearMonthAppointmentList(){
        monthAppointments.clear();
    }

    /**
     * Gets the week number
     * @return the week number
     */
    public Integer getWeek(){
        return Time.getWeekNumber(startTime);
    }

    /**
     * Gets the month number
     * @return the month number
     */
    public Integer getMonth(){
        return Time.getMonthNumber(startTime);
    }

    /**
     * Gets the month name
     * @return the month name
     */
    public String getMonthName(){
        return Time.getMonthName(startTime);
    }
}
