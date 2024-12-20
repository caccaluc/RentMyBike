package ch.zhaw.rentmybike.model.dtos;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class CreateRideDTOTest {

    @Test
    public void testCreateRideDTOAttributes() {
        // Arrange: Erstelle ein neues CreateRideDTO-Objekt
        CreateRideDTO rideDTO = new CreateRideDTO();

        // Setze die Werte
        rideDTO.setMotorcycleId("motorcycle123");
        rideDTO.setCountry("Schweiz");
        rideDTO.setCity("Zürich");
        rideDTO.setPostalCode(8000);
        rideDTO.setStreet("Bahnhofstrasse");
        rideDTO.setStreetNumber(10);
        rideDTO.setStartingTime(LocalDateTime.of(2024, 12, 25, 10, 0));
        rideDTO.setEndingTime(LocalDateTime.of(2024, 12, 25, 18, 0));
        rideDTO.setPrice(100);

        // Assert: Überprüfe die Getter-Werte
        assertEquals("motorcycle123", rideDTO.getMotorcycleId());
        assertEquals("Schweiz", rideDTO.getCountry());
        assertEquals("Zürich", rideDTO.getCity());
        assertEquals(8000, rideDTO.getPostalCode());
        assertEquals("Bahnhofstrasse", rideDTO.getStreet());
        assertEquals(10, rideDTO.getStreetNumber());
        assertEquals(LocalDateTime.of(2024, 12, 25, 10, 0), rideDTO.getStartingTime());
        assertEquals(LocalDateTime.of(2024, 12, 25, 18, 0), rideDTO.getEndingTime());
        assertEquals(100, rideDTO.getPrice());
    }

    @Test
    public void testDefaultConstructor() {
        // Arrange: Erstelle ein neues CreateRideDTO-Objekt mit dem Standardkonstruktor
        CreateRideDTO rideDTO = new CreateRideDTO();

        // Assert: Überprüfe, dass die Attribute null oder Standardwerte haben
        assertNotNull(rideDTO); // Objekt wurde erstellt
        assertEquals(null, rideDTO.getMotorcycleId());
        assertEquals(null, rideDTO.getCountry());
        assertEquals(null, rideDTO.getCity());
        assertEquals(0, rideDTO.getPostalCode());
        assertEquals(null, rideDTO.getStreet());
        assertEquals(0, rideDTO.getStreetNumber());
        assertEquals(null, rideDTO.getStartingTime());
        assertEquals(null, rideDTO.getEndingTime());
        assertEquals(0, rideDTO.getPrice());
    }

}
