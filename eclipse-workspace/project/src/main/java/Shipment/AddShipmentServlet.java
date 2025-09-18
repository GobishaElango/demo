package Shipment;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.ResultSet;
import org.json.JSONArray;
import org.json.JSONObject;

@WebServlet("/AddShipmentServlet")
public class AddShipmentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // PostgreSQL connection details
    private final static String url = "jdbc:postgresql://localhost:5432/supermarketdb";
    private final static String username = "postgres";
    private final static String password = "gobi";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Set response content type
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        try {
            // Get shipment details from the form
            String documentNo = request.getParameter("document_no");
            String documentDate = request.getParameter("document_date");
            String customerId = request.getParameter("customer_id");
            String linesJson = request.getParameter("shipment_lines");  // This is the shipment lines JSON string

            // Parse shipment lines
            JSONArray shipmentLines = new JSONArray(linesJson);

            // Current timestamp for created and updated fields
            Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());

            // Insert into m_inout table
            String insertInoutQuery = "INSERT INTO m_inout (document_no, document_date, customer_id, created, updated, createdby, updatedby) "
                                    + "VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING inout_id";

            // Insert into m_inoutline table
            String insertInoutlineQuery = "INSERT INTO m_inoutline (inout_id, product_id, quantity, created, updated, createdby, updatedby) "
                                        + "VALUES (?, ?, ?, ?, ?, ?, ?)";

            try (Connection conn = DriverManager.getConnection(url, username, password)) {
                conn.setAutoCommit(false);  // Start transaction

                // Insert into m_inout table
                PreparedStatement pstInout = conn.prepareStatement(insertInoutQuery);
                pstInout.setString(1, documentNo);
                pstInout.setString(2, documentDate);
                pstInout.setString(3, customerId);
                pstInout.setTimestamp(4, currentTimestamp);
                pstInout.setTimestamp(5, currentTimestamp);
                pstInout.setString(6, "user_id"); // Replace with actual user from session or auth
                pstInout.setString(7, "user_id"); // Replace with actual user from session or auth
                ResultSet rsInout = pstInout.executeQuery();
                
                // Retrieve the generated inout_id
                String inoutId = "";
                if (rsInout.next()) {
                    inoutId = rsInout.getString("inout_id");
                }

                // Insert shipment lines into m_inoutline table
                PreparedStatement pstInoutline = conn.prepareStatement(insertInoutlineQuery);
                for (int i = 0; i < shipmentLines.length(); i++) {
                    JSONObject line = shipmentLines.getJSONObject(i);
                    String productId = line.getString("product_id");
                    int quantity = line.getInt("quantity");

                    pstInoutline.setString(1, inoutId);
                    pstInoutline.setString(2, productId);
                    pstInoutline.setInt(3, quantity);
                    pstInoutline.setTimestamp(4, currentTimestamp);
                    pstInoutline.setTimestamp(5, currentTimestamp);
                    pstInoutline.setString(6, "user_id"); // Replace with actual user from session or auth
                    pstInoutline.setString(7, "user_id"); // Replace with actual user from session or auth

                    pstInoutline.addBatch();  // Add to batch for execution
                }

                pstInoutline.executeBatch();  // Execute all lines in batch
                conn.commit();  // Commit the transaction

                // Send success response
                out.println("{\"status\": \"success\", \"message\": \"Shipment and product lines added successfully!\"}");
            } catch (SQLException e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error inserting data into the database.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid request data.");
        }
    }
}
