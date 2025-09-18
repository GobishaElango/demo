package com.qualiantech.goodsshipment;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

@WebServlet("/GoodsShipping")
public class ShipmentServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private ShipmentInoutDAO shipmentInoutDAO;

	@Override
	public void init() throws ServletException {
		shipmentInoutDAO = new ShipmentInoutDAO();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			Vector<ShipmentInoutVO> shipments = shipmentInoutDAO.getShipmentById(); 
			request.setAttribute("shipments", shipments);
			String documentNo = generateNewDocumentNo();
			request.setAttribute("documentNo", documentNo);
			Vector<String> customerIds = shipmentInoutDAO.getAllCustomerIds();
			request.setAttribute("customerIds", customerIds);
			PrintWriter out = response.getWriter();
			String documentnos = "";
			out.println("<html lang=\"en\">\r\n" + "<head>\r\n" + "    <meta charset=\"UTF-8\">\r\n"
					+ "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n"
					+ "    <title>Goods Shipment</title>\r\n" + "    <link rel=\"stylesheet\" href=\"style.css\">\r\n"
					+ "    <link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css\">\r\n"
					+ "</head>\r\n" + "<body>\r\n" + "    <div class=\"main-title\">Goods Shipment</div>\r\n" + "\r\n"
					+ "    <div class=\"header-actions\">\r\n" + "        <div class=\"search-filter-options\">\r\n"
					+ "            <div class=\"search-container\">\r\n"
					+ "                <i class=\"fas fa-search search-icon\"></i>\r\n"
					+ "                <input type=\"text\" placeholder=\"Search...\" id=\"shipmentSearchInput\">\r\n"
					+ "            </div>\r\n" + "            <div class=\"filter-dropdown\">\r\n"
					+ "                <select class=\"dropdown\" id=\"shipmentFilterDropdown\">\r\n"
					+ "                    <option value=\"\">Filter by...</option>\r\n"
					+ "                    <option value=\"documentNo\">Document No</option>\r\n"
					+ "                    <option value=\"MovementDate\">Movement Date</option>\r\n"
					+ "                    <option value=\"customerId\">Customer Id</option>\r\n"
					+ "                </select>\r\n" + "            </div>\r\n" + "        </div>\r\n"
					+ "        <div class=\"action-buttons-add-delete\">\r\n"
					+ "            <button class=\"header-btn\" id=\"addShipmentButton\">Add</button>\r\n"
					+ "            <button class=\"header-btn\" id=\"deleteShipmentButton\">Delete</button>\r\n"
					+ "        </div>\r\n" + "    </div>\r\n" + "	 <p id=\"message\" class=\"message\"></p>\r\n"
					+ "    <!-- Shipment Records Table for Shipments -->\r\n" + "    <table id=\"shipmentRecords\">\r\n"
					+ "        <thead>\r\n" + "            <tr>\r\n"
					+ "                <th><input type=\"checkbox\" id=\"selectAllCheckbox\" onclick=\"toggleSelectAll(this)\"></th> \r\n"
					+ "                <th>Document No</th>\r\n" + "                <th>Date</th>\r\n"
					+ "                <th>Customer ID</th>\r\n" + "                <th>Created By</th>\r\n"
					+ "                <th>Updated By</th>\r\n" + "                <th>Actions</th>\r\n"
					+ "            </tr>\r\n" + "			</thead>\r\n" + "			<tbody>\r\n");
			for (ShipmentInoutVO shipment : shipments) {
				String linesString = "";
				if (documentnos != "") {
					documentnos += "," + shipment.getDocumentNo();
				} else {
					documentnos += shipment.getDocumentNo();
				}
				for (ShipmentLineVO line : shipment.getLineItems()) {
					linesString += line.getProductId() + "?" + line.getQuantity() + "|";
				}
				out.println("<tr id=\"row-" + shipment.getInoutId() + "\">"
						+ "<td style='vertical-align:middle; text-align:center;width:21px;'>"
						+ "<input type='checkbox' class=\"shipmentRowcheckbox\" onclick=\"setSelectAllCheckBox()\" value='"
						+ shipment.getInoutId() + "'>" + "</td>" + "<td>" + shipment.getDocumentNo() + "</td>" + "<td>"
						+ shipment.getDocumentDate() + "</td>" + "<td>" + shipment.getCustomerId() + "</td>" + "<td>"
						+ shipment.getCreatedby() + "</td>" + "<td>" + shipment.getUpdatedby() + "</td>" + "<td>"
						+ "<div style='display: flex; justify-content: center; gap: 8px; align-items: center;'>"
						+ "<input id=\"ln-" + shipment.getInoutId() + "\" type=\"hidden\" value=\"" + linesString
						+ "\">" + "<button id=\"edit-" + shipment.getInoutId()
						+ "\" onclick=\"editrow(this.parentNode.parentNode.parentNode)\" style='margin: 0;'>\n"
						+ "<i class=\"fas fa-edit\">\n" + "</i>\n" + "</button>\n"
						+ "<button onclick='viewShipment(this.parentNode.parentNode.parentNode)' style='margin: 0;'>"
						+ "<i class=\"fas fa-eye\"></i>" + "</button>" + "<button onclick='deleteParticularRows(\""
						+ shipment.getInoutId() + "\", \"" + shipment.getDocumentNo() + "\")' style='margin: 0;'>"
						+ "<i class=\"fas fa-trash\"></i>" + "</button>" + "</div>" + "</td>" + "</tr>");
			}
			out.println("</tbody>\r\n" + "    </table>\r\n" + "	   <input type=\"hidden\" id=\"documentnos\" value=\""
					+ documentnos + "\">" + "\r\n" + "   <!-- Add Shipment Form Modal -->\r\n"
					+ "    <div id=\"addShipmentFormModal\" class=\"modal\">\r\n"
					+ "        <div class=\"modal-content\">\r\n"
					+ "            <span class=\"close\">&times;</span>\r\n"
					+ "            <form id=\"addShipmentForm\">\r\n" + "                <fieldset>\r\n"
					+ "                    <legend>Enter Shipment Details</legend>\r\n"
					+ "                    <label for=\"document_no\">Document No:<span class=\"required\">*</span></label>\r\n"
					+ "                    <input type=\"text\" id=\"document_no\" name=\"document_no\" value=\""
					+ request.getAttribute("documentNo") + "\" disabled><br><br>\r\n" + "\r\n"
					+ "                    <label for=\"document_date\">Document Date:<span class=\"required\">*</span></label>\r\n"
					+ "                    <input type=\"date\" id=\"document_date\" name=\"document_date\" required><br><br>\r\n"
					+ "\r\n"
					+ "                    <label for=\"customer_id\">Customer ID:<span class=\"required\">*</span></label>\r\n"
					+ "                    <select id=\"customer_id\" name=\"customer_id\" required>\");\r\n"
					+ "        			  		 <option value=\"\">Select Customer</option>\");\r\n");
			for (String customerId : customerIds) {
				out.println("                      <option value=\"" + customerId + "\">" + customerId + "</option>");
			}
			out.println("                    </select><br><br>" + "                    <fieldset>\r\n"
					+ "                        <legend>Enter Shipment Line Details</legend>\r\n"
					+ "                        <label for=\"product_id\">Product ID:</label>\r\n"
					+ "                        <input type=\"text\" id=\"product_id\" name=\"product_id\"><br><br>\r\n"
					+ "    \r\n" + "                        <label for=\"quantity\">Quantity:</label>\r\n"
					+ "                        <input type=\"number\" id=\"quantity\" name=\"quantity\"><br><br>\r\n"
					+ "\r\n" + "                        <div class=\"line-action-button\">\r\n"
					+ "                            <button type=\"button\" id=\"addLineButton\">Add Line</button>\r\n"
					+ "                            <button type=\"button\" id=\"cancelLineButton\">Cancel</button>\r\n"
					+ "                        </div>\r\n" + "                        \r\n"
					+ "                        <!-- Shipment Line Table -->\r\n"
					+ "                        <table id=\"lineTable\">\r\n" + "                            <thead>\r\n"
					+ "                                <tr>\r\n"
					+ "                                    <th>Product ID</th>\r\n"
					+ "                                    <th>Quantity</th>\r\n"
					+ "                                    <th>Actions</th> \r\n"
					+ "                                </tr>\r\n" + "                            </thead>\r\n"
					+ "                            <tbody>\r\n"
					+ "                                <!-- Dynamically filled with shipment line data -->\r\n"
					+ "                            </tbody>\r\n" + "                        </table>\r\n"
					+ "                    </fieldset>\r\n" + "                    <div class=\"form-action-button\">\r\n"
					+ "						   <input type=\"hidden\" id=\"action\" name=\"action\">"
					+ "                        <button type=\"reset\" id=\"resetFormButton\">Reset</button>\r\n"
					+ "                        <button type=\"submit\" id=\"submitShipment\">Submit</button>\r\n"
					+ "                    </div>\r\n" + "                </fieldset>\r\n" + "            </form>\r\n"
					+ "        </div>\r\n" + "    </div>" + "   <!-- View Shipment Modal -->\r\n"
					+ "    <div id=\"viewShipmentModal\" class=\"modal\">\r\n"
					+ "        <div class=\"modal-content\">\r\n"
					+ "            <span class=\"close-view\">&times;</span>\r\n"
					+ "            <h2>View Shipment</h2>\r\n" + "            <form>\r\n"
					+ "                <label for=\"viewDocumentNo\">Document No:</label>\r\n"
					+ "                <input type=\"text\" id=\"viewDocumentNo\" disabled>\r\n" + "\r\n"
					+ "                <label for=\"viewDocumentDate\">Document Date:</label>\r\n"
					+ "                <input type=\"text\" id=\"viewDocumentDate\" disabled>\r\n" + "\r\n"
					+ "                <label for=\"viewCustomerId\">Customer ID:</label>\r\n"
					+ "                <input type=\"text\" id=\"viewCustomerId\" disabled>\r\n" + "\r\n"
					+ "                <h3>Shipment Lines</h3>\r\n" + "                <table id=\"viewLineTable\">\r\n"
					+ "                    <thead>\r\n" + "                        <tr>\r\n"
					+ "                            <th>Product ID</th>\r\n"
					+ "                            <th>Quantity</th>\r\n" + "                        </tr>\r\n"
					+ "                    </thead>\r\n" + "                    \r\n"
					+ "                    <tbody>\r\n" + "                    </tbody>\r\n"
					+ "                </table>\r\n" + "            </form>\r\n" + "        </div>\r\n"
					+ "    </div>\r\n" + "    \r\n" + "    \r\n" + " <!-- Delete Confirmation Modal -->\r\n"
					+ "<div id=\"deleteConfirmationModal\" class=\"modal\">\r\n"
					+ "    <div class=\"modal-content\">\r\n"
					+ "        <span class=\"close\" onclick=\"closeDeleteConfirmation()\">&times;</span>\r\n"
					+ "        <h2>Delete Confirmation</h2>\r\n"
					+ "        <p id=\"deleteConfirmationMessage\"></p>\r\n"
					+ "        <button onclick=\"confirmDelete()\">Confirm</button>\r\n"
					+ "        <button onclick=\"closeDeleteConfirmation()\">Cancel</button>\r\n" + "    </div>\r\n"
					+ "</div>\r\n" + "\r\n" + "<!-- Notification Modal -->\r\n"
					+ "<div id=\"notificationModal\" class=\"modal\">\r\n" + "    <div class=\"modal-content\">\r\n"
					+ "        <span class=\"close-notification\" onclick=\"closeDeleteConfirmation()\">&times;</span>\r\n"
					+ "        <h2>Notification</h2>\r\n" + "        <p class=\"notification-message\"></p>\r\n"
					+ "    </div>\r\n" + "</div>\r\n" + "\r\n" + "<script src=\"script.js\">\r\n" + "</script>\r\n"
					+ "</body>\r\n" + "</html>\r\n");
		} catch (SQLException e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error");
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");

		try {
			if ("add".equals(action)) {
				ShipmentInoutVO shipment = new ShipmentInoutVO();
				shipment.setInoutId(request.getParameter("inout_id"));
				;
				shipment.setDocumentNo(request.getParameter("documentNo"));
				shipment.setDocumentDate(Date.valueOf(request.getParameter("documentDate")));
				shipment.setCustomerId(request.getParameter("customerId"));
				shipment.setCreatedby(request.getParameter("createdBy"));
				shipment.setUpdatedby(request.getParameter("updatedBy"));

				Vector<ShipmentLineVO> lineItems = new Vector<ShipmentLineVO>();
				String shipmentLinesRequest = request.getParameter("shipmentLinesRequest");
				String[] shipmentLinesArray = shipmentLinesRequest.split("\\|");
				for (String shipmentLine : shipmentLinesArray) {
					String[] shipmentLineFields = shipmentLine.split("\\?");
					ShipmentLineVO lineItem = new ShipmentLineVO();
					lineItem.setProductId(shipmentLineFields[0]);
					lineItem.setQuantity(Integer.parseInt(shipmentLineFields[1]));
					lineItems.add(lineItem);
				}
				shipment.setLineItems(lineItems);
				shipmentInoutDAO.addShipment(shipment);
				response.sendRedirect("GoodsShipping");
			} else if ("edit".equals(action)) {
				ShipmentInoutVO shipment = new ShipmentInoutVO();
				shipment.setInoutId(request.getParameter("inout_id"));
				shipment.setDocumentNo(request.getParameter("documentNo"));
				shipment.setDocumentDate(Date.valueOf(request.getParameter("documentDate")));
				shipment.setCustomerId(request.getParameter("customerId"));
				shipment.setUpdatedby(request.getParameter("updatedBy"));

				Vector<ShipmentLineVO> lineItems = new Vector<ShipmentLineVO>();
				String shipmentLinesRequest = request.getParameter("shipmentLinesRequest");
				String[] shipmentLinesArray = shipmentLinesRequest.split("\\|");
				for (String shipmentLine : shipmentLinesArray) {
					String[] shipmentLineFields = shipmentLine.split("\\?");
					ShipmentLineVO lineItem = new ShipmentLineVO();
					// lineItem.set;
					lineItem.setProductId(shipmentLineFields[0]);
					lineItem.setQuantity(Integer.parseInt(shipmentLineFields[1]));
					lineItems.add(lineItem);
				}
				shipment.setLineItems(lineItems);
				shipmentInoutDAO.updateShipment(shipment);
				response.sendRedirect("GoodsShipping");
			} else if ("delete".equals(action)) {
				String inoutId = request.getParameter("inoutId");
				boolean success = shipmentInoutDAO.deleteShipment(inoutId);
				response.setContentType("application/json");
				response.getWriter().write("{\"status\":\"" + (success ? "success" : "failure") + "\"}");

			} else if ("deleteSelected".equals(action)) {
				String[] inoutIds = request.getParameter("selected_inout_ids").split(",");
				boolean allDeleted = true;
				for (String id : inoutIds) {
					if (!shipmentInoutDAO.deleteShipment(id)) {
						allDeleted = false;
					}
				}
				response.setContentType("application/json");
				response.getWriter().write("{\"status\":\"" + (allDeleted ? "success" : "partial_failure") + "\"}");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error");
		}
	}

	private String generateNewDocumentNo() {
		String generatedDocNo = "GS001";
		try (Connection conn = DBConnection.getConnection();
				PreparedStatement stmt = conn
						.prepareStatement("SELECT document_no FROM m_inout ORDER BY document_no DESC LIMIT 1")) {

			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				String lastGeneratedDocNo = rs.getString("document_no");
				int num = Integer.parseInt(lastGeneratedDocNo.substring(2)) + 1;
				generatedDocNo = String.format("GS%03d", num);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return generatedDocNo;
	}

}
