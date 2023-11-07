package App.Model;

public class User {

    private static String userName;

    /**
     * Sets the user
     * @param loggedInUser the user
     */
    public static void setUser(String loggedInUser){
        userName = loggedInUser;
    }

    /**
     * Gets the user
     * @return the user
     */
    public static String getUser() {
        return userName;
    }

}
