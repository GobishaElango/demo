package com.qualiantech.purchaseorder;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.*;

import com.qualiantech.purchaseorder.*;

/**
 * Servlet implementation class ViewServlet
 */
@WebServlet("/ViewPurchaseorder")
public class ViewPurchaseorder extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static int limitPerPage = 5;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String page = request.getParameter("page");

		// String confirmMessage = request.getParameter("confirmMsg")!= null?
		// request.getParameter("confirmMsg"):"";
		// view all purchaseorder
		if (page == null || page.trim().isEmpty()) {
			page = "1";
		}

		response.setContentType("text/html; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		int currentPageIndex = Integer.parseInt(page);
		int offset = 0;
		if (currentPageIndex > 1) {
			offset = (currentPageIndex - 1) * limitPerPage;
		}
		PrintWriter out = response.getWriter();
		PurchaseorderDAO purchaseorderDAO = new PurchaseorderDAO();
		int totalPage = (int) Math.ceil((double) purchaseorderDAO.getPurchaseorderCount() / limitPerPage);
		int prevPageIndex = currentPageIndex > 1 ? currentPageIndex - 1 : 1;
		int nextPageIndex = currentPageIndex < totalPage ? currentPageIndex + 1 : totalPage;
		ArrayList<PurchaseorderVO> purchaseorderList = purchaseorderDAO.getAllPurchaseorders(limitPerPage, offset);
		out.print("<html><head><title>Purchase Order List</title>");
		out.print(
				"<link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css'>");
		out.println("<link rel='icon' type='image/x-icon' href='favicon.ico'>");
		out.print("<link rel='stylesheet' href='purchaseorder.css'></head>");
		out.print("<body>");
		out.print("<h2 style=\"text-align: center; margin-bottom: -2px;\">Purchase Orders</h2>");
		// lineItemTable
		out.print("<div id=\"linedetails\">");
		out.print("<div id=\"lineItemDetails\">");
		out.print("<h2 style=\"text-align: center;\">Purchase Order Details</h2>");
		out.print("<button  type=\"button\" class=\"close-btn\" onclick=\"hideLineItem()\">&times;</button>");
		out.print("<div style=\"display: flex;\">\r\n" + "       <div class=\"left\">\r\n"
				+ "            <label for=\"linevendorNameDetails\">Vendor Info:</label>\r\n"
				+ "             <p id=\"linevendorNameDetails\">Company Name </p>"
				+ "             <label for=\"livenendorAddressDetails\">Vendor Address Info:</label>"
				+ "             <p id=\"linevendorAddressDetails\">Vendor Address</p>"
				+ "             <label for=\"linedeliveryAddress\">Delivery Address Info:</label>"
				+ "             <p id=\"linedeliveryAddress\">Address location</p>"
				+ "             <div class=\"total-amount\">"
				+ "            		<label for=\"linetotalAmount\">Total:</label>"
				+ "					<span id=\"linetotalAmount\">total amount</span>" + "			   </div>"
				+ "			</div>" + "			<div class=\"right\">" + "				<div class=\"inline\">"
				+ "           		<label for=\"lineorderId\">Document No:</label>"
				+ "					<span id=\"lineorderId\"></span>" + "				</div>"
				+ "				<div class=\"inline\">"
				+ "					<label for=\"lineorderDate\" style=\"margin-left:0px;\">Order Date		:</label>"
				+ "					<span id=\"lineorderDate\">dd/mm/yyyy</span>" + "				</div>"
				+ "				<div class=\"inline\">"
				+ " 					<label for=\"linedeliveryDate\">Delivery Date:</label>"
				+ " 					<span id=\"linedeliveryDate\">dd/mm/yyyy</span>" + "				</div>"
				+ "			</div>" + "		</div>");
		out.print("<table id=\"LineTable\" class=\"table table-bordered\">");
		out.print("<thead><tr>");
		out.print("<th style=\"width: 250px;\">Product</th>" + "<th style=\"width: 100px;\">Price</th>"
				+ "<th style=\"width: 100px;\">Quantity</th>" + "<th>UOM</th>" + "<th>Amount</th>");
		out.print("</tr></thead>");
		out.print("<tbody id=\"LineItemBody\"></tbody>");
		out.print("</table></div></div>");

		// mainTable
		// out.print("<div class=\"title\">Purchase Order</div>");
		out.print("<div id=\"mainTable\" class=\"action-buttons-add-delete\" style=\"display:flex;\">"
				+ "<div style=\"display:flex; gap:10px; align-items:center; justify-content: space-between; margin-bottom:10px;\">\r\n"
				+ "    <div style=\"position: relative; width: 100%;\">\r\n"
				+ "        <input type=\"search\" id=\"searchPurchaseOrder\" onkeyup=\"filterTable(event)\" spellcheck=\"false\" autocomplete=\"off\"\r\n"
				+ "            placeholder=\"Search...\"\r\n"
				+ "            style=\"width:100%; padding-left: 35px; height: 32px; border-radius: 5px; border: 1px solid #ccc;\">\r\n"
				+ "        <i class=\"fa fa-search\"\r\n"
				+ "            style=\"cursor: pointer; position: absolute; left: 5px; top: 50%; transform: translateY(-50%); color: #888;\"></i>\r\n"
				+ "    </div>\r\n" + "</div>\r\n" + "		<select id=\"filterDropdown\""
				+ "style=\"height: 32px; border-radius: 5px; border: 1px solid #ccc; margin-left:36px; width:12%; margin-right:85px;\">\r\n"
				+ "				<option value=\"\">Filter by...</option>\r\n"
				// + " <option value=\"documentNo\">Document No</option>\r\n"
				+ "				<option value=\"orderDate\">Order Date</option>\r\n"
				+ "				<option value=\"deliveryDate\">Delivery Date</option>\r\n"
				+ "				<option value=\"vendorName\">Vendor Name</option>\r\n"
				+ "				<option value=\"vendorAddress\">Vendor Address</option>\r\n"
				+ "				<option value=\"deliveryAddress\">Delivery Address</option>\r\n"
				+ "			</select>"
				+ "			<button class=\"header-actions-btn\" id=\"addPurchaseOrderButton\"\r\n"
				+ "				style=\"margin-left: 600px;height:32px;\"onclick=\"displayPurchaseorderForm()\">Add</button>\r\n"
				+ "			<button class=\"header-actions-btn\"  style=\"height:32px;\" id=\"deletePurchaseOrderButton\" onclick=\"deleteSelectedPurchaseOrder()\">Delete</button>\r\n"
				+ "		</div>");

		String statusMsg = PurchaseorderDAO.getStatusMessage();
		String color = "green";
		if (statusMsg != null && statusMsg.toLowerCase().contains("delete")) {
			color = "red";
		}
		out.print("<span id='confirmMsg' style='color: " + color
				+ "; visibility: hidden; display: block; min-height: 20px;'>");
		if (statusMsg != null && !statusMsg.trim().isEmpty()) {
			out.print(statusMsg);
		}
		out.print("</span>");
		out.print("<!-- Purchase order Records table -->\r\n"
				+ "		<table id=\"purchaseorderRecords\" style=\"margin-top: 30px; border-radius:4px;\">\r\n"
				+ "			<thead>\r\n" + "				<tr>\r\n"
				+ "					<th><input type=\"checkbox\" id=\"selectAllCheckbox\" class=\"check\" onclick=\"toggleSelectAll(this)\"></th>\r\n"
				+ "					<th>Document No</th>\r\n" + "					<th>Order Date</th>\r\n"
				+ "					<th>Delivery Date</th>\r\n" + "					<th>Vendor Name</th>\r\n"
				+ "					<th>Vendor Address</th>\r\n" + "					<th>Delivery Address</th>\r\n"
				+ "					<th>Total Amount</th>\r\n" + "					<th>Actions</th>\r\n"
				+ "				</tr>\r\n" + "			</thead>\r\n" + "			<tbody id=\"purchaseorderBody\">");
		for (PurchaseorderVO purchaseorder : purchaseorderList) {
			out.print("<tr><td><input type=\"checkbox\" class=\"check\" onclick=\"enableDeleteBtn()\"></td>");
			out.print("<td>" + purchaseorder.getDocumentNo() + "</td>");
			out.print("<td>" + PurchaseorderDAO.formatDate(purchaseorder.getOrderDate().toString()) + "</td>");
			out.print("<td>" + PurchaseorderDAO.formatDate(purchaseorder.getDeliveryDate().toString()) + "</td>");
			out.print("<td>" + purchaseorder.getVendorName() + "</td>");
			out.print("<td>" + purchaseorder.getVendorAddress() + "</td>");
			out.print("<td>" + purchaseorder.getDeliveryAddress() + "</td>");
			out.print("<td>" + purchaseorder.getTotalAmount() + "</td>");
			ArrayList<PurchaseorderlineVO> lineItems = purchaseorder.getLineItems();
			JSONArray lineItemsJSONArray = new JSONArray();
			try {
				for (PurchaseorderlineVO lineItem : lineItems) {
					JSONObject lineItemJSONObject = new JSONObject();
					lineItemJSONObject.put("product", lineItem.getProductName());
					lineItemJSONObject.put("price", lineItem.getPrice());
					lineItemJSONObject.put("quantity", lineItem.getQuantity());
					lineItemJSONObject.put("uom", lineItem.getUom());
					lineItemJSONObject.put("amount", lineItem.getAmount());
					lineItemsJSONArray.put(lineItemJSONObject);
				}
			} catch (JSONException e) {
				lineItemsJSONArray = new JSONArray();
			}
			out.print("<td><i class=\"fa fa-eye\" onclick='showLineItem(this, " + lineItemsJSONArray
					+ ")'></i><i class=\"fa fa-edit\" onclick='updatePurchaseorder(this," + lineItemsJSONArray
					+ ")'></i><i class=\"fa fa-trash\" onclick=\"deletePurchaseorder(this)\"></i></td></tr>");
		}
		out.print("</tbody></table></div>");
		// Pagination
		out.print(
				"<div id='pagination' style='display: flex; justify-content: space-between; align-items: center; margin-top: 20px;'>");
		out.print("<div style='flex: 1; text-align: left;'>");
		out.print("<form action='ViewPurchaseorder' method='get' style='display: inline;'>");
		out.print("<input type='hidden' name='page' value='" + prevPageIndex + "'>");
		out.print("<button type='submit' ");
		if (currentPageIndex == 1) {
			out.print("style='display: none;'");
		}
		out.print(">&#8249; Previous</button>");
		out.print("</form>");
		out.print("</div>");

		// CENTER: Page number buttons
		out.print("<div style='flex: 5; text-align: center;'>");
		for (int index = 1; index <= totalPage; index++) {
			if (index == currentPageIndex) {
				out.print("<button style='margin: 0 2px; font-weight:bold;' disabled>" + index + "</button>");
			} else {
				out.print("<form action='ViewPurchaseorder' method='get' style='display:inline;'>");
				out.print("<input type='hidden' name='page' value='" + index + "'>");
				out.print("<button type='submit' style='margin: 0 2px;'>" + index + "</button>");
				out.print("</form>");
			}
		}
		out.print("</div>");

		// RIGHT: Next button
		out.print("<div style='flex: 1; text-align: right;'>");
		out.print("<form action='ViewPurchaseorder' method='get' style='display: inline;'>");
		out.print("<input type='hidden' name='page' value='" + nextPageIndex + "'>");
		out.print("<button type='submit' ");
		if (currentPageIndex == totalPage) {
			out.print("style='display: none;'");
		}
		out.print(">Next &#8250;</button>");
		out.print("</form>");
		out.print("</div>");

		// Close pagination
		out.print("</div>");
		// form
		out.print("<div id=\"addPurchaseOrderFormModal\">\r\n" + "	<span class=\"close\">&times;</span>\r\n"
				+ "		<form id=\"addPurchaseOrderForm\">\r\n" + "			<h2 id=\"formHeading\"\r\n"
				+ "				style=\"text-align: center; margin-top: -3px; margin-bottom: 35px;\">Add Purchase\r\n"
				+ "				Order</h2>");
		out.print("<div id=\"purchaseorderDetails\">\r\n" + "			<div style=\"margin-top: -12px;\">\r\n"
				+ "				<label for=\"orderId\">Order Id:</label>\r\n"
				+ "				<p id=\"orderId\" style=\"display: inline;\">0</p>\r\n"
				+ "				<label for=\"orderDate\" style= \"width: 128px; margin-left:80px;\">Order Date:<span class=\"required\">*</span></label> \r\n"
				+ "				<input type=\"date\" id=\"orderDate\" name=\"orderDate\" oninput=\"validateOrderDate()\" onkeydown=\"event.preventDefault()\" required >\r\n"
				+ "				<label for=\"DeliveryDate\" style= \"width: 128px; margin-left:80px;\">Delivery Date:<span class=\"required\">*</span></label>\r\n"
				+ "				<input type=\"date\" id=\"deliveryDate\" name=\"deliveryDate\"oninput=\"validateDeliveryDate('deliveryDate')\" onkeydown=\"event.preventDefault()\"  required><br>\r\n"
				+ "			</div>\r\n" + "			</div>\r\n"
				+ "			<div id=\"vendorDetails\" style=\"margin: 8px 0;\">\r\n"
				+ "				<label for=\"vendorNameDetails\">Vendor Name:<span class=\"required\"style= \"height:36px;width:208.552px;margin-right:12px;\">*</span ></label>\r\n"
				+ "				<select id=\"vendorNameDetails\" name=\"vendorNameDetails\" oninput=\"removeErrorClassMsg('vendorNameDetails')\" required style=\"margin-right:117px;\">\r\n"
				+ "					<option value=\"Select\">Select</option>\r\n"
				+ "					<option value=\"OnO Goods Pvt. Ltd\">OnO Goods Pvt. Ltd</option>\r\n"
				+ "					<option value=\"Amul Dairy Products\">Amul Dairy Products</option>\r\n"
				+ "					<option value=\"Ninjacart\">Ninjacart</option>\r\n"
				+ "					<option value=\"Aachi Spices Pvt. Ltd\">Aachi Spices Pvt.Ltd</option>\r\n"
				+ "					<option value=\"Nandhi Goods Pvt. Ltd\">Nandhi Goods Pvt.Ltd</option>\r\n"
				+ "				</select>\r\n" + "			</div>\r\n" + "				<br>\r\n"
				+ "			<div id=\"addressDetails\">\r\n"
				+ "				<label for=\"vendorAddressDetails\" style= \"width:300px;margin-right:9px;\">Vendor Address:<span class=\"required\">*</span></label>\r\n"
				+ "				<textarea id=\"vendorAddressDetails\" name=\"vendorAddressDetails\"  rows=\"2\"  cols=\"20\" placeholder=\"Enter Vendor Address\"  style=\"resize:none;\" oninput=\"removeErrorClassMsg('vendorAddressDetails')\" required></textarea>\r\n"
				+ "				<label for=\"deliveryAddress\"style=\"width:300px;margin-left:0px; margin-right:0px;margin-top:0px; margin-bottom:0px;\">Delivery Address:<span class=\"required\">*</span></label>\r\n"
				+ "				<textarea id=\"deliveryAddress\" name=\"deliveryAddress\" rows=\"2\" placeholder=\"Enter Delivery Address\" oninput=\"removeErrorClassMsg('deliveryAddress')\" required></textarea>\r\n"
				+ "				</div>\r\n" + "				<br>\r\n" + "				<div id=\"total\">\r\n"
				+ "				<label for=\"totalAmount\" style=\"margin-right:45px;\">Total Amount:</label>\r\n"
				+ "				<input type=\"text\" id=\"totalAmount\" name=\"totalAmount\" disabled><br>\r\n"
				+ "				</div>");
		out.print("<fieldset id=\"legendError\" style=\"margin-bottom:15px;\">\r\n"
				+ "					<legend  id=\"legend\" >Enter Purchase Order Line Details<sup>*</sup></legend>\r\n"
				+ "					<div class=\"errorDetails\">\r\n"
				+ "						<span id=\"productError\" style=\"width:395px;\"></span>\r\n"
				+ "						<span id=\"priceError\" style=\"width:140px;margin:0 22px;\"></span>\r\n"
				+ "						<span id=\"quantityError\" style=\"width:140px;\"></span>\r\n"
				+ "					</div>\r\n" + "					<div id=\"productDetail\">\r\n"
				+ "						<select id=\"productName\" name=\"productName\" oninput=\"fillProductDetails(this)\" style=\"width:350px;\">\r\n"
				+ "							<option value=\"Select\" id=\"Select\">Select</option>\r\n"
				+ "							<option value=\"Cucumber\" id=\"Cucumber\">Cucumber</option>\r\n"
				+ "							<option value=\"Onion\" id=\"Onion\">Onion</option>\r\n"
				+ "							<option value=\"Aachi Chilli Powder - 100g\"\r\n"
				+ "								id=\"Aachi Chilli Powder - 100g\">Aachi Chilli Powder - 100g</option>\r\n"
				+ "							<option value=\"Gold Winner Refined Sunflower Oil - 1L\"\r\n"
				+ "								id=\"Gold Winner Refined Sunflower Oil - 1L\">Gold Winner Refined Sunflower Oil - 1L</option>\r\n"
				+ "							<option value=\"Tata Urad Dal - 1Kg\" id=\"Tata Urad Dal - 1Kg\">Tata Urad Dal - 1Kg</option>\r\n"
				+ "							<option value=\"Orange\" id=\"Orange\">Orange</option>\r\n"
				+ "							<option value=\"Rice - 1Kg\">Rice - 1Kg</option>\r\n"
				+ "							<option value=\"Bru Instant Coffee - 200g\">Bru Instant Coffee - 200g</option>\r\n"
				+ "							<option value=\"Maggi Noodles-150g\">Maggi Noodles-150g</option>\r\n"
				+ "						</select><br> <br>\r\n"
				+ "						<div style=\"display:flex;\">\r\n"
				+ "							<label for=\"price\" style=\"padding: 5px; margin-left: 5px; width:8px;\">&#8377</label>\r\n"
				+ "							 <input type=\"number\" id=\"price\" placeholder=\"Price\" style=\"width: 145px;\" oninput=\"validatePrice(this)\">\r\n"
				+ "						</div>\r\n" + "						<div>\r\n"
				+ "							<input type=\"number\" id=\"quantity\" placeholder=\"Quantity\" style=\"width: 140px; margin-left: 10px;\" oninput=\"validateQuantity(this)\">\r\n"
				+ "						</div>\r\n" + "						<div id=\"uomDiv\">\r\n"
				+ "							<p id=\"uom\"></p>\r\n" + "						</div>\r\n"
				+ "					</div>\r\n"
				+ "					<div class=\"line-action-button\" style=\"margin-top: 15px;\">\r\n"
				+ "						<button type=\"button\" id=\"addButton\">Add</button>\r\n"
				+ "						<button type=\"button\" id=\"clearButton\">Clear</button>\r\n"
				+ "					</div>");
		out.print("<div class=\"purchaseOrderLineItem\">  ");
		out.print("<table id=\"lineitemTable\">");
		out.print("<thead>\r\n" + "     <tr>\r\n" + "     <th style=\"width: 290px;\">Product</th>\r\n"
				+ "								<th style=\"width: 80px;\">Price</th>\r\n"
				+ "								<th style=\"width: 80px;\">Quantity</th>\r\n"
				+ "								<th>UOM</th>\r\n"
				+ "								<th style=\"width: 100px;\">Amount</th>\r\n"
				+ "								<th style=\"text-align: center; width: 80px;\">Action</th>\r\n"
				+ "							</tr>\r\n" + "						</thead>\r\n"
				+ "						<tbody id=\"lineItemsBody\">\r\n" + "						</tbody>\r\n"
				+ "					</table>\r\n" + "					</div>\r\n" + "					</fieldset>\r\n"
				+ "				\r\n" + "			<div class=\"form-action-button\">\r\n"
				+ "				<button type=\"button\" id=\"submit\" onclick=\"submitForm()\">Submit</button>\r\n"
				+ "				<button type=\"button\" id=\"cancel\" onclick=\"closeForm()\">Cancel</button>\r\n"
				+ "			</div>\r\n" + "	</form>\r\n" + "	</div>\r\n" + "	<div id=\"alertDetails\">\r\n"
				+ "		<div id=\"alertBox\">\r\n"
				+ "			<h4 id=\"alertContent\" style=\"text-align: center; margin-top: 16px;\">Do\r\n"
				+ "				you want to Cancel ?</h4>\r\n" + "			<div id=\"alertButton\">\r\n"
				+ "				<button id=\"yes\">Yes</button>\r\n"
				+ "				<button id=\"no\">No</button>\r\n" + "			</div>\r\n" + " </div>");
		out.print("</div>");
		out.print("<div id=\"confirmDiv\">\r\n" + "    <div id=\"confirmBox\">\r\n"
				+ "        <h3 id=\"confirmContent\" style=\"text-align: center; margin-top: 16px;\">Purchase Order Added Successfully !!</h3>\r\n"
				+ "        <p id=\"lineItemCount\"></p>\r\n"
				+ "        <a id=\"ok\"style=\"display: block; margin: 10px auto; width: 40px; text-align: center; background: green; color: white; padding: 5px 10px; border-radius: 4px; cursor: pointer;\">Ok</a>   \r\n"
				+ "    </div>\r\n" + "</div>");
		out.print("<script src=\"purchaseorder.js\"></script>");
		out.print("</body></html>");

	}

}
