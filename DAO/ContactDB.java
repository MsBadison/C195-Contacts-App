package App.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ContactDB {

    /**
     * Converts a contact name to ID
     * @param name the name to convert
     * @return the contact ID
     * @throws SQLException
     */
    public static Integer convertContactNameToId(String name) throws SQLException {
        String sql = "SELECT * FROM CONTACTS WHERE Contact_Name = ?";
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
}
