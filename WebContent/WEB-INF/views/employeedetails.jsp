<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%
  response.setHeader("Cache-Control", "no-cache , no-store , must-revalidate");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<h1>Welcome 
 <span style="color: #008000;">${welcomeMsg}</span>
</h1>
<form action="${pageContext.request.contextPath}/logout" method="post">
    <input type="submit" value="Logout" />
</form>
</body>
</html>