<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    session.invalidate();
    response.sendRedirect("index.jsp");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Logout</title>
</head>
<body>
<h1>You have been logged out.</h1>
<br/>
<a href="index.jsp">Go to Home</a>
</body>
</html>