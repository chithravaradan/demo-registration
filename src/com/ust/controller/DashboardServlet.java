package com.ust.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/***
 * 
 * @author Chithra Varadan
 * 06-04-2021
 *Class for dashboard servlet implementation
 */
@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	 
    public DashboardServlet() {
        super();
    }
    
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	HttpSession session = request.getSession();
		 if(session.getAttribute("username") != null && session.getAttribute("username")==ConstVariables.admin_Session_Name) {
			 RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/dashboard.jsp");
		    	dispatcher.forward(request, response);
		 }else {
			 response.sendRedirect("login");

		 }
    }
    
		 protected void doPost(HttpServletRequest request, HttpServletResponse response)
		            throws ServletException, IOException {
		    	
		    	HttpSession session = request.getSession();
				 if(session.getAttribute("username") != null) {
					 
					 RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/dashboard.jsp");
				    	dispatcher.forward(request, response);
				 }else {
					 response.sendRedirect("login");

				 }

    }

}
