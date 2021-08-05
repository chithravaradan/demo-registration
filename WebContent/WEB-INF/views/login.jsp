<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Admin Login</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"  type="text/css" > 
</head>
<body>
<% 
 response.setHeader("Cache-Control", "no-cache , no-store , must-revalidate");
%>
<div align="center">

  <form action="<%= request.getContextPath() %>/login" method="post">
  <div class="loginbox">
  <div style="color: #FF0000;">${errorMessage}</div>
  
  <img src="data:image/png;base64,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" class="a">
        <h2>Login Here</h2>
       
            <p>Username</p>
            <input type="text" name="username" placeholder="Enter Username">
            <p>Password</p>
            <input type="password" name="password"  placeholder="Enter Password">
            <br>
            
   
  
  
  
  <!--  
   <table style="with: 80%">
    <tr>
     <td>UserName</td>
     <td><input type="text" name="username" /></td>
    </tr>
    <tr>
     <td>Password</td>
     <td><input type="password" name="password" /></td>
    </tr>
   
   </table>
   -->
   <input type="submit" value="Submit" /> 
   
   </div>
  </form>
 </div>
 
</body>
</html>