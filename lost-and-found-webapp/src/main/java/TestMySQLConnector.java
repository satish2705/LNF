import java.sql.Connection;
import java.sql.DriverManager;

import io.github.cdimascio.dotenv.Dotenv;


public class TestMySQLConnector {
    public static void main(String[] args) {
    // Database connection parameters
    Dotenv dotenv = Dotenv.load();
    String dbUrl = dotenv.get("DB_URL");
    String dbUser = dotenv.get("DB_USER");
    String dbPassword = dotenv.get("DB_PASSWORD");

    if (dbUrl == null || dbUser == null || dbPassword == null) {
        throw new RuntimeException("Database environment variables not set in .env file.");
    }

        try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {
            if (conn.isValid(2)) { // Check if the connection is valid with a 2-second timeout
                System.out.println("MySQL Connector is working! Database connection successful.");
            } else {
                System.out.println("Connection is not valid.");
            }
        } catch (Exception e) {
            System.out.println("Failed to connect to the database.");
            java.util.logging.Logger.getLogger(TestMySQLConnector.class.getName()).log(java.util.logging.Level.SEVERE, "Failed to connect to the database.", e);
        }
    }
}