package App.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Division {
    private Integer id;
    private String name;
    private Integer country;

    private static final ObservableList<Division> allDivisions = FXCollections.observableArrayList();


    public Division(Integer id, String name, Integer country){
        this.id = id;
        this.name = name;
        this.country = country;
    }

    /**
     * Gets the division ID
     * @return
     */
    public Integer getDivisionId(){
        return id;
    }

    /**
     * Gets the division name
     * @return the division name
     */
    public String getDivisionName(){
        return name;
    }

    /**
     * Gets the division country
     * @return the division country
     */
    public Integer getDivisionCountry(){
        return country;
    }

    /**
     * Sets the division ID
     * @param id the division ID
     */
    public void setDivisionId(Integer id){
        this.id = id;
    }

    /**
     * Sets the division name
     * @param name teh divison name
     */
    public void setDivisionName(String name){
        this.name = name;
    }

    /**
     * Sets the division country
     * @param country the division country
     */
    public void setDivisionCountry(Integer country){
        this.country = country;
    }

    /**
     * Adds a division to the list
     * @param newDivision the divison to add
     */
    public static void addDivision(Division newDivision){
        allDivisions.add(newDivision);
    }

    /**
     * Clears the division list
     */
    public static void clearDivisionList(){
        allDivisions.clear();
    }

    /**
     * Gets the divisions from the list
     * @return the divisions
     */
    public static ObservableList<Division> getAllDivisions(){
        return allDivisions;
    }

    /**
     * Format for combo boxes
     * @return name
     */
    public String toString(){
        return (name);
    }


}
