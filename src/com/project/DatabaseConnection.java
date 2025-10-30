import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/medical_management?serverTimezone=UTC";
    private static final String USER = "root"; // replace with your DB username
    private static final String PASSWORD = "haripete006"; // replace with your DB password

    // Returns a fresh connection every time
    public static Connection getConnection() throws SQLException {
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Database Connected Successfully!");
            return conn;
        } catch (SQLException e) {
            System.out.println("Database Connection Failed!");
            throw e; // propagate exception to handle in calling code
        }
    }
}
