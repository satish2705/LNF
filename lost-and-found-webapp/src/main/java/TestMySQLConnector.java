import java.sql.Connection;
import java.sql.DriverManager;

public class TestMySQLConnector {
    public static void main(String[] args) {
        String dbUrl = "jdbc:mysql://localhost:3306/lostandfound"; // Replace with your database URL
        String dbUser = "abhinai"; // Replace with your MySQL username
        String dbPassword = "abhi"; // Replace with your MySQL password

        try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {
            System.out.println("MySQL Connector is working! Database connection successful.");
        } catch (Exception e) {
            System.out.println("Failed to connect to the database.");
            e.printStackTrace();
        }
    }
}