package com.qualiantech.supermarket.quotation.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qualiantech.supermarket.quotation.QuotationDAO;

/**
 * Servlet implementation class GenerateQuotationDocumentNo
 */
@WebServlet("/GenerateQuotationDocumentNo")
public class GenerateQuotationDocumentNo extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 response.setContentType("text/plain");
		 QuotationDAO quotationDAO = new QuotationDAO();
		 response.getWriter().print(quotationDAO.generateDocumentNo());
		
	}


}
