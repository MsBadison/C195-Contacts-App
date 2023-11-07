package App.DAO;

import App.Helper.Time;
import App.Model.Appointment;
import App.Model.ApptOverlap;
import App.Model.Contact;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;

public class AppointmentDB {

    /**
     * Imports the appointments from the database
     * @throws SQLException
     */
    public static void importAppointments() throws SQLException {
        Appointment.clearAppointmentList();
        String sql = "SELECT * FROM APPOINTMENTS";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){

            Integer appointmentId = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String contact = convertContactId(rs.getInt("Contact_ID"));
            String type = rs.getString("Type");

            LocalDate startDate = rs.getDate("Start").toLocalDate();
            LocalTime startTime = rs.getTime("Start").toLocalTime();
            ZonedDateTime start = Time.convertLocaltoZoned(startDate, startTime);

            LocalDate endDate = rs.getDate("End").toLocalDate();
            LocalTime endTime = rs.getTime("End").toLocalTime();
            ZonedDateTime end = Time.convertLocaltoZoned(endDate, endTime);

            Integer custId = rs.getInt("Customer_ID");
            Integer userId = rs.getInt("User_ID");

            Appointment appointment = new Appointment(appointmentId, title, description, location, contact, type, start,
                    end, custId, userId);
            Appointment.addAppointment(appointment);
        }
    }

    /**
     * Converts a contact ID to a contact name
     * @param contactId the ID to convert
     * @return the contact name
     * @throws SQLException
     */
    public static String convertContactId(int contactId) throws SQLException {
        String sql = "SELECT * FROM CONTACTS WHERE CONTACT_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, contactId);
        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            return rs.getString("Contact_Name");
        }
        else{
            return "";
        }
    }

    /**
     * Converts a contact name to a contact ID
     * @param name the name to convert
     * @return the contact ID
     * @throws SQLException
     */
    public static Integer convertContactName(String name) throws SQLException {
        String sql = "SELECT * FROM CONTACTS WHERE CONTACT_Name = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, name);
        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            return rs.getInt("Contact_ID");
        }
        else{
            return null;
        }
    }

    /**
     * Imports the contacts from the database
     * @throws SQLException
     */
    public static void importContacts() throws SQLException {
        Contact.clearContactList();
        String sql = "SELECT * FROM CONTACTS";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            Integer id = rs.getInt("Contact_ID");
            String name = rs.getString("Contact_Name");
            String email = rs.getString("Email");

            Contact contact = new Contact(id, name, email);
            Contact.addContact(contact);
        }
    }

    /**
     * Converts a user name to user ID
     * @param userName the name to convert
     * @return the user ID
     * @throws SQLException
     */
    public static Integer convertUserToId(String userName) throws SQLException {
        String sql = "SELECT * FROM USERS WHERE USER_NAME = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, userName);
        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            return (rs.getInt("User_ID"));
        }
        else{
            return null;
        }
    }

    /**
     * Adds a new appointment to the database
     * @param title the appointment
     * @param description the appointment description
     * @param location the appointment location
     * @param type the appointment type
     * @param start the appointment start time
     * @param end the appointment end time
     * @param creation the appointment creation time
     * @param createdBy the user who createds the appointment
     * @param custID the appointment the customer ID
     * @param userID the user ID
     * @param contact the contact
     * @throws SQLException
     */
    public static void insertAppointment(String title, String description, String location, String type,
                                         Timestamp start, Timestamp end, Timestamp creation, String createdBy,
                                         Integer custID, Integer userID, Integer contact) throws SQLException {
        String sql = "INSERT INTO APPOINTMENTS (Title, Description, Location, Type, Start, End, Create_Date, " +
                "Created_By, Customer_ID, User_ID, Contact_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setTimestamp(5, start);
        ps.setTimestamp(6, end);
        ps.setTimestamp(7, creation);
        ps.setString(8, createdBy);
        ps.setInt(9,custID);
        ps.setInt(10, userID);
        ps.setInt(11, contact);
        ps.executeUpdate();
    }

    /**
     * Deletes an appointment from the database
     * @param id the appointment ID to delete
     * @return the number of rows affected
     * @throws SQLException
     */
    public static Integer deleteAppointment(Integer id) throws SQLException {
        String sql = "DELETE FROM APPOINTMENTS WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, id);
        Integer rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**
     * Deletes a customer's appointments
     * @param id the customer ID
     * @return the number of rows affected
     * @throws SQLException
     */
    public static Integer deleteCustAppt(Integer id) throws SQLException {
        String sql = "DELETE FROM APPOINTMENTS WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, id);
        Integer rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**
     * Adds appointment to appointment overlap list
     * @param id the appointment ID
     * @throws SQLException
     */
    public static void apptSameCustomer(Integer id) throws SQLException {
        ApptOverlap.clearAppointmentList();
        String sql = "SELECT * FROM APPOINTMENTS WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){

            Integer appointmentId = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String contact = convertContactId(rs.getInt("Contact_ID"));
            String type = rs.getString("Type");

            LocalDate startDate = rs.getDate("Start").toLocalDate();
            LocalTime startTime = rs.getTime("Start").toLocalTime();
            ZonedDateTime start = Time.convertLocaltoZoned(startDate, startTime);

            LocalDate endDate = rs.getDate("End").toLocalDate();
            LocalTime endTime = rs.getTime("End").toLocalTime();
            ZonedDateTime end = Time.convertLocaltoZoned(endDate, endTime);

            Integer custId = rs.getInt("Customer_ID");
            Integer userId = rs.getInt("User_ID");

            ApptOverlap appointment = new ApptOverlap(appointmentId, title, description, location, contact, type, start,
                    end, custId, userId);
            ApptOverlap.addAppointment(appointment);
        }
    }

    /**
     * Updates an appointment's information
     * @param title the appointment title
     * @param description the appointment description
     * @param location the appointment location
     * @param contact the appointment contact
     * @param type the appointment type
     * @param startTime the appointment start time
     * @param endTime the appointment end time
     * @param updateTime the appointment update time
     * @param updateUser the user who updated the appointment
     * @param custId the appointment customer ID
     * @param userID the appointment user ID
     * @param apptId the appointment ID
     * @throws SQLException
     */
    public static void updateAppt(String title, String description, String location, int contact, String type, Timestamp startTime, Timestamp endTime, Timestamp updateTime, String updateUser, int custId, int userID, int apptId) throws SQLException {
        String sql = "UPDATE APPOINTMENTS SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Last_Update = ?, Last_Updated_By = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setTimestamp(5, startTime);
        ps.setTimestamp(6, endTime);
        ps.setTimestamp(7, updateTime);
        ps.setString(8, updateUser);
        ps.setInt(9, custId);
        ps.setInt(10, userID);
        ps.setInt(11, contact);
        ps.setInt(12, apptId);
        ps.executeUpdate();
    }

}
