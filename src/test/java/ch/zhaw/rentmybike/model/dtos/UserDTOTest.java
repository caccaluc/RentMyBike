package ch.zhaw.rentmybike.model.dtos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import ch.zhaw.rentmybike.model.entities.User.UserState;

@SpringBootTest
public class UserDTOTest {

    @Test
    public void testUserDTOAttributes() {
        // Arrange: Erstelle ein neues UserDTO-Objekt
        UserDTO userDTO = new UserDTO();

        // Setze Werte für die Attribute
        String testId = "12345";
        String testFirstName = "Max";
        String testLastName = "Mustermann";
        String testEmail = "max.mustermann@example.com";
        UserState testState = UserState.ACTIVE;
        String testLicenseCode = "LICENSE123";

        userDTO.setId(testId);
        userDTO.setFirstName(testFirstName);
        userDTO.setLastName(testLastName);
        userDTO.setEmail(testEmail);
        userDTO.setState(testState);
        userDTO.setLicenseCode(testLicenseCode);

        // Assert: Überprüfe die Getter-Werte
        assertEquals(testId, userDTO.getId());
        assertEquals(testFirstName, userDTO.getFirstName());
        assertEquals(testLastName, userDTO.getLastName());
        assertEquals(testEmail, userDTO.getEmail());
        assertEquals(testState, userDTO.getState());
        assertEquals(testLicenseCode, userDTO.getLicenseCode());
    }

    @Test
    public void testDefaultConstructor() {
        // Arrange: Erstelle ein neues UserDTO-Objekt mit dem Standardkonstruktor
        UserDTO userDTO = new UserDTO();

        // Assert: Überprüfe, dass das Objekt nicht null ist und die Attribute den Standardwerten entsprechen
        assertNotNull(userDTO);
        assertEquals(null, userDTO.getId());
        assertEquals(null, userDTO.getFirstName());
        assertEquals(null, userDTO.getLastName());
        assertEquals(null, userDTO.getEmail());
        assertEquals(null, userDTO.getState());
        assertEquals(null, userDTO.getLicenseCode());
    }
}
