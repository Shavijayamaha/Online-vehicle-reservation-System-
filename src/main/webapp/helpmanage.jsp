<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Manage Help Guidelines</title>
</head>
<body>
<h1>Manage Help Guidelines</h1>
<form action="help" method="post">
    <input type="hidden" name="action" value="add">
    <label for="guideline">Guideline:</label>
    <textarea id="guideline" name="guideline" required></textarea>
    <br/>
    <button type="submit">Add Guideline</button>
</form>
</body>
</html>