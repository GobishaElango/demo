package com.qualiantech.goodsshipment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.UUID;

public class ShipmentInoutDAO {

	private String url = "jdbc:postgresql://localhost:5432/supermarketdb"; // Update with your DB URL
	private String username = "postgres"; // Update with your DB username
	private String password = "qualian"; // Update with your DB password

	// Add shipment header and its lines
	public void addShipment(ShipmentInoutVO shipment) throws SQLException {
		String insertShipmentSQL = "INSERT INTO public.m_inout (inout_id,document_no, document_date, customer_id, createdby, updatedby) "
				+ "VALUES (?,?,?, ?, ?, ?)";
		String insertLineSQL = "INSERT INTO public.m_inoutline (inoutline_id,inout_id, product_id, quantity, createdby, updatedby) "
				+ "VALUES (?, ?, ?, ?, ?, ?)";
		try (Connection conn = DBConnection.getConnection();
				PreparedStatement stmtShipment = conn.prepareStatement(insertShipmentSQL);
				PreparedStatement stmtLine = conn.prepareStatement(insertLineSQL)) {

			stmtShipment.setString(1, shipment.getInoutId());
			stmtShipment.setString(2, shipment.getDocumentNo());
			stmtShipment.setDate(3, shipment.getDocumentDate());
			stmtShipment.setString(4, shipment.getCustomerId());
			stmtShipment.setString(5, shipment.getCreatedby());
			stmtShipment.setString(6, shipment.getUpdatedby());
			stmtShipment.executeUpdate();

			for (ShipmentLineVO line : shipment.getLineItems()) {
//                	    stmtLine.setObject(1, UUID.fromString(inoutId));
				String inoutlineIdWithDashes = UUID.randomUUID().toString();
				String inoutlineId = inoutlineIdWithDashes.replaceAll("-", "");
				stmtLine.setString(1, inoutlineId);
				stmtLine.setString(2, shipment.getInoutId());
				stmtLine.setString(3, line.getProductId());
				stmtLine.setInt(4, line.getQuantity());
				stmtLine.setString(5, shipment.getCreatedby());
				stmtLine.setString(6, shipment.getUpdatedby());
				stmtLine.addBatch();
			}
			stmtLine.executeBatch();
	}
	}
	// Add a shipment line
	public void addShipmentLine(String inoutlineId, String inoutId, String productId, int quantity, String createdBy,
			String updatedBy) throws SQLException {
		String sql = "INSERT INTO public.m_inoutline (inoutline_id, inout_id, product_id, quantity, createdby, updatedby) "
				+ "VALUES (?, ?, ?, ?, ?, ?)";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, inoutlineId);
			stmt.setString(2, inoutId);
			stmt.setString(3, productId); 
			stmt.setInt(4, quantity);
			stmt.setString(5, createdBy); 
			stmt.setString(6, updatedBy);
			stmt.executeUpdate();

		}
	}

	public List<ShipmentLineVO> getShipmentLines(String inoutId) throws SQLException {
		String sql = "SELECT * FROM public.m_inoutline WHERE inout_id = ?";
		List<ShipmentLineVO> lines = new ArrayList<>();

		try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, inoutId);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				ShipmentLineVO line = new ShipmentLineVO();
				line.setInoutlineId(rs.getString("inoutline_id"));
				line.setProductId(rs.getString("product_id"));
				line.setQuantity(rs.getInt("quantity"));
				lines.add(line);
			}
		}
		return lines;
	}

	// Delete a shipment line
	public boolean deleteShipmentLine(String inoutlineId) throws SQLException {
		String sql = "DELETE FROM public.m_inoutline WHERE inoutline_id = ?";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, inoutlineId);
			int rowsAffected = stmt.executeUpdate();
			return rowsAffected > 0;
		}
	}

	public Vector<ShipmentInoutVO> getShipmentById() throws SQLException {
		ShipmentInoutVO shipment = null;
		String query = "select * from m_inout ORDER BY document_no ASC";
		Connection connection = null;
		Vector<ShipmentInoutVO> ls = new Vector<ShipmentInoutVO>();

		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection(url, username, password);

			PreparedStatement pst = connection.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			PreparedStatement pst1 = null;
			ResultSet rs1 = null;

			while (rs.next()) {
				String currentInoutId = rs.getString("inout_id");

				if (shipment == null || !shipment.getInoutId().equals(currentInoutId)) {
					if (shipment != null) {
						ls.add(shipment);
					}

					shipment = new ShipmentInoutVO();
					shipment.setInoutId(currentInoutId);
					shipment.setDocumentNo(rs.getString("document_no"));
					shipment.setDocumentDate(rs.getDate("document_date"));
					shipment.setCustomerId(rs.getString("customer_id"));
					shipment.setCreatedby(rs.getString("createdby"));
					shipment.setUpdatedby(rs.getString("updatedby"));

					String queryLine = "select * from m_inoutline where inout_id='" + currentInoutId + "'";
					pst1 = connection.prepareStatement(queryLine);
					rs1 = pst1.executeQuery();
					Vector<ShipmentLineVO> v1 = new Vector<ShipmentLineVO>();
					while (rs1.next()) {
						ShipmentLineVO lineItem = new ShipmentLineVO();
						lineItem.setProductId(rs1.getString("product_id"));
						lineItem.setQuantity(rs1.getInt("quantity"));
						v1.add(lineItem);
					}
					shipment.setLineItems(v1);
				}
			}

			if (shipment != null) {
				ls.add(shipment);
			}

			return ls;

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				connection.close();
			}
		}

		return ls;
	}

	// Method to update an existing shipment
	public void updateShipment(ShipmentInoutVO shipment) throws SQLException {
		String query = "UPDATE m_inout SET document_no = ?, document_date = ?, customer_id = ?, updatedby = ? WHERE inout_id = ?";
		String queryDel = "DELETE from m_inoutline WHERE inout_id = ?";
		try (Connection connection = DriverManager.getConnection(url, username, password)) {

			try (PreparedStatement pstUpdate = connection.prepareStatement(query)) {
				pstUpdate.setString(1, shipment.getDocumentNo());
				pstUpdate.setDate(2, shipment.getDocumentDate());
				pstUpdate.setString(3, shipment.getCustomerId());
				pstUpdate.setString(4, shipment.getUpdatedby());
				pstUpdate.setString(5, shipment.getInoutId());
				pstUpdate.executeUpdate();
			}

			try (PreparedStatement pstDelete = connection.prepareStatement(queryDel)) {
				pstDelete.setString(1, shipment.getInoutId());
				pstDelete.executeUpdate();
			}
			for (ShipmentLineVO line : shipment.getLineItems()) {

				String inoutlineIdWithDashes = UUID.randomUUID().toString();
				String inoutlineId = inoutlineIdWithDashes.replaceAll("-", "");

				addShipmentLine(inoutlineId, shipment.getInoutId(), line.getProductId(), line.getQuantity(), "22",
						"22");
			}
		}
	}

	public boolean deleteShipment(String inoutId) {
		String deleteLinesSql = "DELETE FROM m_inoutline WHERE inout_id = ?";
		String deleteShipmentSql = "DELETE FROM m_inout WHERE inout_id = ?";

		Connection conn = null;
		PreparedStatement stmtLines = null;
		PreparedStatement stmtShipment = null;

		try {
			Class.forName("org.postgresql.Driver");
			conn = DBConnection.getConnection();
			conn.setAutoCommit(false);
			stmtLines = conn.prepareStatement(deleteLinesSql);
			stmtLines.setString(1, inoutId); 
			int linesDeleted = stmtLines.executeUpdate();

			stmtShipment = conn.prepareStatement(deleteShipmentSql);
			stmtShipment.setString(1, inoutId); 
			int shipmentDeleted = stmtShipment.executeUpdate();

			if (shipmentDeleted > 0) {
				conn.commit();
				return true;
			} else {
				conn.rollback();
				return false;
			}

		} catch (Exception e) {
			try {
				if (conn != null) {
					conn.rollback();
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			e.printStackTrace();
			return false;

		} finally {
			try {
				if (stmtLines != null)
					stmtLines.close();
				if (stmtShipment != null)
					stmtShipment.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public Vector<String> getAllCustomerIds() throws SQLException {
		Vector<String> customerIds = new Vector<>();
		String sql = "SELECT customer_id FROM customer"; 
		try (Connection connection = DBConnection.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql);
				ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				customerIds.add(resultSet.getString("customer_id")); 
			}
		}
		return customerIds;
	}

}






	// Method to get the total count of shipments
	/*
	 * public int getShipmentCount() throws SQLException { String query =
	 * "SELECT COUNT(*) FROM m_inout"; try (Connection connection =
	 * DriverManager.getConnection(url, username, password); PreparedStatement pst =
	 * connection.prepareStatement(query); ResultSet rs = pst.executeQuery()) { if
	 * (rs.next()) { return rs.getInt(1); } } return 0; }
	 */

