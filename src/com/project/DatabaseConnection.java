
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/medical_management";
    private static final String USER = "root"; // replace with your DB username
    private static final String PASSWORD = "haripete006"; // replace with your DB password
    private static Connection conn = null;

    public static Connection getConnection() {
        if (conn == null) {
            try {
                conn = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Database Connected Successfully!");
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Database Connection Failed!");
            }
        }
        return conn;
    }
}
