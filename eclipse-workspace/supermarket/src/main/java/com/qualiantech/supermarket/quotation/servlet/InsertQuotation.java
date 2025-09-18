package com.qualiantech.supermarket.quotation.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.*;

import com.qualiantech.supermarket.quotation.*;



/**
 * Servlet implementation class InsertServlet
 */
@WebServlet("/InsertQuotation")
public class InsertQuotation extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/plain");
		PrintWriter out=response.getWriter();
		String jsonString=request.getParameter("quotation");
		try {
			JSONObject quotationJSONObject = new JSONObject(jsonString); 
			QuotationVO quotation = new QuotationVO();
	        quotation.setDocumentNo(quotationJSONObject.getString("quotationId"));
	        quotation.setDate(quotationJSONObject.getString("date"));
	        quotation.setValidTill(quotationJSONObject.getString("validTill"));
	        quotation.setVendorName(quotationJSONObject.getString("vendor"));
	        JSONArray lineItemsJSONArray = quotationJSONObject.getJSONArray("lineItems");//array of lineItem object
	        ArrayList<QuotationLineVO> lineItems = new ArrayList<>();
	        for (int i = 0; i < lineItemsJSONArray.length(); i++) {
	            JSONObject lineItemJSONObject = lineItemsJSONArray.getJSONObject(i);//each lineItem object
	            QuotationLineVO lineItem = new QuotationLineVO();
	            //lineItem.setsNo(lineItemObject.getString("sNo"));
	            lineItem.setProductName(lineItemJSONObject.getString("product"));
	            lineItem.setPrice(lineItemJSONObject.getDouble("price"));
	            lineItem.setQuantity(lineItemJSONObject.getInt("quantity"));
	            lineItem.setUom(lineItemJSONObject.getString("uom"));
	            lineItem.setLineNetAmt(lineItemJSONObject.getDouble("amount"));
	            lineItems.add(lineItem);
	        }
	        quotation.setLineItems(lineItems);
	        QuotationDAO quotationDAO=new QuotationDAO();
	        int insertRowCount=quotationDAO.insertQuotation(quotation);
	        out.print(insertRowCount);

		} catch (JSONException e) {
			out.print(0);
			e.printStackTrace();
		}
		
		//response.setCharacterEncoding("UTF-8");
		
	}

}
