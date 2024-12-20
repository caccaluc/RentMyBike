package ch.zhaw.rentmybike.model.entities;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
public class UserTest {

    @Test
    public void testUserAttributes() {
        // Arrange: Erstelle ein neues User-Objekt mit Testdaten
        User user = new User();
        user.setId("1");
        user.setFirstName("Max");
        user.setLastName("Mustermann");
        user.setEmail("max.mustermann@example.com");
        user.setLicenceCode("12345-ABCDE");
        user.setBirthdate(LocalDate.of(1990, 1, 1));
        user.setPhoneNumber("0123456789");
        user.setAverageRating(4.5);
        user.setNumberOfRatings(10);

        // Act & Assert: Überprüfe die Attribute
        assertEquals("1", user.getId());
        assertEquals("Max", user.getFirstName());
        assertEquals("Mustermann", user.getLastName());
        assertEquals("max.mustermann@example.com", user.getEmail());
        assertEquals("12345-ABCDE", user.getLicenceCode());
        assertEquals(LocalDate.of(1990, 1, 1), user.getBirthdate());
        assertEquals("0123456789", user.getPhoneNumber());
        assertEquals(4.5, user.getAverageRating());
        assertEquals(10, user.getNumberOfRatings());
    }

    @Test
    public void testDefaultValues() {
        // Arrange: Erstelle ein neues User-Objekt
        User user = new User();

        // Act & Assert: Überprüfe die Standardwerte
        assertEquals(User.UserState.NEW, user.getState());
        assertEquals(0.0, user.getAverageRating());
        assertEquals(0, user.getNumberOfRatings());
        assertEquals(0, user.getRatings().size());
        assertEquals(0, user.getMotorcycleIds().size());
    }

    @Test
    public void testUserStateEnum() {
        // Arrange: Werte des Enums UserState
        User.UserState newState = User.UserState.NEW;
        User.UserState activeState = User.UserState.ACTIVE;
        User.UserState deactivatedState = User.UserState.DEACTIVATED;

        // Act & Assert: Überprüfe die Enum-Werte
        assertEquals("NEW", newState.name());
        assertEquals("ACTIVE", activeState.name());
        assertEquals("DEACTIVATED", deactivatedState.name());

        // Teste die Gleichheit von Enum-Werten
        assertEquals(User.UserState.NEW, newState);
        assertEquals(User.UserState.ACTIVE, activeState);
        assertEquals(User.UserState.DEACTIVATED, deactivatedState);

        // Teste die Ungleichheit von Enum-Werten
        assertNotEquals(User.UserState.NEW, activeState);
        assertNotEquals(User.UserState.ACTIVE, deactivatedState);
        assertNotEquals(User.UserState.DEACTIVATED, newState);
    }
}
   