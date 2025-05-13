import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import io.github.cdimascio.dotenv.Dotenv;


@WebServlet("/searchItems")
public class SearchItemServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String keyword = request.getParameter("keyword");
        if (keyword == null) {
            keyword = "";
        }
        String type = request.getParameter("type");
        if (type == null || (!type.equals("lost") && !type.equals("found") && !type.equals("all"))) {
            type = "all";
        }

        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");

        // Pagination parameters
        int page = 1; // Default to page 1
        int resultsPerPage = 5; // Default to 5 results per page
        try {
            String pageParam = request.getParameter("page");
            String resultsPerPageParam = request.getParameter("resultsPerPage");
            if (pageParam != null) page = Integer.parseInt(pageParam);
            if (resultsPerPageParam != null) resultsPerPage = Integer.parseInt(resultsPerPageParam);
        } catch (NumberFormatException e) {
            // Use default values if parameters are missing or invalid
        }

        int offset = (page - 1) * resultsPerPage;

        // Load database connection parameters from .env file
        Dotenv dotenv = Dotenv.load();
        String dbUrl = dotenv.get("DB_URL");
        String dbUser = dotenv.get("DB_USER");
        String dbPassword = dotenv.get("DB_PASSWORD");

        if (dbUrl == null || dbUser == null || dbPassword == null) {
            throw new ServletException("Database environment variables not set in .env file.");
        }

        // Build SQL query dynamically
        StringBuilder sql = new StringBuilder("SELECT * FROM items WHERE (item_name LIKE ? OR description LIKE ?)");
        if (!type.equals("all")) {
            sql.append(" AND status = ?");
        }
        if (startDate != null && !startDate.isEmpty()) {
            sql.append(" AND created_at >= ?");
        }
        if (endDate != null && !endDate.isEmpty()) {
            sql.append(" AND created_at <= ?");
        }
        sql.append(" ORDER BY created_at DESC LIMIT ? OFFSET ?");

        try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {

            int paramIndex = 1;
            stmt.setString(paramIndex++, "%" + keyword + "%");
            stmt.setString(paramIndex++, "%" + keyword + "%");
            if (!type.equals("all")) {
                stmt.setString(paramIndex++, type);
            }
            if (startDate != null && !startDate.isEmpty()) {
                stmt.setString(paramIndex++, startDate);
            }
            if (endDate != null && !endDate.isEmpty()) {
                stmt.setString(paramIndex++, endDate);
            }
            stmt.setInt(paramIndex++, resultsPerPage);
            stmt.setInt(paramIndex++, offset);

            ResultSet rs = stmt.executeQuery();
            ArrayList<Map<String, String>> results = new ArrayList<>();
            Gson gson = new Gson();
            String jsonResponse = gson.toJson(results);
            response.getWriter().println(jsonResponse);

            while (rs.next()) {
                Map<String, String> item = new HashMap<>();
                item.put("item_name", rs.getString("item_name"));
                item.put("description", rs.getString("description"));
                item.put("status", rs.getString("status"));
                item.put("created_at", rs.getString("created_at"));
                item.put("image_path", rs.getString("image_path"));
                results.add(item);
            }

            response.setContentType("application/json");
            } catch (SQLException e) {
                Logger.getLogger(SearchItemServlet.class.getName()).log(Level.SEVERE, "Error fetching items", e);
                response.setContentType("application/json");
                response.getWriter().println("{\"error\": \"An error occurred while fetching items.\"}");
        }
    }
}