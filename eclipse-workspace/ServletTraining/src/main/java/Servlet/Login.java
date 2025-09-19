package Servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }
//  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		String username =request.getParameter("Name");
//		String password =request.getParameter("Password");
//		if("gobi".equals(username) && "gobi123".equals(password)) {
//			response.sendRedirect("SecondPage.html");
//		}
//		else {
//			response.sendRedirect("FirstPage.html?error=true");
//		}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username =request.getParameter("Name");
		String password =request.getParameter("Password");
		if("gobi".equals(username) && "gobi123".equals(password)) {
			response.sendRedirect("SecondPage.html");
		}
		else {
			response.sendRedirect("FirstPage.html?error=true");
		}
		
	}

}
