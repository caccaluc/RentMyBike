package ch.zhaw.rentmybike.model.entities;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
public class RideTest {

    @Test
    public void testRideAttributes() {
        // Arrange: Erstelle ein neues Ride-Objekt mit Testdaten
        Adress pickupAdress = new Adress();
        pickupAdress.setCountry("Schweiz");
        pickupAdress.setCity("Zürich");
        pickupAdress.setPostalCode(8000);
        pickupAdress.setStreet("Bahnhofstrasse");
        pickupAdress.setStreetNumber(10);

        LocalDateTime startingTime = LocalDateTime.of(2024, 12, 25, 10, 0);
        LocalDateTime endingTime = LocalDateTime.of(2024, 12, 25, 18, 0);

        Ride ride = new Ride();
        ride.setId("1");
        ride.setOwnerId("owner123");
        ride.setRenterId("renter456");
        ride.setMotorcycleId("motorcycle789");
        ride.setPickupAdress(pickupAdress);
        ride.setStartingTime(startingTime);
        ride.setEndingTime(endingTime);
        ride.setPrice(100);
        ride.setStatus(Ride.RideStatus.BOOKED);

        // Act & Assert: Überprüfe die Attribute
        assertEquals("1", ride.getId());
        assertEquals("owner123", ride.getOwnerId());
        assertEquals("renter456", ride.getRenterId());
        assertEquals("motorcycle789", ride.getMotorcycleId());
        assertEquals(pickupAdress, ride.getPickupAdress());
        assertEquals(startingTime, ride.getStartingTime());
        assertEquals(endingTime, ride.getEndingTime());
        assertEquals(100, ride.getPrice());
        assertEquals(Ride.RideStatus.BOOKED, ride.getStatus());
    }

    @Test
    public void testRideEquality() {
        // Arrange: Erstelle zwei Ride-Objekte
        Ride ride1 = new Ride();
        ride1.setId("1");
        ride1.setOwnerId("owner123");

        Ride ride2 = new Ride();
        ride2.setId("2");
        ride2.setOwnerId("owner456");

        // Act & Assert: Überprüfe die Gleichheit und Ungleichheit
        assertNotEquals(ride1, ride2);
        assertEquals("owner123", ride1.getOwnerId());
        assertEquals("owner456", ride2.getOwnerId());
    }

    @Test
    public void testRideDefaultConstructor() {
        // Arrange: Erstelle ein neues Ride-Objekt mit dem Standardkonstruktor
        Ride ride = new Ride();

        // Act & Assert: Überprüfe, dass die Werte null oder Standardwerte sind
        assertEquals(null, ride.getId());
        assertEquals(null, ride.getOwnerId());
        assertEquals(null, ride.getRenterId());
        assertEquals(null, ride.getMotorcycleId());
        assertEquals(null, ride.getPickupAdress());
        assertEquals(null, ride.getStartingTime());
        assertEquals(null, ride.getEndingTime());
        assertEquals(0, ride.getPrice());
        assertEquals(Ride.RideStatus.AVAILABLE, ride.getStatus());
    }

    @Test
    public void testRideStatusEnum() {
        // Act: Überprüfe die Enum-Werte
        Ride.RideStatus available = Ride.RideStatus.AVAILABLE;
        Ride.RideStatus booked = Ride.RideStatus.BOOKED;
        Ride.RideStatus completed = Ride.RideStatus.COMPLETED;

        // Assert: Überprüfe die Enum-Namen
        assertEquals("AVAILABLE", available.name());
        assertEquals("BOOKED", booked.name());
        assertEquals("COMPLETED", completed.name());

        // Assert: Überprüfe die Enum-Werte
        assertEquals(Ride.RideStatus.AVAILABLE, available);
        assertEquals(Ride.RideStatus.BOOKED, booked);
        assertEquals(Ride.RideStatus.COMPLETED, completed);

        // Assert: Überprüfe Ungleichheit zwischen Enum-Werten
        assertNotEquals(Ride.RideStatus.AVAILABLE, booked);
        assertNotEquals(Ride.RideStatus.BOOKED, completed);
        assertNotEquals(Ride.RideStatus.COMPLETED, available);
    }

}