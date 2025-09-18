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
 * Servlet implementation class ViewServlet
 */
@WebServlet("/ViewQuotation")
public class ViewQuotation extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static int limitPerPage=5;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 String page = request.getParameter("page");
		 String confirmMessage = request.getParameter("confirmMsg")!= null? request.getParameter("confirmMsg"):"";
		 //view all Quotation
		 if(page!=null){
			  response.setContentType("text/html");  
			  int curPageIndex=Integer.parseInt(page);
		      int offset=0;
		      if(curPageIndex>1) {
		    	  offset=(curPageIndex-1)*limitPerPage;
		      }
		      int sNo=offset+1;
		      PrintWriter out=response.getWriter();
		      QuotationDAO quotationDAO = new QuotationDAO();
		      int totalPage=(int)Math.ceil((double)quotationDAO.getQuotationsCount()/limitPerPage);
		      int prevPageIndex = curPageIndex > 1 ? curPageIndex - 1 : 1;
		      int nextPageIndex = curPageIndex < totalPage ? curPageIndex + 1 : totalPage;
		      ArrayList<QuotationVO> quotationList=quotationDAO.getAllQuotations(limitPerPage,offset);
		      
		      out.print("<html><head><title>Quotation List</title>");
		      out.print("<link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css'>");
		      out.print("<link rel='stylesheet' href='quotation.css'></head>");
		      out.print("<body>");
		      out.print("<h2 style=\"text-align: center; margin-bottom: -2px;\">Quotations</h2>");
		      //lineItemTable
		      out.print("<div id=\"linedetails\">");
		      out.print("<div id=\"lineItemDiv\">");
		      out.print("<h2 style=\"text-align: center;\">Quotation Details</h2>");
		      out.print("<button  type=\"button\" class=\"close-btn\" onclick=\"hideLineItem()\">X</button>");
		      out.print("<div style=\"margin-top:50px;\">\r\n"
		      		+ "       <div class=\"right\">\r\n"                               
		      		+ "            <label for=\"liquoteId\">Quotation ID:</label>\r\n"
		      		+ "            <span id=\"liquoteId\" style=\"margin-left:14px;\">QTN00</span>\r\n"
		      		+ "       </div>\r\n"
		      		+ "       <div class=\"right\">\r\n"
		      		+ "             <label for=\"liquoteDate\">Quotation Date:</label>\r\n"
		      		+ "             <span id=\"liquoteDate\">dd/mm/yyyy</span>\r\n"
		      		+ "        </div>\r\n"
		      		+ "        <div class=\"right\">\r\n"
		      		+ "              <label for=\"livtd\">Valid Till:</label>\r\n"
		      		+ "              <span id=\"livtd\" style=\"margin-left:42px;\">dd/mm/yyyy</span>\r\n"
		      		+ "        </div>\r\n"
		      		+ "</div>");
		      out.print("<div class=\"left\">\r\n"
		      		+ "       <label for=\"livendor\">Vendor Info</label>\r\n"
		      		+ "       <p id=\"livendor\">Company Name</p>\r\n"
		      		+ "       <p id=\"livendorAdd\" style=\"margin-top: -5px;\">456 Main Street, Salem, Tamil Nadu</p>\r\n"
		      		+ "       <p id=\"livendorphone\" style=\"margin-top: -5px;\">+91 8754329103</p>  \r\n"
		      		+ " </div>");
		      out.print("<table id=\"LITable\" class=\"table table-bordered\">");
		      out.print("<thead><tr>");
		      out.print("<th>S.No</th>"
		      		+ "<th style=\"width: 250px;\">Product</th>"
		      		+ "<th style=\"width: 100px;\">Price</th>"
		      		+ "<th style=\"width: 100px;\">Quantity</th>"
		      		+ "<th>UOM</th>"
		      		+ "<th>Amount</th>");
		      out.print("</tr></thead>");
		      out.print("<tbody id=\"LIBody\"></tbody>");
		      out.print("</table></div></div>");
		      
		      //mainTable
		      out.print("<div id=\"mainTable\">");
		      out.print("<div id=\"searchBox\">\r\n"
		      		+ "      <i id=\"searchIcon\" class=\"fa fa-search\"></i>"
		      		+ "      <input type=\"search\" id=\"searchQuoteInput\" placeholder=\"Search by Quotation ID\" spellcheck=\"false\" autocomplete=\"off\">\r\n"
		      		+ " </div>");
		      out.print("<span id='confirmMsg'>"+confirmMessage+"</span>");
		      out.print("<button type=\"button\" id=\"deleteQuoteBtn\" onclick=\"deleteSelectedQuote()\" disabled>Delete</button>");
		      out.print("<button type=\"button\" id=\"addQuoteBtn\" style=\"width: 70px;\" onclick=\"displayForm()\">Add</button>");
		      out.print("<table id=\"quoteTable\">");
		      out.print("<thead><tr>");
		      out.print("<th  id=\"thCheckBox\" style=\"width: 50px;\"><input type=\"checkbox\" id=\"checkBoxHead\" class=\"check\" onclick=\"selectDeselectAll(this)\"></th>");
		      out.print("<th  id=\"thSNo\" style=\"width: 100px;\">S.No</th>");
		      out.print("<th  style=\"width: 180px;\">Quotation ID</th>");
		      out.print("<th  style=\"width: 350px;\">Vendor</th>");
		      out.print("<th  style=\"width: 200px;\">Quotation Date</th>");
		      out.print("<th  style=\"width: 200px;\">Valid Till</th>");
		      out.print("<th  style=\"width: 130px;\">Action</th>");
		      out.print("</tr></thead>");
		      out.print("<tbody id=\"quoteBody\">");
		      for(QuotationVO quotation:quotationList) {
		    	  out.print("<tr><td><input type=\"checkbox\" class=\"check\" onclick=\"enableDeleteBtn()\"></td>");
		    	  out.print("<td>"+sNo+"</td>");
		    	  out.print("<td>"+quotation.getDocumentNo()+"</td>");
		    	  out.print("<td>"+quotation.getVendorName()+"</td>");
		    	  out.print("<td>"+QuotationDAO.formatDate(quotation.getDate())+"</td>");
		    	  out.print("<td>"+QuotationDAO.formatDate(quotation.getValidTill())+"</td>");
		    	  ArrayList<QuotationLineVO>lineItems= quotation.getLineItems();
		    	  JSONArray lineItemsJSONArray = new JSONArray();
		    	  try {
		    	      for (QuotationLineVO lineItem : lineItems) {
		    	          JSONObject lineItemJSONObject = new JSONObject();
		    	          lineItemJSONObject.put("product", lineItem.getProductName());
		    	          lineItemJSONObject.put("price", lineItem.getPrice());
		    	          lineItemJSONObject.put("quantity", lineItem.getQuantity());
		    	          lineItemJSONObject.put("uom", lineItem.getUom());
		    	          lineItemJSONObject.put("lineNetAmt", lineItem.getLineNetAmt());
		    	          lineItemsJSONArray.put(lineItemJSONObject);
		    	      }
		    	  } catch (JSONException e) {
		    		  lineItemsJSONArray = new JSONArray();
		    	  }
		    	  out.print("<td><i class=\"fa fa-eye\" onclick='showLineItem(this, " + lineItemsJSONArray + ")'></i><i class=\"fa fa-edit\" onclick='updateQuotation(this," + lineItemsJSONArray + ")'></i><i class=\"fa fa-trash\" onclick=\"deleteQuotation(this)\"></i></td></tr>");
		    	  sNo++;
		      }
		      out.print("</tbody></table></div>");
		      out.print("<div id='pagination'>");
		      //previous button
		      out.print("<a href='ViewQuotation?page=" + prevPageIndex + "'");
		      if(curPageIndex==1) {
		    	  out.print("class='disableLink'");
		      }
		      out.print(">&#8249; Previous</a>");
		      
		      for(int index = 1; index <= totalPage; index++) {
		    	    out.print("<a href='ViewQuotation?page=" + index + "'");
		    	    if (index == curPageIndex) {
		    	        out.print(" class='active'");
		    	    }
		    	    out.print(">" + index + "</a>");
		      }
		      //next button
		      out.print("<a href='ViewQuotation?page=" + nextPageIndex + "'");
		      if(curPageIndex==totalPage) {
		    	  out.print("class='disableLink'");
		      }
		      out.print(">Next &#8250;</a>");
		      out.print("</div>");
		      
		      //form
		      out.print("<div id=\"formDiv\"><form id=\"quoteForm\">");	      
		      out.print("<h2 id=\"formh2\"style=\"text-align: center; margin-top: -3px; margin-bottom:35px;\">Add Quotation</h2>");	      
		      out.print("<div id=\"quoteDetails\">\r\n"
		      		+ "      <div style=\"margin-top: -12px;\">\r\n"
		      		+ "           <label for=\"quoteId\">Quotation ID: </label>\r\n"
		      		+ "           <p id=\"quoteId\" style=\"display: inline;\">0</p>\r\n"
		      		+ "      </div>\r\n"
		      		+ "      <div>\r\n"
		      		+ "           <label for=\"quoteDate\">Quotation Date<sup>*</sup>: </label>\r\n"
		      		+ "           <input type=\"date\" id=\"quoteDate\" name=\"quoteDate\" style=\"width: 128px;\" oninput=\"changeValidTill()\">\r\n"
		      		+ "      </div>\r\n"
		      		+ "      <div>\r\n"
		      		+ "           <label for=\"vtd\">Valid till<sup>*</sup>:</label>\r\n"
		      		+ "           <input type=\"date\" id=\"vtd\" name=\"vtd\" oninput=\"toggleErrorClass('vtd')\">\r\n"
		      		+ "      </div>\r\n"
		      		+ "</div>");	      
		      out.print("<div id=\"vendorDetails\"  style=\"margin: 8px 0;\">\r\n"
		      		+ "      <label for=\"vendor\">Vendor<sup>*</sup>: </label>\r\n"
		      		+ "      <select id=\"vendor\" name=\"vendor\" oninput=\"toggleErrorClass('vendor')\">\r\n"
		      		+ "      <option value=\"Select\">Select</option>\r\n"
		      		+ "      <option value=\"QMart\">QMart</option>\r\n"
		      		+ "      <option value=\"Sakthi Goods Pvt. Ltd\">Sakthi Goods Pvt. Ltd</option>\r\n"
		      		+ "      <option value=\"Tamil Nadu Spices Pvt. Ltd\">Tamil Nadu Spices Pvt. Ltd</option>\r\n"
		      		+ "      <option value=\"Coimbatore Agro Farms\">Coimbatore Agro Farms</option>\r\n"
		      		+ "      <option value=\"Salem Dairy Products\">Salem Dairy Products</option>\r\n"
		      		+ "      </select>\r\n"
		      		+ " </div>");	      
		      out.print("<div id=\"product\">");	      
		      out.print("<fieldset style=\"margin-bottom: 15px;\">");	      
		      out.print("<legend>Line Items</legend>");	      
		      out.print("<fieldset id=\"fs\" style=\"border-width: 1px; padding-bottom: 14px; padding-top: 0;\">");	      
		      out.print("<legend id=\"legend\">Add Product<sup>*</sup></legend>\r\n"
		      		+ " <div class=\"errorDiv\">\r\n"
		      		+ "     <span id=\"prodError\" style=\"width: 350px;\"></span>\r\n"
		      		+ "     <span id=\"priceError\" style=\"width: 140px;margin: 0 22px;\"></span>\r\n"
		      		+ "     <span id=\"quantError\" style=\"width: 140px;\"></span>\r\n"
		      		+ " </div>");	      
		      out.print("<div id=\"productDetail\">");	      
		      out.print("<select id=\"productName\" name=\"productName\" oninput=\"autoFillUOM(this)\" style=\"width: 350px;\">\r\n"
		      		+ "  	<option value=\"Select\" id=\"Select\">Select</option>\r\n"
		      		+ "     <option value=\"Apple\" id=\"Apple\">Apple</option>\r\n"
		      		+ "     <option value=\"Aachi Chilli Powder - 100g\" id=\"Aachi Chilli Powder - 100g\">Aachi Chilli Powder - 100g</option>\r\n"
		      		+ "     <option value=\"Gold Winner Refined Sunflower Oil - 1L\" id=\"Gold Winner Refined Sunflower Oil - 1L\">Gold Winner Refined Sunflower Oil - 1L</option>\r\n"
		      		+ "     <option value=\"Tata Urad Dal - 1Kg\" id=\"Tata Urad Dal - 1Kg\">Tata Urad Dal - 1Kg</option>\r\n"
		      		+ "     <option value=\"Aashirvaad Whole Wheat Flour - 1Kg\" id=\"Aashirvaad Whole Wheat Flour - 1Kg\">Aashirvaad Whole Wheat Flour - 1Kg</option>\r\n"
		      		+ "     <option value=\"Sugar\" id=\"Sugar\">Sugar</option>\r\n"
		      		+ "     <option value=\"Onion\" id=\"Onion\">Onion</option>\r\n"
		      		+ " </select>");	      
		      out.print("<div style=\"display: flex;\">\r\n"
		      		+ "  	<label for=\"price\" style=\"padding: 5px; margin-left: 5px;\">&#8377 </label>\r\n"
		      		+ "     <input type=\"number\" id=\"price\" placeholder=\"Price\" style=\"width: 145px;\" oninput=\"validatePrice(this)\">\r\n"
		      		+ "</div>");	      
		      out.print("<div>\r\n"
		      		+ "  	<input type=\"number\" id=\"quantity\" placeholder=\"Quantity\"  style=\"width: 140px;margin-left:10px;\" oninput=\"validateQuantity(this)\">\r\n"
		      		+ " </div>");	      
		      out.print("<div id=\"uomDiv\">\r\n"
		      		+ "     <p id=\"uom\"></p>\r\n"
		      		+ " </div> ");	      
		      out.print("</div>");	      
		      out.print("<div class=\"btn\" style=\"margin-top: 15px;\">\r\n"
		      		+ "     <button type=\"button\"  id=\"addBtn\"> Add </button>   \r\n"
		      		+ "     <button type=\"button\"  id=\"clearBtn\">Clear</button>\r\n"
		      		+ " </div>");	      
		      out.print("</fieldset>");	      
		      out.print("<div>\r\n"
		      		+ "     <button type=\"button\" id=\"deleteSelectedBtn\" onclick=\"deleteSelectedLineItems()\" disabled>Delete</button>            \r\n"
		      		+ " </div>");	      
		      out.print("<div class=\"qlineitem\">  ");	      
		      out.print("<table id=\"lineitemTable\">");	      
		      out.print("<thead>\r\n"
		      		+ "     <tr>\r\n"
		      		+ "     <th style=\"width:38px;\"><input type=\"checkbox\" class=\"checkLineItems\" id=\"checkHeadLI\" onclick=\"selectDeselectAllLineItems(this)\"></th>\r\n"
		      		+ "     <th>S.No</th>\r\n"
		      		+ "     <th style=\"width: 290px;\">Product</th>\r\n"
		      		+ "     <th style=\"width: 80px;\">Price</th>\r\n"
		      		+ "     <th style=\"width: 80px;\">Quantity</th>\r\n"
		      		+ "     <th>UOM</th>\r\n"
		      		+ "     <th style=\"width: 100px;\">Amount</th>\r\n"
		      		+ "     <th style=\"text-align: center; width: 80px;\">Action</th>\r\n"
		      		+ "     </tr>\r\n"
		      		+ " </thead>");	      
		      out.print("<tbody id=\"lineItemsBody\"></tbody>");	      
		      out.print("</table>");	      
		      out.print("</div></fieldset>");	      
		      out.print("</div>");	      
		      out.print("<div class=\"btn\">\r\n"
		      		+ "  	<button type=\"button\" id=\"submit\" onclick=\"submitForm()\">Submit</button>\r\n"
		      		+ "     <button type=\"button\" id=\"cancel\" onclick=\"cancelForm()\">Cancel</button>\r\n"
		      		+ " </div>");	      
		      out.print("</form></div>");	
		      out.print("<div id=\"alertDiv\">");
		      out.print("<div id=\"alertBox\">\r\n"
		      		+ "  	<h4 id=\"alertContent\" style=\"text-align: center; margin-top: 16px;\">Do you want to Cancel ?</h4>\r\n"
		      		+ "		<div id=\"alertBtn\">\r\n"
		      		+ "		<button id=\"yes\">Yes</button>\r\n"
		      		+ "		<button id=\"no\">No</button>\r\n"
		      		+ "		</div>\r\n"
		      		+ " </div>");
		      out.print("</div>");
		      out.print("<div id=\"confirmDiv\">\r\n"
		      		+ "    <div id=\"confirmBox\">\r\n"
		      		+ "        <h3 id=\"confirmContent\" style=\"text-align: center; margin-top: 16px;\">Quotation Added Successfully !!</h3>\r\n"
		      		+ "        <p id=\"lineItemCount\"></p>\r\n"
		      		+ "        <a id=\"ok\">Ok</a>   \r\n"
		      		+ "    </div>\r\n"
		      		+ "</div>");
		      
		      
		      out.print("<script src=\"quotation.js\"></script>");
		      out.print("</body></html>");			 
		 }
		 
		 //view Quotation by Id
		 else {
			 response.setContentType("text/plain");
			 response.setCharacterEncoding("UTF-8");
			 String quotationId=request.getParameter("quotationId");
			 PrintWriter out=response.getWriter();
			 QuotationDAO quotationDAO=new QuotationDAO();
			 QuotationVO quotation=quotationDAO.getQuotationById(quotationId);
			 JSONObject quotationJSONObject=new JSONObject();
			 if(quotation!=null){
				 try {
					 quotationJSONObject.put("quotationId",quotation.getDocumentNo());
					 quotationJSONObject.put("vendor",quotation.getVendorName());
					 quotationJSONObject.put("date",QuotationDAO.formatDate(quotation.getDate()));
					 quotationJSONObject.put("validTill",QuotationDAO.formatDate(quotation.getValidTill()));
					 ArrayList<QuotationLineVO>lineItems= quotation.getLineItems();
					 JSONArray lineItemsJSONArray = new JSONArray();
					 for (QuotationLineVO lineItem : lineItems) {
						 JSONObject lineItemJSONObject = new JSONObject();
						 lineItemJSONObject.put("product", lineItem.getProductName());
						 lineItemJSONObject.put("price", lineItem.getPrice());
						 lineItemJSONObject.put("quantity", lineItem.getQuantity());
						 lineItemJSONObject.put("uom", lineItem.getUom());
						 lineItemJSONObject.put("lineNetAmt", lineItem.getLineNetAmt());
						 lineItemsJSONArray.put(lineItemJSONObject);
					 } 
					 quotationJSONObject.put("lineItems",lineItemsJSONArray);
					 out.print(quotationJSONObject);
				 }
				 catch (JSONException e) {
					 out.print("Failed to get Quotation");
					 e.printStackTrace();
				 }
						
			 }
			 else {
				 out.print("null");
			 }
		 }

	}
	
}
