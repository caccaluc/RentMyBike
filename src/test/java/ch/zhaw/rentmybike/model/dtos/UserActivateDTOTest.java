package ch.zhaw.rentmybike.model.dtos;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class UserActivateDTOTest {

    @Test
    public void testUserActivateDTOAttributes() {
        // Arrange: Erstelle ein neues UserActivateDTO-Objekt
        UserActivateDTO userActivateDTO = new UserActivateDTO();

        // Setze den Wert
        String testUserId = "12345";
        userActivateDTO.setUserId(testUserId);

        // Assert: Überprüfe den Getter-Wert
        assertEquals(testUserId, userActivateDTO.getUserId());
    }

    @Test
    public void testDefaultConstructor() {
        // Arrange: Erstelle ein neues UserActivateDTO-Objekt mit dem Standardkonstruktor
        UserActivateDTO userActivateDTO = new UserActivateDTO();

        // Assert: Überprüfe, dass die Attribute null sind
        assertNotNull(userActivateDTO); // Objekt wurde erstellt
        assertEquals(null, userActivateDTO.getUserId());
    }
}
