package ch.zhaw.rentmybike.model.entities;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
public class MotorcycleTest {

    @Test
    public void testMotorcycleAttributes() {
        // Arrange: Erstelle ein neues Motorcycle-Objekt mit Testdaten
        Motorcycle motorcycle = new Motorcycle();
        motorcycle.setId("1");
        motorcycle.setBrand("Yamaha");
        motorcycle.setModel("R1");
        motorcycle.setYear(2022);
        motorcycle.setColor("Blau");
        motorcycle.setLicensePlate("ZH123456");
        motorcycle.setValue(15000);
        motorcycle.setPs(200);
        motorcycle.setKm(5000);
        motorcycle.setUserId("user123");

        // Act & Assert: Überprüfe die Attribute
        assertEquals("1", motorcycle.getId());
        assertEquals("Yamaha", motorcycle.getBrand());
        assertEquals("R1", motorcycle.getModel());
        assertEquals(2022, motorcycle.getYear());
        assertEquals("Blau", motorcycle.getColor());
        assertEquals("ZH123456", motorcycle.getLicensePlate());
        assertEquals(15000, motorcycle.getValue());
        assertEquals(200, motorcycle.getPs());
        assertEquals(5000, motorcycle.getKm());
        assertEquals("user123", motorcycle.getUserId());
    }

    @Test
    public void testMotorcycleEquality() {
        // Arrange: Erstelle zwei Motorcycle-Objekte
        Motorcycle motorcycle1 = new Motorcycle();
        motorcycle1.setId("1");
        motorcycle1.setBrand("Yamaha");
        motorcycle1.setModel("R1");

        Motorcycle motorcycle2 = new Motorcycle();
        motorcycle2.setId("2");
        motorcycle2.setBrand("Honda");
        motorcycle2.setModel("CB500");

        // Act & Assert: Überprüfe die Gleichheit und Ungleichheit
        assertNotEquals(motorcycle1, motorcycle2);
        assertEquals("Yamaha", motorcycle1.getBrand());
        assertEquals("Honda", motorcycle2.getBrand());
    }


}
