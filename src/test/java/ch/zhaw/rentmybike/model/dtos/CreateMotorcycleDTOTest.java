package ch.zhaw.rentmybike.model.dtos;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class CreateMotorcycleDTOTest {

    @Test
    public void testCreateMotorcycleDTOAttributes() {
        // Arrange: Erstelle ein neues DTO-Objekt
        CreateMotorcycleDTO dto = new CreateMotorcycleDTO();

        // Setze die Werte
        dto.setBrand("Yamaha");
        dto.setModel("R1");
        dto.setYear(2022);
        dto.setColor("Blau");
        dto.setLicensePlate("ZH123456");
        dto.setValue(15000);
        dto.setPs(200);
        dto.setKm(5000);

        // Assert: Überprüfe die Getter-Werte
        assertEquals("Yamaha", dto.getBrand());
        assertEquals("R1", dto.getModel());
        assertEquals(2022, dto.getYear());
        assertEquals("Blau", dto.getColor());
        assertEquals("ZH123456", dto.getLicensePlate());
        assertEquals(15000, dto.getValue());
        assertEquals(200, dto.getPs());
        assertEquals(5000, dto.getKm());
    }

    @Test
    public void testDefaultConstructor() {
        // Arrange: Erstelle ein neues DTO-Objekt mit dem Standardkonstruktor
        CreateMotorcycleDTO dto = new CreateMotorcycleDTO();

        // Assert: Überprüfe, dass die Attribute null oder Standardwerte haben
        assertNotNull(dto); // Objekt wurde erstellt
        assertEquals(null, dto.getBrand());
        assertEquals(null, dto.getModel());
        assertEquals(0, dto.getYear());
        assertEquals(null, dto.getColor());
        assertEquals(null, dto.getLicensePlate());
        assertEquals(0, dto.getValue());
        assertEquals(0, dto.getPs());
        assertEquals(0, dto.getKm());
    }

}