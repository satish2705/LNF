import java.sql.Connection;
import java.sql.DriverManager;
import java.util.logging.Level;
import java.util.logging.Logger;
public class TestDatabaseConnection {
    private static final Logger LOGGER = Logger.getLogger(TestDatabaseConnection.class.getName());

    public static void main(String[] args) {
        String dbUrl = "jdbc:mysql://localhost:3306/lostandfound";
        String dbUser = "abhinai";
        String dbPassword = "abhi";
        try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {
            if (conn.isValid(2)) { // Check if the connection is valid with a 2-second timeout
                System.out.println("Database connection is valid and successful!");
            } else {
                System.out.println("Database connection is not valid.");
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Database connection failed", e);
        }
    }
}