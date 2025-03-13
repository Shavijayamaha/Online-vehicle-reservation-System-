package com.example.mega_city_cab.service;

import com.example.mega_city_cab.dao.AdminDAO;
import com.example.mega_city_cab.model.Admin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

class AdminServiceTest {

    private AdminService adminService;
    private AdminDAO adminDAO;
    private Admin admin;

    @BeforeEach
    void setUp() {
        // Mock AdminDAO implementation
        adminDAO = new AdminDAO() {
            private List<Admin> admins = new ArrayList<>();

            @Override
            public Admin getAdminByUsernameAndPassword(String username, String password) throws SQLException {
                return admins.stream()
                        .filter(a -> a.getUsername().equals(username) && a.getPassword().equals(password))
                        .findFirst()
                        .orElse(null);
            }

            @Override
            public void addAdmin(Admin admin) throws SQLException {
                admins.add(admin);
            }

            @Override
            public void updateAdmin(Admin admin) throws SQLException {
                for (int i = 0; i < admins.size(); i++) {
                    if (admins.get(i).getAdminID() == admin.getAdminID()) {
                        admins.set(i, admin);
                        break;
                    }
                }
            }

            @Override
            public void deleteAdmin(int adminID) throws SQLException {
                admins.removeIf(a -> a.getAdminID() == adminID);
            }
        };

        // Initialize AdminService with the mock AdminDAO
        adminService = new AdminService();
        adminService.setAdminDAO(adminDAO);

        // Create a sample admin for testing
        admin = new Admin();
        admin.setAdminID(1);
        admin.setUsername("admin");
        admin.setPassword("password");
    }

    @Test
    void testAuthenticateAdmin_Success() throws SQLException {
        // Add the admin to the mock database
        adminService.addAdmin(admin);

        // Authenticate the admin
        Admin authenticatedAdmin = adminService.authenticateAdmin("admin", "password");

        // Assert that the admin is authenticated successfully
        assertNotNull(authenticatedAdmin);
        assertEquals(1, authenticatedAdmin.getAdminID());
        assertEquals("admin", authenticatedAdmin.getUsername());
        assertEquals("password", authenticatedAdmin.getPassword());
    }

    @Test
    void testAuthenticateAdmin_Failure() throws SQLException {
        // Add the admin to the mock database
        adminService.addAdmin(admin);

        // Attempt to authenticate with incorrect credentials
        Admin authenticatedAdmin = adminService.authenticateAdmin("admin", "wrongpassword");

        // Assert that authentication fails
        assertNull(authenticatedAdmin);
    }

    @Test
    void testAddAdmin() throws SQLException {
        // Add the admin to the mock database
        adminService.addAdmin(admin);

        // Retrieve the admin from the mock database
        Admin retrievedAdmin = adminService.authenticateAdmin("admin", "password");

        // Assert that the admin was added successfully
        assertNotNull(retrievedAdmin);
        assertEquals(1, retrievedAdmin.getAdminID());
        assertEquals("admin", retrievedAdmin.getUsername());
        assertEquals("password", retrievedAdmin.getPassword());
    }

    @Test
    void testUpdateAdmin() throws SQLException {
        // Add the admin to the mock database
        adminService.addAdmin(admin);

        // Update the admin's password
        admin.setPassword("newpassword");
        adminService.updateAdmin(admin);

        // Retrieve the updated admin from the mock database
        Admin updatedAdmin = adminService.authenticateAdmin("admin", "newpassword");

        // Assert that the admin was updated successfully
        assertNotNull(updatedAdmin);
        assertEquals("newpassword", updatedAdmin.getPassword());
    }

    @Test
    void testDeleteAdmin() throws SQLException {
        // Add the admin to the mock database
        adminService.addAdmin(admin);

        // Delete the admin
        adminService.deleteAdmin(1);

        // Attempt to retrieve the deleted admin
        Admin deletedAdmin = adminService.authenticateAdmin("admin", "password");

        // Assert that the admin was deleted successfully
        assertNull(deletedAdmin);
    }
}