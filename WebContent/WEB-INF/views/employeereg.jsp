<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<% 
 response.setHeader("Cache-Control", "no-cache , no-store , must-revalidate");
%>



	<div align="center">
  <h1>Employee Register Form</h1>
  <form action="<%= request.getContextPath() %>/register" method="post">
  <div style="color: #FF0000;">${errorMes}</div>
  <div style="color: #008000;">${errorMsg}</div>
  
  
   <table style="with: 80%">
   
    <tr>
     <td>First Name</td>
     <td><input type="text" name="firstName" /></td>
    </tr>
    <tr>
     <td>Last Name</td>
     <td><input type="text" name="lastName" /></td>
    </tr>
    <tr>
     <td>UserName</td>
     <td><input type="text" name="username" /></td>
    </tr>
    <tr>
     <td>Password</td>
     <td><input type="password" name="password" /></td>
    </tr>
   
   </table>
   <input type="submit" value="Submit" />
   
     <a href="/demo-registration/dashboard">Back</a>  
   
  </form>
  <br/>
    <!--
   <form action="${pageContext.request.contextPath}/dashboard" method="post">
    <input type="submit" value="Back" />
</form>
--> 
  <form action="${pageContext.request.contextPath}/logout" method="post">
    <input type="submit" value="Logout" />
</form>
 </div>

</body>



</html>