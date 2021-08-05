package com.ust.controller;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ust.dao.LoginDAO;
import com.ust.model.Login;

/***
 * 
 * @author Chithra
 * 28-07-2021
 *LoginServlet
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet{
	EmployeeServlet employeeServlet=new EmployeeServlet();
	
	
private static final long serialVersionUID = 1L;
	
	private LoginDAO loginDao=new LoginDAO();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	public LoginServlet() {
        super();
    }
	
    

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		System.out.println(session.getAttribute("username"));
		response.getWriter().append("Served at: ").append(request.getContextPath());
		RequestDispatcher dispatcher=request.getRequestDispatcher("/WEB-INF/views/login.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

       
        Login login = new Login();
 
        login.setUsername(username);
        login.setPassword(password);
        
        try {
        	if(loginDao.validateEmployee(login)) {
        		HttpSession session = request.getSession();
                session.setAttribute("username", ConstVariables.admin_Session_Name);
                
    			RequestDispatcher dispatcher=request.getRequestDispatcher("/WEB-INF/views/dashboard.jsp");
    			dispatcher.forward(request, response);
        	}else if(loginDao.validateUserEmployee(login)) {
        		HttpSession session = request.getSession();
                session.setAttribute("username", ConstVariables.user_Session_Name);
               
                request.setAttribute("welcomeMsg", login.getUsername());
    			RequestDispatcher dispatcher=request.getRequestDispatcher("/WEB-INF/views/employeedetails.jsp");
    			dispatcher.forward(request, response);
        	}
        	else {
        		
        		request.setAttribute("errorMessage", "Invalid Credentials");
        		RequestDispatcher dispatcher=request.getRequestDispatcher("/WEB-INF/views/login.jsp");
    			dispatcher.forward(request, response);
        		
        	}

		} catch (ClassNotFoundException e) {
			RequestDispatcher dispatcher=request.getRequestDispatcher("/WEB-INF/views/error.jsp");
			dispatcher.forward(request, response);
			e.printStackTrace();
		}
        
	}



}