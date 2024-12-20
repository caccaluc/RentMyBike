package ch.zhaw.rentmybike.model.dtos;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class AdressDTOTest {

    @Test
    public void testAdressDTOAttributes() {
        // Arrange: Erstelle ein neues AdressDTO-Objekt
        AdressDTO adressDTO = new AdressDTO();

        // Setze die Werte
        adressDTO.setCountry("Schweiz");
        adressDTO.setCity("Zürich");
        adressDTO.setPostalCode(8000);
        adressDTO.setStreet("Bahnhofstrasse");
        adressDTO.setStreetNumber(10);

        // Assert: Überprüfe die Getter-Werte
        assertEquals("Schweiz", adressDTO.getCountry());
        assertEquals("Zürich", adressDTO.getCity());
        assertEquals(8000, adressDTO.getPostalCode());
        assertEquals("Bahnhofstrasse", adressDTO.getStreet());
        assertEquals(10, adressDTO.getStreetNumber());
    }

}
