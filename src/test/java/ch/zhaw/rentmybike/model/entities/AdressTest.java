package ch.zhaw.rentmybike.model.entities;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
public class AdressTest {

    @Test
    public void testAdressAttributes() {
        // Arrange: Erstelle ein neues Adress-Objekt mit Testdaten
        Adress adress = new Adress();
        adress.setId("1");
        adress.setCountry("Schweiz");
        adress.setCity("Zürich");
        adress.setPostalCode(8000);
        adress.setStreet("Bahnhofstrasse");
        adress.setStreetNumber(10);

        // Act & Assert: Überprüfe die Attribute
        assertEquals("1", adress.getId());
        assertEquals("Schweiz", adress.getCountry());
        assertEquals("Zürich", adress.getCity());
        assertEquals(8000, adress.getPostalCode());
        assertEquals("Bahnhofstrasse", adress.getStreet());
        assertEquals(10, adress.getStreetNumber());
    }

    @Test
    public void testAdressEquality() {
        // Arrange: Erstelle zwei Adress-Objekte
        Adress adress1 = new Adress();
        adress1.setId("1");
        adress1.setCountry("Schweiz");
        adress1.setCity("Zürich");

        Adress adress2 = new Adress();
        adress2.setId("2");
        adress2.setCountry("Deutschland");
        adress2.setCity("Berlin");

        // Act & Assert: Überprüfe die Gleichheit und Ungleichheit
        assertNotEquals(adress1, adress2);
        assertEquals("Schweiz", adress1.getCountry());
        assertEquals("Deutschland", adress2.getCountry());
    }

}
