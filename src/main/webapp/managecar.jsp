<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Manage Cars</title>
</head>
<body>
<h1>Manage Cars</h1>
<form action="car" method="post">
    <input type="hidden" name="action" value="add">
    <label for="model">Model:</label>
    <input type="text" id="model" name="model" required>
    <br/>
    <label for="licensePlate">License Plate:</label>
    <input type="text" id="licensePlate" name="licensePlate" required>
    <br/>
    <label for="availability">Availability:</label>
    <select id="availability" name="availability">
        <option value="true">Available</option>
        <option value="false">Not Available</option>
    </select>
    <br/>
    <button type="submit">Add Car</button>
</form>
</body>
</html>