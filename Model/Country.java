package App.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Country {
    private static final ObservableList<Country> allCountries = FXCollections.observableArrayList();

    private Integer id;
    private String name;

    public Country(Integer id, String name){
        this.id = id;
        this.name = name;
    }

    /**
     * Gets the ID
     * @return the ID
     */
    public Integer getCountryId(){
        return id;
    }

    /**
     * Gets the country name
     * @return the country name
     */
    public String getCountryName(){
        return name;
    }

    /**
     * Sets the country ID
     * @param id the country ID
     */
    public void setCountryId(Integer id){
        this.id = id;
    }

    /**
     * Sets the country name
     * @param name the name
     */
    public void setCountryName(String name){
        this.name = name;
    }

    /**
     * Add a country ot the list
     * @param newCountry the country to add
     */
    public static void addCountry(Country newCountry){
        allCountries.add(newCountry);
    }

    /**
     * Clears a country from the list
     */
    public static void clearCountryList(){
        allCountries.clear();
    }

    /**
     * Gets all countries in the list
     * @return teh countries
     */
    public static ObservableList<Country> getAllCountries(){
        return allCountries;
    }

    /**
     * Formats for comboboxes
     * @return the name
     */
    public String toString(){
        return (name);
    }
}
