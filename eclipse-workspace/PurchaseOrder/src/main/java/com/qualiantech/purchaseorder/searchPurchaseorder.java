package com.qualiantech.purchaseorder;

import java.io.*;
import java.sql.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import org.json.JSONArray;
import org.json.JSONObject;

@WebServlet("/searchPurchaseorder")
public class searchPurchaseorder extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		response.setContentType("application/json");
		PrintWriter out = response.getWriter();

		try {
			String filterType = request.getParameter("filterType");
			String filterValue = request.getParameter("filterValue");
			String pageStr = request.getParameter("page");
			String pageSizeStr = request.getParameter("pageSize");

			int page = (pageStr != null) ? Integer.parseInt(pageStr) : 1;
			int pageSize = (pageSizeStr != null) ? Integer.parseInt(pageSizeStr) : 5; // default 5 records
			int offset = (page - 1) * pageSize;

			conn = DBConnection.getConnection();

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT po.order_id, po.document_no, po.order_date, po.delivery_date, ");
			sql.append("v.name, po.vendor_address, po.delivery_address, po.total_amount ");
			sql.append("FROM purchaseorder po ");
			sql.append("JOIN vendor v ON po.vendor_id = v.vendor_id ");

			if (filterType != null && !filterType.isEmpty() && filterValue != null && !filterValue.isEmpty()) {
				sql.append("WHERE ");
				switch (filterType) {
				case "documentNo":
					sql.append("po.document_no like ? ");
					break;
				case "orderDate":
					sql.append("CAST(po.order_date AS TEXT) like ? ");
					break;
				case "deliveryDate":
					sql.append("CAST(po.delivery_date AS TEXT) like ? ");
					break;
				case "vendorName":
					sql.append("v.name like ? ");
					break;
				case "vendorAddress":
					sql.append("po.vendor_address like ? ");
					break;
				case "deliveryAddress":
					sql.append("po.delivery_address like ? ");
					break;
				default:
					sql.append("po.document_no like ? ");
					break;
				}
			}

			sql.append("ORDER BY po.order_date DESC ");

			if (filterValue == null || filterValue.isEmpty()) {
				sql.append("LIMIT ? OFFSET ?");
			}
			ps = conn.prepareStatement(sql.toString());

			int paramIndex = 1;
			if (filterType != null && !filterType.isEmpty() && filterValue != null && !filterValue.isEmpty()) {
				ps.setString(paramIndex++, "%" + filterValue + "%");
			}
			if (filterValue == null || filterValue.isEmpty()) {
				ps.setInt(paramIndex++, pageSize);
				ps.setInt(paramIndex, offset);
			}
			rs = ps.executeQuery();

			JSONArray ordersArray = new JSONArray();

			while (rs.next()) {
				JSONObject orderObj = new JSONObject();
				String documentNo = rs.getString("document_no");

				orderObj.put("orderId", rs.getString("order_id"));
				orderObj.put("documentNo", documentNo);
				orderObj.put("orderDate", rs.getString("order_date"));
				orderObj.put("deliveryDate", rs.getString("delivery_date"));
				orderObj.put("vendorName", rs.getString("name"));
				orderObj.put("vendorAddress", rs.getString("vendor_address"));
				orderObj.put("deliveryAddress", rs.getString("delivery_address"));
				orderObj.put("totalAmount", rs.getString("total_amount"));

				List<JSONObject> lineItems = getLineItemsForOrder(conn, documentNo);
				orderObj.put("lineItems", lineItems);

				ordersArray.put(orderObj);
			}

			out.print(ordersArray.toString());
			out.flush();

		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			out.print("{\"error\":\"Something went wrong.\"}");
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
			}
			try {
				if (ps != null)
					ps.close();
			} catch (SQLException e) {
			}
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
			}
		}
	}

	private List<JSONObject> getLineItemsForOrder(Connection conn, String documentNo) throws SQLException {
		List<JSONObject> lineItems = new ArrayList<>();

		String lineItemQuery = "SELECT p.name, pol.price, pol.quantity, p.uom, pol.amount "
				+ "FROM purchaseorderline pol " + "INNER JOIN product p ON pol.product_id = p.product_id "
				+ "WHERE pol.order_id = (SELECT order_id FROM purchaseorder WHERE document_no = ?)";

		try (PreparedStatement pstmt = conn.prepareStatement(lineItemQuery)) {
			pstmt.setString(1, documentNo);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				JSONObject lineItem = new JSONObject();
				lineItem.put("product", rs.getString("name"));
				lineItem.put("price", rs.getDouble("price"));
				lineItem.put("quantity", rs.getInt("quantity"));
				lineItem.put("uom", rs.getString("uom"));
				lineItem.put("amount", rs.getDouble("amount"));
				lineItems.add(lineItem);
			}
			rs.close();
		}

		return lineItems;
	}
}
