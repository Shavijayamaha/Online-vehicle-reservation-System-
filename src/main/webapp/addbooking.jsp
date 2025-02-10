<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Add Booking</title>
</head>
<body>
<h1>Add Booking</h1>
<form action="booking" method="post">
    <input type="hidden" name="action" value="add">
    <label for="destination">Destination:</label>
    <input type="text" id="destination" name="destination" required>
    <br/>
    <label for="paymentMethod">Payment Method:</label>
    <select id="paymentMethod" name="paymentMethod">
        <option value="cash">Cash</option>
        <option value="card">Card</option>
    </select>
    <br/>
    <label for="distanceKm">Distance (km):</label>
    <input type="number" id="distanceKm" name="distanceKm" required>
    <br/>
    <label for="discountRate">Discount Rate (%):</label>
    <input type="number" id="discountRate" name="discountRate" step="0.01" value="0">
    <br/>
    <button type="submit">Make Booking</button>
</form>
</body>
</html>