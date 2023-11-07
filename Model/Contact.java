package App.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Contact {

    private Integer id;
    private String name;
    private String email;
    private static final ObservableList<Contact> allContacts = FXCollections.observableArrayList();

    public Contact(Integer id, String name, String email){
        this.id = id;
        this.name = name;
        this.email = email;
    }

    /**
     * Sets the ID
     * @param id the ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Sets the name
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the email
     * @param email the email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the ID
     * @return the ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * Gets the name
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the email
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Gets all contacts from the list
     * @return the contacts
     */
    public static ObservableList<Contact> getAllContacts() {
        return allContacts;
    }

    /**
     * Adds a contact to the list
     * @param newContact the contact to add
     */
    public static void addContact(Contact newContact){
        allContacts.add(newContact);
    }

    /**
     * Clears the list
     */
    public static void clearContactList(){
        allContacts.clear();
    }

    /**
     * Sets the formating for comboboxes
     * @return the name
     */
    public String toString(){
        return (name);
    }
}
