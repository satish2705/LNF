
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;

import io.github.cdimascio.dotenv.Dotenv;

public class TestDatabaseConnection {

    private static final Logger LOGGER = Logger.getLogger(TestDatabaseConnection.class.getName());

    public static void main(String[] args) throws ServletException {

        // Database connection parameters
        Dotenv dotenv = Dotenv.load();
        String dbUrl = dotenv.get("DB_URL");
        String dbUser = dotenv.get("DB_USER");
        String dbPassword = dotenv.get("DB_PASSWORD");

        if (dbUrl == null || dbUser == null || dbPassword == null) {
            throw new ServletException("Database environment variables not set in .env file.");
        }

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
