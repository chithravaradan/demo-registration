<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%

  response.setHeader("Cache-Control", "no-cache , no-store , must-revalidate");
%>
<%
String id = request.getParameter("id");
String driver = "com.mysql.jdbc.Driver";
String connectionUrl = "jdbc:mysql://localhost:3306/";
String database = "employees";
String userid = "root";
String password = "Chithra1!";
try {
Class.forName(driver);
} catch (ClassNotFoundException e) {
e.printStackTrace();
}
Connection connection = null;
Statement statement = null;
ResultSet resultSet = null;
%>
<%
try{
connection = DriverManager.getConnection(connectionUrl+database, userid, password);
statement=connection.createStatement();
String sql ="select * from employee where id != 1";
resultSet = statement.executeQuery(sql);


%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"  type="text/css" >  
<!--   <script src="${pageContext.request.contextPath}/js/script.js"></script> -->
<title>Admin access page</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/logout" method="post">
    <input type="submit" value="Logout" />
</form>

<div class="container">
  <div class="split left">
    <h3>Register New Employee</h3>
    <input  class="button" type="button" value="Register" onclick="window.location='register'" >
  </div>
  <div class="split right">
    <h3>Registered Employees List</h3>
    <div class="tbclr">
<table class="tbclr1" style="" width: 200px;  border= "1" white;>

<tr>
<th>First Name</th>
<th>Last Name</th>
</tr>

<%
while(resultSet.next()){
%>
<tr>
<td><%=resultSet.getString("first_name") %></td>
<td><%=resultSet.getString("last_name") %></td>

</tr>

<%
}
connection.close();
} catch (Exception e) {
e.printStackTrace();
}
%>
</table>
  </div>
</div>
</div>


</body>
</html>