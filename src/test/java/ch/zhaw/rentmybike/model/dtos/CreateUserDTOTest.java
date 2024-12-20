package ch.zhaw.rentmybike.model.dtos;

import ch.zhaw.rentmybike.model.entities.Adress;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class CreateUserDTOTest {

    @Test
    public void testCreateUserDTOAttributes() {
        // Arrange: Erstelle ein neues CreateUserDTO-Objekt
        CreateUserDTO userDTO = new CreateUserDTO();

        // Setze die Werte
        Adress adress = new Adress();
        adress.setCountry("Schweiz");
        adress.setCity("Zürich");
        adress.setPostalCode(8000);
        adress.setStreet("Bahnhofstrasse");
        adress.setStreetNumber(10);

        userDTO.setFirstName("Max");
        userDTO.setLastName("Mustermann");
        userDTO.setEmail("max.mustermann@example.com");
        userDTO.setLicenseCode("12345-ABCDE");
        userDTO.setPhoneNumber("0123456789");
        userDTO.setAdress(adress);
        userDTO.setBirthdate(LocalDate.of(1990, 1, 1));

        // Assert: Überprüfe die Getter-Werte
        assertEquals("Max", userDTO.getFirstName());
        assertEquals("Mustermann", userDTO.getLastName());
        assertEquals("max.mustermann@example.com", userDTO.getEmail());
        assertEquals("12345-ABCDE", userDTO.getLicenseCode());
        assertEquals("0123456789", userDTO.getPhoneNumber());
        assertEquals(adress, userDTO.getAdress());
        assertEquals(LocalDate.of(1990, 1, 1), userDTO.getBirthdate());
    }

    @Test
    public void testDefaultConstructor() {
        // Arrange: Erstelle ein neues CreateUserDTO-Objekt mit dem Standardkonstruktor
        CreateUserDTO userDTO = new CreateUserDTO();

        // Assert: Überprüfe, dass die Attribute null oder Standardwerte haben
        assertNotNull(userDTO); // Objekt wurde erstellt
        assertEquals(null, userDTO.getFirstName());
        assertEquals(null, userDTO.getLastName());
        assertEquals(null, userDTO.getEmail());
        assertEquals(null, userDTO.getLicenseCode());
        assertEquals(null, userDTO.getPhoneNumber());
        assertEquals(null, userDTO.getAdress());
        assertEquals(null, userDTO.getBirthdate());
    }

    
}
