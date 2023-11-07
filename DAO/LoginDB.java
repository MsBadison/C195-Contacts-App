package App.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDB {
    /**
     * Checks if the entered username/password combination is in the Users database
     *
     * @param enteredUsername the entered username
     * @param enteredPassword the entered password
     * @return true if combination exists, false if not
     * @throws SQLException
     */
    public static boolean login(String enteredUsername, String enteredPassword) throws SQLException {
        String sql = "SELECT * FROM USERS WHERE User_Name = ? AND Password = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, enteredUsername);
        ps.setString(2, enteredPassword);
        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            return true;
        }
        else {
            return false;
        }
    }
}
