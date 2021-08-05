package com.ust.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.ust.dao.EmployeeDAO;
import com.ust.model.Employee;

/**
 * @author Chithra
 * 28-07-2021
 * Servlet implementation class EmployeeServlet
 */
@WebServlet("/register")
public class EmployeeServlet extends HttpServlet {

	private Logger logger = Logger.getLogger(this.getClass());


	private static final long serialVersionUID = 1L;

	private EmployeeDAO employeeDAO=new EmployeeDAO();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EmployeeServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 * 
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());

		HttpSession session = request.getSession();
		if(session.getAttribute("username") != null && session.getAttribute("username")== ConstVariables.admin_Session_Name) {
			RequestDispatcher dispatcher=request.getRequestDispatcher("/WEB-INF/views/employeereg.jsp");
			dispatcher.forward(request, response);
		}else {
			response.sendRedirect("login");

		}


	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		logger.info("Entering the post ");
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		Employee employee1 = new Employee();
		employee1.setFirstName(firstName);
		employee1.setLastName(lastName);
		employee1.setUsername(username);
		employee1.setPassword(password);

		String err=null;
		if(isFiledBlank(firstName))
		{
			err="First Name cannot be empty <br/>";
		}else if(firstName.length()>20) {
			err="First Name excceds limit <br/>";
		}
		if(isFiledBlank(lastName))
		{
			err=err.concat(" \n Last Name cannot be empty<br/>");
		} else if(lastName.length()>20) {
			err="First Name excceds limit<br/>";
		} 
		if(isFiledBlank(username))
		{
			err=err.concat(" \nUserName cannot be empty<br/>");

		}else if(username.length()>20) {
			err=err.concat("UserName excceds limit<br/>");
		}
		if(isFiledBlank(password))
		{
			err=err.concat(" \nPassword cannot be empty<br/>");

		}else if(password.length()>20) {
			err=err.concat("Password excceds limit<br/>");
		}


		if(err!=null){

			request.setAttribute("errorMes", err);
			RequestDispatcher dispatcher=request.getRequestDispatcher("/WEB-INF/views/employeereg.jsp");
			dispatcher.forward(request, response);
		}else {
			Employee employee = new Employee();
			employee.setFirstName(firstName);
			employee.setLastName(lastName);
			employee.setUsername(username);
			employee.setPassword(password);

			try {
				if(employeeDAO.isAlreadyExists(employee)) {
					err="User Already Exists";
					request.setAttribute("errorMes", err);
					RequestDispatcher dispatcher=request.getRequestDispatcher("/WEB-INF/views/employeereg.jsp");
					dispatcher.forward(request, response);
				}
				else if(employeeDAO.registerEmployee(employee)) {         
					err="Sucessfully Registered";
					request.setAttribute("errorMsg", err);
					RequestDispatcher dispatcher=request.getRequestDispatcher("/WEB-INF/views/employeereg.jsp");
					dispatcher.forward(request, response);
				}
				else {
					RequestDispatcher dispatcher=request.getRequestDispatcher("/WEB-INF/views/error.jsp");
					dispatcher.forward(request, response);
				}

			} catch (ClassNotFoundException e) {
				RequestDispatcher dispatcher=request.getRequestDispatcher("/WEB-INF/views/error.jsp");
				dispatcher.forward(request, response);
				e.printStackTrace();
			}


		}

	}


	private boolean isFiledBlank(String name) {
		return name==null || name.trim().length()==0;
	}




}
