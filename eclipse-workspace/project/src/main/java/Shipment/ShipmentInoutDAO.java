package Shipment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ShipmentInoutDAO {

    private String url = "jdbc:postgresql://localhost:5432/supermarketdb"; // Update with your DB URL
    private String username = "postgres"; // Update with your DB username
    private String password = "gobi"; // Update with your DB password

    // Method to get a shipment by its ID
    public ShipmentInoutVO getShipmentById(String inoutId) throws SQLException {
        ShipmentInoutVO shipment = null;
        String query = "SELECT * FROM m_inout WHERE inout_id = ?";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, inoutId);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                shipment = new ShipmentInoutVO();
                shipment.setInoutId(rs.getString("inout_id"));
                shipment.setDocumentNo(rs.getString("document_no"));
                shipment.setDocumentDate(rs.getDate("document_date"));
                shipment.setCustomerId(rs.getString("customer_id"));
                shipment.setCreatedby(rs.getString("createdby"));
                shipment.setUpdatedby(rs.getString("updatedby"));
            }
        }
        return shipment;
    }

    // Method to add a new shipment
    public void addShipment(ShipmentInoutVO shipment) throws SQLException {
        String query = "INSERT INTO m_inout (document_no, document_date, customer_id, createdby, updatedby) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, shipment.getDocumentNo());
            pst.setDate(2, shipment.getDocumentDate());
            pst.setString(3, shipment.getCustomerId());
            pst.setString(4, shipment.getCreatedby());
            pst.setString(5, shipment.getUpdatedby());
            pst.executeUpdate();
        }
    }

    // Method to update an existing shipment
    public void updateShipment(ShipmentInoutVO shipment) throws SQLException {
        String query = "UPDATE m_inout SET document_no = ?, document_date = ?, customer_id = ?, updatedby = ? WHERE inout_id = ?";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, shipment.getDocumentNo());
            pst.setDate(2, shipment.getDocumentDate());
            pst.setString(3, shipment.getCustomerId());
            pst.setString(4, shipment.getUpdatedby());
            pst.setString(5, shipment.getInoutId());
            pst.executeUpdate();
        }
    }

    // Method to delete a shipment
    public void deleteShipment(String inoutId) throws SQLException {
        String query = "DELETE FROM m_inout WHERE inout_id = ?";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, inoutId);
            pst.executeUpdate();
        }
    }

    // Method to get all shipments with pagination
    public List<ShipmentInoutVO> main(int page, int recordsPerPage) throws SQLException {
        List<ShipmentInoutVO> shipments = new ArrayList<>();
        String query = "SELECT * FROM m_inout LIMIT ? OFFSET ?";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setInt(1, recordsPerPage);
            pst.setInt(2, (page - 1) * recordsPerPage);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                ShipmentInoutVO shipment = new ShipmentInoutVO();
                shipment.setInoutId(rs.getString("inout_id"));
                shipment.setDocumentNo(rs.getString("document_no"));
                shipment.setDocumentDate(rs.getDate("document_date"));
                shipment.setCustomerId(rs.getString("customer_id"));
                shipment.setCreatedby(rs.getString("createdby"));
                shipment.setUpdatedby(rs.getString("updatedby"));
                shipments.add(shipment);
            }
        }
        return shipments;
    }
    
    // Method to get the total count of shipments
    public int getShipmentCount() throws SQLException {
        String query = "SELECT COUNT(*) FROM m_inout";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement pst = connection.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }
}
