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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.qualiantech.purchaseorder.PurchaseorderDAO;
import com.qualiantech.purchaseorder.PurchaseorderVO;
import com.qualiantech.purchaseorder.PurchaseorderlineVO;

/**
 * Servlet implementation class UpdateServlet
 */
@WebServlet("/UpdatePurchaseorder")
public class UpdatePurchaseorder extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/plain");
		PrintWriter out=response.getWriter();
		String purchaseorderJSONString=request.getParameter("purchaseorder");
		try {
			JSONObject purchaseorderJSONObject = new JSONObject(purchaseorderJSONString);
			PurchaseorderVO purchaseorder = new PurchaseorderVO();
	        purchaseorder.setDocumentNo(purchaseorderJSONObject.getString("orderId"));
	        purchaseorder.setOrderDate(Date.valueOf(purchaseorderJSONObject.getString("orderDate")));
	        purchaseorder.setDeliveryDate(Date.valueOf(purchaseorderJSONObject.getString("deliveryDate")));
	        purchaseorder.setVendorName(purchaseorderJSONObject.getString("vendorName"));
	        purchaseorder.setVendorAddress(purchaseorderJSONObject.getString("vendorAddress"));
	        purchaseorder.setDeliveryAddress(purchaseorderJSONObject.getString("deliveryAddress"));
	        String totalAmountStr = purchaseorderJSONObject.getString("totalAmount").replaceAll("[^0-9.]", "");
	        purchaseorder.setTotalAmount(Double.parseDouble(totalAmountStr));
	        JSONArray lineItemsJSONArray = purchaseorderJSONObject.getJSONArray("lineItems");//array of lineItem object
	        ArrayList<PurchaseorderlineVO> lineItems = new ArrayList<>();
	        for (int i = 0; i < lineItemsJSONArray.length(); i++) {
	            JSONObject lineItemJSONObject = lineItemsJSONArray.getJSONObject(i);//each lineItem object
	            PurchaseorderlineVO lineItem = new PurchaseorderlineVO();
	            lineItem.setProductName(lineItemJSONObject.getString("product"));
	            lineItem.setPrice(lineItemJSONObject.getDouble("price"));
	            lineItem.setQuantity(lineItemJSONObject.getInt("quantity"));
	            lineItem.setUom(lineItemJSONObject.getString("uom"));
	            lineItem.setAmount(lineItemJSONObject.getDouble("amount"));
	            lineItems.add(lineItem);
	        }
	        purchaseorder.setLineItems(lineItems);
	        PurchaseorderDAO purchaseorderDAO=new PurchaseorderDAO();
	        int rowCount=purchaseorderDAO.updatePurchaseorder(purchaseorder);
	       // String lineItemsString = new JSONArray(lineItems).toString();
	        String responseString = "{ \"rowCount\": \""+rowCount+"\", \"lineItems\": "+lineItemsJSONArray.toString()+" }";
	        //JSONObject responseObject = new JSONObject(responseString);
	        //out.print(responseString);
	        if (rowCount > 0) {
	            PurchaseorderDAO.setStatusMessage(null);
	            PurchaseorderDAO.setStatusMessage("Purchase Order " + purchaseorder.getDocumentNo() + " Updated Successfully!");
	            out.print(responseString);
	        } else {
	            response.getWriter().print("FAILED");
	        }
//	        out.print("{ rowCount: \""+rowCount+"\", lineItems: "+lineItems.toString()+"");			
		} catch (JSONException e) {
			out.print(0);
			e.printStackTrace();
		}
		
		
	}

}
