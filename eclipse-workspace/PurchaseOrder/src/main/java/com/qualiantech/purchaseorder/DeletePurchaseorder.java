package com.qualiantech.purchaseorder;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;

import com.qualiantech.purchaseorder.PurchaseorderDAO;

/**
 * Servlet implementation class DeleteServlet
 */
@WebServlet("/DeletePurchaseorder")
public class DeletePurchaseorder extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();
		String purchaseorderIdsJSONString = request.getParameter("orderIds");
		try {
			JSONArray purchaseorderIdJSONArray = new JSONArray(purchaseorderIdsJSONString);
			String[] purchaseorderIds = new String[purchaseorderIdJSONArray.length()];
			for (int i = 0; i < purchaseorderIdJSONArray.length(); i++) {
				purchaseorderIds[i] = purchaseorderIdJSONArray.getString(i);
			}
			PurchaseorderDAO purchaseorderDAO = new PurchaseorderDAO();
			int deletedRowCount = purchaseorderDAO.deletePurchaseorder(purchaseorderIds);
			out.print(deletedRowCount);

		} catch (JSONException e) {
			out.print(0);
			e.printStackTrace();
		}
	}
}
