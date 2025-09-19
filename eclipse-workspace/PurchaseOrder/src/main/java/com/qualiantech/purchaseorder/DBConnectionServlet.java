package com.qualiantech.purchaseorder;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/dbconnection")
public class DBConnectionServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String url = "jdbc:postgresql://localhost:5432/Supermarket";
    private static final String user = "postgres";
    private static final String password = "qualian";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        try {
        	Class.forName("org.postgresql.Driver");
    		Connection conn = DriverManager.getConnection(url, user, password);
            if (conn != null) {
                out.println("<h2>Successful!</h2>");
            } else {
                out.println("<h2>Failed!</h2>");
            }
        } catch (SQLException | ClassNotFoundException e) {
            out.println("<h2> Error: " + e.getMessage() +"</h2>");
        }
    }
}



