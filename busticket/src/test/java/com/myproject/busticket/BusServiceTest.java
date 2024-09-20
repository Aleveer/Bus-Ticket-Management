package com.myproject.busticket;

import com.myproject.busticket.models.Bus;
import com.myproject.busticket.models.Driver;
import com.myproject.busticket.repositories.BusRepository;
import com.myproject.busticket.services.BusService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class BusServiceTest {

    @Mock
    private BusRepository busRepository;

    @InjectMocks
    private BusService busService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Check connection to database:
    @Test
    void testConnection() {
        assertNotNull(busRepository);
    }

    @Test
    void testGetAll() {
        Bus bus1 = new Bus();
        Bus bus2 = new Bus();
        Bus bus3 = new Bus();
        when(busRepository.findAll()).thenReturn(Arrays.asList(bus1, bus2, bus3));

        List<Bus> buses = busService.getAll();
        assertEquals(3, buses.size());
        verify(busRepository, times(1)).findAll();
    }

    @Test
    void testGetById() {
        // Arrange
        Bus expectedBus = new Bus(); // Create a Bus object with appropriate properties
        when(busRepository.findById(2)).thenReturn(Optional.of(expectedBus));

        // Act
        Bus bus = busService.getById(2);

        // Assert
        assertNotNull(bus);
        assertEquals(expectedBus, bus); // Optionally, check if the returned bus is the expected one
        verify(busRepository, times(1)).findById(2);
    }

    @Test
    void testGetByIdNotFound() {
        // Arrange
        when(busRepository.findById(5)).thenReturn(Optional.empty());

        // Act
        Bus foundBus = busService.getById(5);

        // Assert
        assertNull(foundBus);
        verify(busRepository, times(1)).findById(5);
    }

    @Test
    void testSave() {
        Bus bus = new Bus();
        bus.setId(1);
        bus.setPlateNumber("ABC123");
        bus.setBusType("Luxury");
        Driver driver = new Driver();
        driver.setId(1);
        driver.setFullName("John Doe");
        bus.setDriver(driver);

        when(busRepository.save(bus)).thenReturn(bus);

        Bus savedBus = busService.save(bus);
        assertNotNull(savedBus);
        verify(busRepository, times(1)).save(bus);
    }

    @Test
    void testDeleteById() {
        // Arrange
        Bus bus = new Bus();
        when(busRepository.findById(1)).thenReturn(Optional.of(bus));

        // Act
        Bus deletedBus = busService.deleteById(1);

        // Assert
        assertNotNull(deletedBus);
        verify(busRepository, times(1)).findById(1);
        verify(busRepository, times(1)).deleteById(1);
    }

    @Test
    void testGetByPlateNumber() {
        Bus bus = new Bus();
        when(busRepository.findByPlateNumber("ABC123")).thenReturn(bus);

        Bus foundBus = busService.getByPlateNumber("ABC123");
        assertNotNull(foundBus);
        verify(busRepository, times(1)).findByPlateNumber("ABC123");
    }

    @Test
    void testGetByPlateNumberNotFound() {
        // Arrange
        when(busRepository.findByPlateNumber("XYZ78900")).thenReturn(null);

        // Act
        Bus foundBus = busService.getByPlateNumber("XYZ78900");

        // Assert
        assertNull(foundBus);
        verify(busRepository, times(1)).findByPlateNumber("XYZ78900");
    }

    @Test
    void testGetByBusType() {
        Bus bus1 = new Bus();
        Bus bus2 = new Bus();
        when(busRepository.findByBusType("Luxury")).thenReturn(Arrays.asList(bus1, bus2));

        List<Bus> buses = busService.getByBusType("Luxury");
        assertEquals(2, buses.size());
        verify(busRepository, times(1)).findByBusType("Luxury");
    }

    @Test
    void testGetByDriverId() {
        Bus bus1 = new Bus();
        Bus bus2 = new Bus();
        when(busRepository.findByDriverId(1)).thenReturn(Arrays.asList(bus1, bus2));

        List<Bus> buses = busService.getByDriverId(1);
        assertEquals(2, buses.size());
        verify(busRepository, times(1)).findByDriverId(1);
    }
}