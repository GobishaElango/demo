package com.qualiantech.supermarket.quotation.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;

import com.qualiantech.supermarket.quotation.QuotationDAO;

/**
 * Servlet implementation class DeleteServlet
 */
@WebServlet("/DeleteQuotation")
public class DeleteQuotation extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/plain");
		PrintWriter out=response.getWriter();
		String quotationIdsJSONString=request.getParameter("quotationIds");
		try {
			JSONArray quotationIdJSONArray=new JSONArray(quotationIdsJSONString);
			String[] quotationIds=new String[quotationIdJSONArray.length()];
			for(int i=0;i<quotationIdJSONArray.length();i++) {
				quotationIds[i]=quotationIdJSONArray.getString(i);
			}
			QuotationDAO quotationDAO=new QuotationDAO();
			int deletedRowCount=quotationDAO.deleteQuotation(quotationIds);
			out.print(deletedRowCount);
			
		}
		catch (JSONException e) {
			out.print(0);
			e.printStackTrace();
		}
		
	
		
	}

}
