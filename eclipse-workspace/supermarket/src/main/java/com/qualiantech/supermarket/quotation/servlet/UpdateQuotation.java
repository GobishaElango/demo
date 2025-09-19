package com.qualiantech.supermarket.quotation.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.qualiantech.supermarket.quotation.QuotationDAO;
import com.qualiantech.supermarket.quotation.QuotationVO;
import com.qualiantech.supermarket.quotation.QuotationLineVO;

/**
 * Servlet implementation class UpdateServlet
 */
@WebServlet("/UpdateQuotation")
public class UpdateQuotation extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/plain");
		PrintWriter out=response.getWriter();
		String quotationJSONString=request.getParameter("quotation");
		try {
			JSONObject quotationJSONObject = new JSONObject(quotationJSONString);
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
	        int rowCount=quotationDAO.updateQuotation(quotation);
	        out.print(rowCount);

			
		} catch (JSONException e) {
			out.print(0);
			e.printStackTrace();
		}
		
		
	}

}
