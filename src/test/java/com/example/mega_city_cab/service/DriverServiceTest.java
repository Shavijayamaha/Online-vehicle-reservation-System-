package com.example.mega_city_cab.service;

import com.example.mega_city_cab.dao.DriverDAO;
import com.example.mega_city_cab.model.Car;
import com.example.mega_city_cab.model.Driver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DriverServiceTest {

    private DriverService driverService;
    private DriverDAO driverDAO;
    private Driver driver;
    private Car car;

    @BeforeEach
    void setUp() {
        // Mock DriverDAO implementation
        driverDAO = new DriverDAO() {
            private List<Driver> drivers = new ArrayList<>();

            @Override
            public void addDriver(Driver driver) throws SQLException {
                drivers.add(driver);
            }

            @Override
            public Driver getDriver(int driverID) throws SQLException {
                return drivers.stream()
                        .filter(d -> d.getDriverID() == driverID)
                        .findFirst()
                        .orElse(null);
            }

            @Override
            public List<Driver> getAllDrivers() throws SQLException {
                return new ArrayList<>(drivers);
            }

            @Override
            public void updateDriver(Driver driver) throws SQLException {
                for (int i = 0; i < drivers.size(); i++) {
                    if (drivers.get(i).getDriverID() == driver.getDriverID()) {
                        drivers.set(i, driver);
                        break;
                    }
                }
            }

            @Override
            public void deleteDriver(int driverID) throws SQLException {
                drivers.removeIf(d -> d.getDriverID() == driverID);
            }
        };

        // Initialize DriverService and inject the mock DriverDAO
        driverService = new DriverService();
        driverService.setDriverDAO(driverDAO);

        // Create a sample car for testing
        car = new Car();
        car.setCarID(1);
        car.setModel("Toyota Camry");
        car.setLicensePlate("ABC123");
        car.setAvailability(true);

        // Create a sample driver for testing
        driver = new Driver();
        driver.setDriverID(1);
        driver.setName("John Doe");
        driver.setContact("1234567890");
        driver.setLicenseNumber("LIC12345");
        driver.setCar(car);
        driver.setAvailability(true);
    }

    @Test
    void testAddDriver() throws SQLException {
        // Add the driver to the mock database
        driverService.addDriver(driver);

        // Retrieve the driver from the mock database
        Driver retrievedDriver = driverService.getDriver(1);

        // Assert that the driver was added successfully
        assertNotNull(retrievedDriver);
        assertEquals(1, retrievedDriver.getDriverID());
        assertEquals("John Doe", retrievedDriver.getName());
    }

    @Test
    void testGetDriver() throws SQLException {
        // Add the driver to the mock database
        driverService.addDriver(driver);

        // Retrieve the driver from the mock database
        Driver retrievedDriver = driverService.getDriver(1);

        // Assert that the driver was retrieved successfully
        assertNotNull(retrievedDriver);
        assertEquals(1, retrievedDriver.getDriverID());
        assertEquals("John Doe", retrievedDriver.getName());
    }

    @Test
    void testGetAllDrivers() throws SQLException {
        // Add the driver to the mock database
        driverService.addDriver(driver);

        // Retrieve all drivers from the mock database
        List<Driver> retrievedDrivers = driverService.getAllDrivers();

        // Assert that the list contains the added driver
        assertNotNull(retrievedDrivers);
        assertEquals(1, retrievedDrivers.size());
        assertEquals("John Doe", retrievedDrivers.get(0).getName());
    }

    @Test
    void testUpdateDriver() throws SQLException {
        // Add the driver to the mock database
        driverService.addDriver(driver);

        // Update the driver's name
        driver.setName("Jane Doe");
        driverService.updateDriver(driver);

        // Retrieve the updated driver from the mock database
        Driver updatedDriver = driverService.getDriver(1);

        // Assert that the driver was updated successfully
        assertNotNull(updatedDriver);
        assertEquals("Jane Doe", updatedDriver.getName());
    }

    @Test
    void testDeleteDriver() throws SQLException {
        // Add the driver to the mock database
        driverService.addDriver(driver);

        // Delete the driver
        driverService.deleteDriver(1);

        // Attempt to retrieve the deleted driver
        Driver deletedDriver = driverService.getDriver(1);

        // Assert that the driver was deleted successfully
        assertNull(deletedDriver);
    }
}