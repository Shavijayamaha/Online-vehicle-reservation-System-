package com.example.mega_city_cab.service;

public class BillCalculator {

    private static final double TAX_RATE = 0.1; // Example tax rate of 10%

    public static double calculateTotalPrice(double distanceKm, double pricePerKm, double discountRate) {
        double totalPrice = distanceKm * pricePerKm;
        double tax = totalPrice * TAX_RATE;
        double discount = totalPrice * discountRate;
        return totalPrice + tax - discount;
    }
}