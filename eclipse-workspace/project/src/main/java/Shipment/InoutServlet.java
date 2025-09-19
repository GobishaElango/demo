package Shipment;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class InoutServlet
 */
@WebServlet("/InoutServlet")
public class InoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       private InoutDAO inoutdao = new InoutDAO();
  
    public InoutServlet() {
        super();

    }

		protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
			response.setContentType("text/html");
	        
	        int page = 1;
	        int recordsPerPage = 5;
	        try {
	            if (request.getParameter("page") != null)
	                page = Integer.parseInt(request.getParameter("page"));
	            
	            String action = request.getParameter("action");
	            if ("viewInoutLine".equals(action)) {
	                String inoutId = request.getParameter("inoutId");
	                List<InoutLineVO> line = inoutdao.getInoutLineById(inoutId);
	                PrintWriter pw = response.getWriter();
	                pw.println("<h2 id=\"lineHeading\">InoutLine List</h2>");
	                pw.println("<table id=\"inout_table\"  width=\"100%\" rules=\"all\" style=\"border: 1px solid black; border-collapse: collapse; margin: auto;\">");
	                pw.println("<tr>");
	                pw.println("<th>S.No</th>");
	                pw.println("<th>InoutId</th>");
	                pw.println("<th>Product ID</th>");
	                pw.println("<th>Quantity</th>");
	                pw.println("</tr>");
	                int serialNo = 1;
	                for (InoutLineVO li : line) {
	                    pw.println("<tr>");
	                    pw.println("<td>" + serialNo++ + "</td>");
	                    pw.println("<td>" + li.getInoutId() + "</td>");
	                    pw.println("<td>" + li.getProductId()+ "</td>");
	                    pw.println("<td>" + li.getQuantity() + "</td>");
	                    pw.println("</tr>");
	                }
	                pw.println("</table>");
	                return;
	            }
	            
	            List<InoutVO> list = inoutdao.main((page - 1) * recordsPerPage, recordsPerPage);
	            int noOfRecords = inoutdao.getInoutCount();
	            int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
	            PrintWriter pw = response.getWriter();
	            
	            
	            pw.print("<html>"
	                    + "<head>"
	                        + "<title>Inout</title>"
	                        + "<link rel=\"stylesheet\" href=\"Inout.css\">"
	                        + "<link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css\">"
	                        +  "<script src=\"inout.js\"></script>"
	                    + "</head>"
	                    + "<body>"
	                        + "<h1 id=\"h1\">Inout List</h1>"
	                        +"<div class=\"icons\">"
	                        	+"<i class=\"fas fa-plus\" title=\"Add\"></i>"
	                        	+"<i class=\"fas fa-times\" title=\"Cancel\"></i>"
	                        +"</div>"
	                        + "<table id=\"m_inout\" class=\"inout_table\" width=\"100%\" rules=\"all\" style=\"border: 1px solid black; border-collapse: collapse;\">"
	                            + "<tr>"
	                            	+ "<th>S.No</th>"
	                                + "<th>Document No</th>"
	                                + "<th>Document Date</th>"
	                                + "<th>Customer Id</th>"
	                                + "<th id=\"action\">Action</th>"
	                            + "</tr>"
	                            + "<tbody id=\"tableBody\">");
	            int serialNo = (page * 5)-5;
	            for (int i = 0; i < list.size(); i++) {
	                InoutVO inoutvo = list.get(i);
	                pw.print("<tr>"
	                        + "<td>" + (++serialNo) + "</td>"
	                        + "<td>" + inoutvo.getDocumentNo() + "</td>"
	                        + "<td>" + inoutvo.getDocumentDate() + "</td>"
	                        + "<td>" + inoutvo.getCustomerId() + "</td>"
	                        + "<td>"
	                            + "<i class=\"fas fa-edit update-icon\" title=\"Update\" ></i>"
	                            + "<i class=\"fas fa-trash delete-icon\" title=\"Delete\"></i>"
	                            + "<i class=\"fas fa-eye view-icon\" title=\"View\" onclick=\"viewInoutLine('" + inoutvo.getInoutId() + "')\"></i>"
	                        + "</td>"
	                    + "</tr>");
	            }
	            pw.print("</tbody>"
	            	    + "</table>"
	            	    + "<div id=\"LineView\">"
	            	    + "<div id=\"inoutLineView\"></div>"
	            	    + "</div>"
	            	    + "<div class=\"page\">");
	 
	                    if (page > 1) {
	                        pw.print("<a href=\"InoutServlet?page=" + (page - 1) + "\">Previous</a> ");
	                    }
	                    for (int i = 1; i <= noOfPages; i++) {
	                        if (i == page) {
	                            pw.print("<span>" + i + "</span> ");
	                        } else {
	                            pw.print("<a href=\"InoutServlet?page=" + i + "\">" + i + "</a> ");
	                        }
	                    }
	                    if (page < noOfPages) {
	                        pw.print("<a href=\"InoutServlet?page=" + (page + 1) + "\">Next</a>");
	                    }
	                   
	            pw.print("</div>");
	            pw.print("</body>"
	                    + "</html>");
	            
	            pw.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while processing your request.");
	        }
	    }
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
