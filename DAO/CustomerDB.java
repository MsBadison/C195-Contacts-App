package App.DAO;

import App.Helper.Time;
import App.Model.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;

public class CustomerDB {

    /**
     * Imports customers from SQL database
     *
     * @throws SQLException
     */
    public static void importCustomers() throws SQLException {
        Customer.clearCustomerList();
        String sql = "SELECT * FROM CUSTOMERS";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){

            Integer id = rs.getInt("Customer_ID");
            String name = rs.getString("Customer_Name");
            String address = rs.getString("Address");
            String postalcode = rs.getString("Postal_Code");
            String phone = rs.getString("Phone");
            String state = convertDivisionIDToState(rs.getInt("Division_Id"));
            String country = convertDivisionIDToCountry(rs.getInt("Division_Id"));

            Customer customer = new Customer(id, name, address, postalcode, state, country, phone);
            Customer.addCustomer(customer);
        }
    }

    /**
     * Imports countries from SQL database
     *
     * @throws SQLException
     */
    public static void importCountries() throws SQLException {
        Country.clearCountryList();
        String sql = "SELECT * FROM COUNTRIES";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Country country = new Country(rs.getInt("Country_ID"), rs.getString("Country"));
            Country.addCountry(country);
        }
    }

    /**
     * Imports divisions from SQL database
     *
     * @throws SQLException
     */
    public static void importDivisions() throws SQLException {
        Division.clearDivisionList();
        String sql = "SELECT * FROM FIRST_LEVEL_DIVISIONS";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Division division = new Division(rs.getInt("Division_ID"), rs.getString("Division"), rs.getInt("Country_ID"));
            Division.addDivision(division);
        }
    }

    /**
     * Inserts a new customer into the Customers database
     *
     * @param name the customer name to be inserted
     * @param address the customer address to be inserted
     * @param zipcode the customer zipcode to be inserted
     * @param division the customer division ID to be inserted
     * @param phone the customer phone number to be inserted
     * @param createdBy the user who is logged in when the customer is created
     * @throws SQLException
     */
    public static void insertCustomer(String name, String address, String zipcode, int division, String phone, String createdBy, Timestamp createTime) throws SQLException {
        String sql = "INSERT INTO CUSTOMERS (Customer_Name, Address, Postal_Code, Phone, Created_By, Create_Date, Division_ID) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, name);
        ps.setString(2, address);
        ps.setString(3, zipcode);
        ps.setString(4, phone);
        ps.setString(5, createdBy);
        ps.setTimestamp(6, createTime);
        ps.setInt(7, division);
        ps.executeUpdate();
    }

    /**
     * Deletes any customer with the matching ID from the Customer database
     *
     * @param id the ID of the customer to be deleted
     * @return the numbers of rows affected by the delete operation
     * @throws SQLException
     */
    public static Integer deleteCustomer(Integer id) throws SQLException {
        String sql = "DELETE FROM CUSTOMERS WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, id);
        Integer rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**
     * Updates a customer in the database
     * @param name the customer name
     * @param address the customer address
     * @param zipcode the customer zipcode
     * @param division the customer division
     * @param phone teh customer phone number
     * @param lastUpdate the customer update time
     * @param lastUpdateUser the user who updated the customer information
     * @param id the customer ID
     * @throws SQLException
     */
    public static void updateCustomer(String name, String address, String zipcode, int division, String phone, Timestamp lastUpdate, String lastUpdateUser, int id) throws SQLException {
        String sql = "UPDATE CUSTOMERS SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Division_ID = ?, Last_Update = ?, Last_Updated_By = ? WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, name);
        ps.setString(2, address);
        ps.setString(3, zipcode);
        ps.setString(4, phone);
        ps.setInt(5, division);
        ps.setTimestamp(6, lastUpdate);
        ps.setString(7, lastUpdateUser);
        ps.setInt(8, id);
        ps.executeUpdate();
    }

    /**
     * Converts a division ID to the name of that division
     *
     * @param divisionId the division ID to convert
     * @return the name of the division
     * @throws SQLException
     */
    public static String convertDivisionIDToState(int divisionId) throws SQLException {
        String sql = "SELECT * FROM FIRST_LEVEL_DIVISIONS WHERE DIVISION_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, divisionId);
        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            return rs.getString("Division");
        }
        else{
            return "";
        }
    }

    /**
     * Converts the name of a division to its division ID
     *
     * @param state the name of the division
     * @return the division ID
     * @throws SQLException
     */
    public static Integer convertStateToDivisionID(String state) throws SQLException {
        String sql = "SELECT * FROM FIRST_LEVEL_DIVISIONS WHERE Division = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, state);
        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            return rs.getInt("Division_ID");
        }
        else{
            return null;
        }
    }

    /**
     * Converts a division ID to its respective country name
     *
     * @param divisionId the division ID to convert
     * @return the name of the country
     * @throws SQLException
     */
    public static String convertDivisionIDToCountry(int divisionId) throws SQLException {
        String sql = "SELECT * FROM FIRST_LEVEL_DIVISIONS WHERE DIVISION_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, divisionId);
        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            return convertCountryId(rs.getInt("Country_ID"));
        }
        else{
            return "";
        }
    }

    /**
     * Converts a country ID to the country's name
     *
     * @param countryId the country ID to convert
     * @return the name of the country
     * @throws SQLException
     */
    public static String convertCountryId(int countryId) throws SQLException {
        String sql = "SELECT * FROM COUNTRIES WHERE COUNTRY_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, countryId);
        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            return rs.getString("Country");
        }
        else{
            return "";
        }
    }

    /**
     * Converts the name of a country to that country's ID
     *
     * @param name the name of the country to convert
     * @return the country ID
     * @throws SQLException
     */
    public static Integer convertCountryNameToId(String name) throws SQLException {
        String sql = "SELECT * FROM COUNTRIES WHERE COUNTRY = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, name);
        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            return rs.getInt("Country_ID");
        }
        else{
            return null;
        }
    }

    /**
     * Converts a customer ID to name
     * @param id the ID to convert
     * @return the customer name
     * @throws SQLException
     */
    public static String convertCustomerIDtoName(Integer id) throws SQLException {
        String sql = "SELECT * FROM CUSTOMERS WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            return rs.getString("Customer_Name");
        }
        else{
            return "";
        }
    }

}
