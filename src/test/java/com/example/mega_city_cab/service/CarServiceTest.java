package com.example.mega_city_cab.service;

import com.example.mega_city_cab.dao.CarDAO;
import com.example.mega_city_cab.model.Car;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CarServiceTest {

    private CarService carService;
    private CarDAO carDAO;
    private Car car;

    @BeforeEach
    void setUp() {
        // Mock CarDAO implementation
        carDAO = new CarDAO() {
            private List<Car> cars = new ArrayList<>();

            @Override
            public void addCar(Car car) throws SQLException {
                cars.add(car);
            }

            @Override
            public Car getCar(int carID) throws SQLException {
                return cars.stream()
                        .filter(c -> c.getCarID() == carID)
                        .findFirst()
                        .orElse(null);
            }

            @Override
            public List<Car> getAllCars() throws SQLException {
                return new ArrayList<>(cars);
            }

            @Override
            public void updateCar(Car car) throws SQLException {
                for (int i = 0; i < cars.size(); i++) {
                    if (cars.get(i).getCarID() == car.getCarID()) {
                        cars.set(i, car);
                        break;
                    }
                }
            }

            @Override
            public void deleteCar(int carID) throws SQLException {
                cars.removeIf(c -> c.getCarID() == carID);
            }

            @Override
            public Car getCarByModel(String model) throws SQLException {
                return cars.stream()
                        .filter(c -> c.getModel().equals(model))
                        .findFirst()
                        .orElse(null);
            }
        };

        // Initialize CarService and inject the mock CarDAO
        carService = new CarService();
        carService.setCarDAO(carDAO);

        // Create a sample car for testing
        car = new Car();
        car.setCarID(1);
        car.setModel("Toyota Camry");
        car.setLicensePlate("ABC123");
        car.setAvailability(true);
    }

    @Test
    void testAddCar() throws SQLException {
        // Add the car to the mock database
        carService.addCar(car);

        // Retrieve the car from the mock database
        Car retrievedCar = carService.getCar(1);

        // Assert that the car was added successfully
        assertNotNull(retrievedCar);
        assertEquals(1, retrievedCar.getCarID());
        assertEquals("Toyota Camry", retrievedCar.getModel());
    }

    @Test
    void testGetCar() throws SQLException {
        // Add the car to the mock database
        carService.addCar(car);

        // Retrieve the car from the mock database
        Car retrievedCar = carService.getCar(1);

        // Assert that the car was retrieved successfully
        assertNotNull(retrievedCar);
        assertEquals(1, retrievedCar.getCarID());
        assertEquals("Toyota Camry", retrievedCar.getModel());
    }

    @Test
    void testGetAllCars() throws SQLException {
        // Add the car to the mock database
        carService.addCar(car);

        // Retrieve all cars from the mock database
        List<Car> retrievedCars = carService.getAllCars();

        // Assert that the list contains the added car
        assertNotNull(retrievedCars);
        assertEquals(1, retrievedCars.size());
        assertEquals("Toyota Camry", retrievedCars.get(0).getModel());
    }

    @Test
    void testUpdateCar() throws SQLException {
        // Add the car to the mock database
        carService.addCar(car);

        // Update the car's model
        car.setModel("Honda Accord");
        carService.updateCar(car);

        // Retrieve the updated car from the mock database
        Car updatedCar = carService.getCar(1);

        // Assert that the car was updated successfully
        assertNotNull(updatedCar);
        assertEquals("Honda Accord", updatedCar.getModel());
    }

    @Test
    void testDeleteCar() throws SQLException {
        // Add the car to the mock database
        carService.addCar(car);

        // Delete the car
        carService.deleteCar(1);

        // Attempt to retrieve the deleted car
        Car deletedCar = carService.getCar(1);

        // Assert that the car was deleted successfully
        assertNull(deletedCar);
    }

    @Test
    void testGetCarByModel() throws SQLException {
        // Add the car to the mock database
        carService.addCar(car);

        // Retrieve the car by its model
        Car retrievedCar = carService.getCarByModel("Toyota Camry");

        // Assert that the car was retrieved successfully
        assertNotNull(retrievedCar);
        assertEquals("Toyota Camry", retrievedCar.getModel());
    }
}