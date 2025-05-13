import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@MultipartConfig
@WebServlet("/submitItem")
public class SubmitItemServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(SubmitItemServlet.class.getName());

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve form parameters
        String itemName = request.getParameter("itemName");
        String description = request.getParameter("description");
        String status = request.getParameter("status");
        String contact = request.getParameter("contact");

        // Retrieve the uploaded file
        Part filePart = request.getPart("image");
        String fileName = System.currentTimeMillis() + "_" + filePart.getSubmittedFileName();
        String uploadDirPath = "C:/uploads";
        File uploadDir = new File(uploadDirPath);
        if (!uploadDir.exists()) uploadDir.mkdir();
        String filePath = uploadDirPath + File.separator + fileName;

        Files.copy(filePart.getInputStream(), new File(filePath).toPath());
        LOGGER.info("File Path: " + filePath);

        // Debugging logs
        LOGGER.info("Item Name: " + itemName);
        LOGGER.info("Description: " + description);
        LOGGER.info("Status: " + status);
        LOGGER.info("Contact: " + contact);

        // Hardcoded database connection parameters
        String dbUrl = "jdbc:mysql://localhost:3306/lostandfound";
        String dbUser = "abhinai"; // Replace with your MySQL username
        String dbPassword = "abhi"; // Replace with your MySQL password

        try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {
            String sql = "INSERT INTO items (item_name, description, status, contact, image_path) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, itemName);
                stmt.setString(2, description);
                stmt.setString(3, status);
                stmt.setString(4, contact);
                stmt.setString(5, filePath);

                LOGGER.info("Executing query: " + stmt);
                stmt.executeUpdate();
                LOGGER.info("Data inserted successfully!");
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "SQL query failed!", e);
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Database connection failed!", e);
        }
    }
}