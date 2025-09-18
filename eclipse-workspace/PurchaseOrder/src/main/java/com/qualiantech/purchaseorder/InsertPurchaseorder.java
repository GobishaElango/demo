package com.qualiantech.purchaseorder;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.*;

/**
 * Servlet implementation class InsertServlet
 */
@WebServlet("/InsertPurchaseorder")
public class InsertPurchaseorder extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();
		String jsonString = request.getParameter("purchaseorder");
		try {
			JSONObject purchaseorderJSONObject = new JSONObject(jsonString);
			PurchaseorderVO purchaseorder = new PurchaseorderVO();
			purchaseorder.setDocumentNo(purchaseorderJSONObject.getString("orderId"));
			purchaseorder.setOrderDate(Date.valueOf(purchaseorderJSONObject.getString("orderDate")));
			purchaseorder.setDeliveryDate(Date.valueOf(purchaseorderJSONObject.getString("deliveryDate")));
			purchaseorder.setVendorName(purchaseorderJSONObject.getString("vendorNameDetails"));
			purchaseorder.setVendorAddress(purchaseorderJSONObject.getString("vendorAddressDetails"));
			purchaseorder.setDeliveryAddress(purchaseorderJSONObject.getString("deliveryAddress"));
			String totalAmountStr = purchaseorderJSONObject.getString("totalAmount").replaceAll("[^0-9.]", "");
			purchaseorder.setTotalAmount(Double.parseDouble(totalAmountStr));
			JSONArray lineItemsJSONArray = purchaseorderJSONObject.getJSONArray("lineItems");
			ArrayList<PurchaseorderlineVO> lineItems = new ArrayList<>();
			for (int i = 0; i < lineItemsJSONArray.length(); i++) {
				JSONObject lineItemJSONObject = lineItemsJSONArray.getJSONObject(i);
				PurchaseorderlineVO lineItem = new PurchaseorderlineVO();
				lineItem.setProductName(lineItemJSONObject.getString("product"));
				String priceStr = lineItemJSONObject.getString("price").replaceAll("[^0-9.]", "");
				lineItem.setPrice(Double.parseDouble(priceStr));
				lineItem.setQuantity(lineItemJSONObject.getInt("quantity"));
				lineItem.setUom(lineItemJSONObject.getString("uom"));
				String amountStr = lineItemJSONObject.getString("amount").replaceAll("[^0-9.]", "");
				lineItem.setAmount(Double.parseDouble(amountStr));
				lineItems.add(lineItem);
			}
			purchaseorder.setLineItems(lineItems);
			PurchaseorderDAO purchaseorderDAO = new PurchaseorderDAO();
			int insertRowCount = purchaseorderDAO.insertPurchaseorder(purchaseorder);
			if (insertRowCount > 0) {
				PurchaseorderDAO.setStatusMessage(null);
				PurchaseorderDAO.setStatusMessage("Purchase Order Added Successfully!");
				response.getWriter().print("REDIRECT_TO_PAGE_1");
			} else {
				response.getWriter().print("FAILED");
			}
		} catch (JSONException e) {
			out.print(0);
			e.printStackTrace();
		}
		// response.setCharacterEncoding("UTF-8");
	}
}
