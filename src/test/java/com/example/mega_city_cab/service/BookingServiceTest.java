package com.example.mega_city_cab.service;

import com.example.mega_city_cab.dao.BookingDAO;
import com.example.mega_city_cab.model.Booking;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BookingServiceTest {

    private BookingService bookingService;
    private BookingDAO bookingDAO;
    private Booking booking;

    @BeforeEach
    void setUp() {
        // Mock BookingDAO implementation
        bookingDAO = new BookingDAO() {
            private List<Booking> bookings = new ArrayList<>();

            @Override
            public void addBooking(Booking booking) throws SQLException {
                bookings.add(booking);
            }

            @Override
            public Booking getBooking(int bookingID) throws SQLException {
                return bookings.stream()
                        .filter(b -> b.getBookingID() == bookingID)
                        .findFirst()
                        .orElse(null);
            }

            @Override
            public List<Booking> getAllBookings() throws SQLException {
                return new ArrayList<>(bookings);
            }

            @Override
            public void updateBooking(Booking booking) throws SQLException {
                for (int i = 0; i < bookings.size(); i++) {
                    if (bookings.get(i).getBookingID() == booking.getBookingID()) {
                        bookings.set(i, booking);
                        break;
                    }
                }
            }

            @Override
            public void deleteBooking(int bookingID) throws SQLException {
                bookings.removeIf(b -> b.getBookingID() == bookingID);
            }
        };

        // Initialize BookingService and inject the mock BookingDAO
        bookingService = new BookingService();
        bookingService.setBookingDAO(bookingDAO);

        // Create a sample booking for testing
        booking = new Booking();
        booking.setBookingID(1);
        booking.setCustomerID(1);
        booking.setDriverID(1);
        booking.setCarID(1);
        booking.setDestination("New York");
        booking.setPaymentMethod("Cash");
        booking.setDistanceKm(10.0);
        booking.setTotalPrice(100.0);
        booking.setBookingDate(new Date());
    }

    @Test
    void testAddBooking() throws SQLException {
        // Add the booking to the mock database
        bookingService.addBooking(booking);

        // Retrieve the booking from the mock database
        Booking retrievedBooking = bookingService.getBooking(1);

        // Assert that the booking was added successfully
        assertNotNull(retrievedBooking);
        assertEquals(1, retrievedBooking.getBookingID());
        assertEquals("New York", retrievedBooking.getDestination());
    }

    @Test
    void testGetBooking() throws SQLException {
        // Add the booking to the mock database
        bookingService.addBooking(booking);

        // Retrieve the booking from the mock database
        Booking retrievedBooking = bookingService.getBooking(1);

        // Assert that the booking was retrieved successfully
        assertNotNull(retrievedBooking);
        assertEquals(1, retrievedBooking.getBookingID());
        assertEquals("New York", retrievedBooking.getDestination());
    }

    @Test
    void testGetAllBookings() throws SQLException {
        // Add the booking to the mock database
        bookingService.addBooking(booking);

        // Retrieve all bookings from the mock database
        List<Booking> retrievedBookings = bookingService.getAllBookings();

        // Assert that the list contains the added booking
        assertNotNull(retrievedBookings);
        assertEquals(1, retrievedBookings.size());
        assertEquals("New York", retrievedBookings.get(0).getDestination());
    }

    @Test
    void testUpdateBooking() throws SQLException {
        // Add the booking to the mock database
        bookingService.addBooking(booking);

        // Update the booking's destination
        booking.setDestination("Los Angeles");
        bookingService.updateBooking(booking);

        // Retrieve the updated booking from the mock database
        Booking updatedBooking = bookingService.getBooking(1);

        // Assert that the booking was updated successfully
        assertNotNull(updatedBooking);
        assertEquals("Los Angeles", updatedBooking.getDestination());
    }

    @Test
    void testDeleteBooking() throws SQLException {
        // Add the booking to the mock database
        bookingService.addBooking(booking);

        // Delete the booking
        bookingService.deleteBooking(1);

        // Attempt to retrieve the deleted booking
        Booking deletedBooking = bookingService.getBooking(1);

        // Assert that the booking was deleted successfully
        assertNull(deletedBooking);
    }
}