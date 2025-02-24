<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
</head>
<body>
<h1>Login</h1>
<form action="login" method="post">
    <label for="emailOrUsername">Email or Username:</label>
    <input type="text" id="emailOrUsername" name="emailOrUsername" required>
    <br/>
    <label for="password">Password:</label>
    <input type="password" id="password" name="password" required>
    <br/>
    <button type="submit">Login</button>
</form>
<br/>
<a href="register.jsp">Don't have an account? Register here.</a>
</body>
</html>