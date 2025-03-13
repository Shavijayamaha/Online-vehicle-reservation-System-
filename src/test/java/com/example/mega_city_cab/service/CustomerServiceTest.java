package com.example.mega_city_cab.service;

import com.example.mega_city_cab.dao.CustomerDAO;
import com.example.mega_city_cab.model.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CustomerServiceTest {

    private CustomerService customerService;
    private CustomerDAO customerDAO;
    private OtpService otpService;
    private Customer customer;

    @BeforeEach
    void setUp() {
        // Mock CustomerDAO implementation
        customerDAO = new CustomerDAO() {
            private List<Customer> customers = new ArrayList<>();

            @Override
            public void addCustomer(Customer customer) throws SQLException {
                customers.add(customer);
            }

            @Override
            public Customer getCustomer(int customerID) throws SQLException {
                return customers.stream()
                        .filter(c -> c.getCustomerID() == customerID)
                        .findFirst()
                        .orElse(null);
            }

            @Override
            public List<Customer> getAllCustomers() throws SQLException {
                return new ArrayList<>(customers);
            }

            @Override
            public void updateCustomer(Customer customer) throws SQLException {
                for (int i = 0; i < customers.size(); i++) {
                    if (customers.get(i).getCustomerID() == customer.getCustomerID()) {
                        customers.set(i, customer);
                        break;
                    }
                }
            }

            @Override
            public void deleteCustomer(int customerID) throws SQLException {
                customers.removeIf(c -> c.getCustomerID() == customerID);
            }

            @Override
            public Customer getCustomerByEmailAndPassword(String email, String password) throws SQLException {
                return customers.stream()
                        .filter(c -> c.getEmail().equals(email) && c.getPassword().equals(password))
                        .findFirst()
                        .orElse(null);
            }

            @Override
            public Customer getCustomerByEmail(String email) throws SQLException {
                return customers.stream()
                        .filter(c -> c.getEmail().equals(email))
                        .findFirst()
                        .orElse(null);
            }
        };

        // Mock OtpService implementation
        otpService = new OtpService() {
            private String generatedOtp;

            @Override
            public String generateOtp(String email) {
                generatedOtp = "123456"; // Simulate OTP generation
                return generatedOtp;
            }

            @Override
            public boolean validateOtp(String email, String otp) {
                return "123456".equals(otp); // Simulate OTP validation
            }

            @Override
            public void clearOtp(String email) {
                generatedOtp = null; // Simulate clearing OTP
            }
        };

        // Initialize CustomerService and inject the mock DAO and OtpService
        customerService = new CustomerService();
        customerService.setCustomerDAO(customerDAO);
        customerService.setOtpService(otpService);

        // Create a sample customer for testing
        customer = new Customer();
        customer.setCustomerID(1);
        customer.setName("John Doe");
        customer.setAddress("123 Main St");
        customer.setNic("123456789012");
        customer.setPhone("0123456789");
        customer.setEmail("john.doe@gmail.com");
        customer.setPassword("password");
    }

    @Test
    void testAddCustomer() throws SQLException {
        // Add the customer to the mock database
        customerService.addCustomer(customer);

        // Retrieve the customer from the mock database
        Customer retrievedCustomer = customerService.getCustomer(1);

        // Assert that the customer was added successfully
        assertNotNull(retrievedCustomer);
        assertEquals(1, retrievedCustomer.getCustomerID());
        assertEquals("John Doe", retrievedCustomer.getName());
    }

    @Test
    void testGetCustomer() throws SQLException {
        // Add the customer to the mock database
        customerService.addCustomer(customer);

        // Retrieve the customer from the mock database
        Customer retrievedCustomer = customerService.getCustomer(1);

        // Assert that the customer was retrieved successfully
        assertNotNull(retrievedCustomer);
        assertEquals(1, retrievedCustomer.getCustomerID());
        assertEquals("John Doe", retrievedCustomer.getName());
    }

    @Test
    void testGetAllCustomers() throws SQLException {
        // Add the customer to the mock database
        customerService.addCustomer(customer);

        // Retrieve all customers from the mock database
        List<Customer> retrievedCustomers = customerService.getAllCustomers();

        // Assert that the list contains the added customer
        assertNotNull(retrievedCustomers);
        assertEquals(1, retrievedCustomers.size());
        assertEquals("John Doe", retrievedCustomers.get(0).getName());
    }

    @Test
    void testUpdateCustomer() throws SQLException {
        // Add the customer to the mock database
        customerService.addCustomer(customer);

        // Update the customer's name
        customer.setName("Jane Doe");
        customerService.updateCustomer(customer);

        // Retrieve the updated customer from the mock database
        Customer updatedCustomer = customerService.getCustomer(1);

        // Assert that the customer was updated successfully
        assertNotNull(updatedCustomer);
        assertEquals("Jane Doe", updatedCustomer.getName());
    }

    @Test
    void testDeleteCustomer() throws SQLException {
        // Add the customer to the mock database
        customerService.addCustomer(customer);

        // Delete the customer
        customerService.deleteCustomer(1);

        // Attempt to retrieve the deleted customer
        Customer deletedCustomer = customerService.getCustomer(1);

        // Assert that the customer was deleted successfully
        assertNull(deletedCustomer);
    }

    @Test
    void testAuthenticateCustomer_Success() throws SQLException {
        // Add the customer to the mock database
        customerService.addCustomer(customer);

        // Authenticate the customer
        Customer authenticatedCustomer = customerService.authenticateCustomer("john.doe@gmail.com", "password");

        // Assert that the customer is authenticated successfully
        assertNotNull(authenticatedCustomer);
        assertEquals(1, authenticatedCustomer.getCustomerID());
        assertEquals("John Doe", authenticatedCustomer.getName());
    }

    @Test
    void testAuthenticateCustomer_Failure() throws SQLException {
        // Add the customer to the mock database
        customerService.addCustomer(customer);

        // Attempt to authenticate with incorrect credentials
        Customer authenticatedCustomer = customerService.authenticateCustomer("john.doe@gmail.com", "wrongpassword");

        // Assert that authentication fails
        assertNull(authenticatedCustomer);
    }

    @Test
    void testGetCustomerByEmail() throws SQLException {
        // Add the customer to the mock database
        customerService.addCustomer(customer);

        // Retrieve the customer by email
        Customer retrievedCustomer = customerService.getCustomerByEmail("john.doe@gmail.com");

        // Assert that the customer was retrieved successfully
        assertNotNull(retrievedCustomer);
        assertEquals("john.doe@gmail.com", retrievedCustomer.getEmail());
    }

    @Test
    void testValidateOtp() {
        // Generate an OTP for the customer's email
        otpService.generateOtp("john.doe@gmail.com");

        // Validate the OTP
        boolean isValid = customerService.validateOtp("john.doe@gmail.com", "123456");

        // Assert that the OTP is valid
        assertTrue(isValid);
    }


}