package Servlet;
import java.io.*;
import java.sql.*;
import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ViewInout")
public class ViewInout extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final static String url="jdbc:postgresql://localhost:5432/supermarketdb";
	private final static String username="postgres";
	private final static String password="gobi";
	
	
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException {
		response.setContentType("text/html");
		
			PrintWriter pw=response.getWriter();
			try {
	            Class.forName("org.postgresql.Driver");
			}catch(ClassNotFoundException e) {
				pw.print(e);
			}
		String query="select * from m_inout;";
		try(Connection connection = DriverManager.getConnection(url,username,password)){
			PreparedStatement pst=connection.prepareStatement(query);
			ResultSet rst = pst.executeQuery();
			
			pw.print("<html><head><title>Inout  Details</title></head><body>");
			pw.print("<table border='2' rules='all'>");
			pw.print("<tr><th>InoutID</th><th>isactive</th><th>Created</th><th>Createdby</th><th>Updated</th><th>Updatedby</th><th>DocumentNo</th><th>DocumentDate</th><th>CustomerId</th></tr>");
			while(rst.next()) {
				String inout_id = rst.getString("inout_id");
				String isactive=rst.getString("isactive");
				Timestamp rawCreatedTimeStamp=rst.getTimestamp("created");
				Timestamp rawUpdatedTimeStamp=rst.getTimestamp("updated");
				ZonedDateTime createdTimestamp = rawCreatedTimeStamp!=null? rawCreatedTimeStamp.toInstant().atZone(ZoneId.of("Asia/Kolkata")):null;
				ZonedDateTime updatedTimestamp = rawUpdatedTimeStamp!=null? rawUpdatedTimeStamp.toInstant().atZone(ZoneId.of("Asia/Kolkata")):null;
				String createdby=rst.getString("createdby");
				String updatedby=rst.getString("updatedby");
				String document_no =rst.getString("document_no");
				String document_date =rst.getString("document_date");
				String customer_id =rst.getString("customer_id");
				
				pw.print("<tr><td>"+ inout_id +"</td><td>"+ isactive +"</td><td>"+ createdTimestamp+"</td><td>"+createdby+"</td><td>"+updatedTimestamp+"</td><td>"+ updatedby+"</td><td>"+ document_no+"</td><td>"+ document_date+"</td><td>"+ customer_id+"</td><tr>");
				
			}
			pw.println("</table>");
			pw.println("</body></html>");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
