import java.sql.*;

public class DBConnection {
    public static Connection connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/hotel_db", "root","");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
