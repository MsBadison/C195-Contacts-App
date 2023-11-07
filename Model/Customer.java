package App.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Customer {

    private Integer id;
    private String name;
    private String address;
    private String postalcode;
    private String state;
    private String country;
    private String phone;

    private static final ObservableList<Customer> allCustomers = FXCollections.observableArrayList();

    public Customer(Integer id, String name, String address, String postalcode, String state, String country, String phone) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.postalcode = postalcode;
        this.state = state;
        this.country = country;
        this.phone = phone;
    }

    /**
     * Gets the IO
     * @return the ID
     */
    public Integer getId(){
        return id;
    }

    /**
     * Gets the name
     * @return the name
     */
    public String getName(){
        return name;
    }

    /**
     * Gets the address
     * @return the address
     */
    public String getAddress(){
        return address;
    }

    /**
     * Gets the postal code
     * @return the postal code
     */
    public String getPostalcode(){
        return postalcode;
    }

    /**
     * Gets the state
     * @return the state
     */
    public String getState(){
        return state;
    }

    /**
     * Gets the country
     * @return the country
     */
    public String getCountry(){
        return country;
    }

    /**
     * Gets the phone number
     * @return the phone number
     */
    public String getPhone(){
        return phone;
    }

    /**
     * Sets the ID
     * @param id the ID
     */
    public void setId(Integer id){
        this.id = id;
    }

    /**
     * Sets the name
     * @param name the name
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * Sets the address
     * @param address the address
     */
    public void setAddress(String address){
        this.address = address;
    }

    public void setPostalcode(String postalcode){
        this.postalcode = postalcode;
    }

    /**
     * Sets the state
     * @param state the state
     */
    public void setState(String state){
        this.state = state;
    }

    /**
     * Sets the country
     * @param country the country
     */
    public void setCountry(String country){
        this.country = country;
    }

    /**
     * Sets the phone number
     * @param phone the phone number
     */
    public void setPhone(String phone){
        this.phone = phone;
    }

    /**
     * Adds a customer to the list
     * @param newCustomer the customer to add
     */
    public static void addCustomer(Customer newCustomer){
        allCustomers.add(newCustomer);
    }

    /**
     * Gets all customer from the list
     * @return the customers
     */
    public static ObservableList<Customer> getAllCustomers(){
        return allCustomers;
    }

    /**
     * Clears the customer list
     */
    public static void clearCustomerList(){
        allCustomers.clear();
    }

    /**
     * Format for comboboxes
     * @return the ID and name
     */
    public String toString(){
        return (id + " - " + name);
    }
}
