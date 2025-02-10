<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Manage Drivers</title>
</head>
<body>
<h1>Manage Drivers</h1>
<form action="driver" method="post">
    <input type="hidden" name="action" value="add">
    <label for="name">Name:</label>
    <input type="text" id="name" name="name" required>
    <br/>
    <label for="contact">Contact:</label>
    <input type="text" id="contact" name="contact" required>
    <br/>
    <label for="licenseNumber">License Number:</label>
    <input type="text" id="licenseNumber" name="licenseNumber" required>
    <br/>
    <label for="carID">Car ID:</label>
    <input type="number" id="carID" name="carID" required>
    <br/>
    <label for="availability">Availability:</label>
    <select id="availability" name="availability">
        <option value="true">Available</option>
        <option value="false">Not Available</option>
    </select>
    <br/>
    <button type="submit">Add Driver</button>
</form>
</body>
</html>