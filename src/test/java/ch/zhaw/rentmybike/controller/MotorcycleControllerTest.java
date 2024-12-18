package ch.zhaw.rentmybike.controller;

import ch.zhaw.rentmybike.model.dtos.CreateMotorcycleDTO;
import ch.zhaw.rentmybike.model.entities.Motorcycle;
import ch.zhaw.rentmybike.services.MotorcycleService;
import ch.zhaw.rentmybike.repository.MotorcycleRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MotorcycleController.class)
public class MotorcycleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MotorcycleRepository motorcycleRepository;

    @MockBean
    private MotorcycleService motorcycleService;

    // Test für den Endpunkt: /api/motorcycles/create
    @Test
    public void testCreateMotorcycle() throws Exception {
        CreateMotorcycleDTO motorcycleDTO = new CreateMotorcycleDTO();
        motorcycleDTO.setBrand("Yamaha");
        motorcycleDTO.setModel("R1");
        motorcycleDTO.setYear(2022);
        motorcycleDTO.setColor("Blau");
        motorcycleDTO.setLicensePlate("ZH123456");
        motorcycleDTO.setValue(15000);
        motorcycleDTO.setPs(200);
        motorcycleDTO.setKm(5000);
        

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

        // Mock the service to return the created motorcycle
        when(motorcycleService.createMotorcycle(any(CreateMotorcycleDTO.class))).thenReturn(motorcycle);

        mockMvc.perform(post("/api/motorcycles/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"brand\":\"Yamaha\",\"model\":\"R1\",\"year\":2022,\"color\":\"Blau\",\"licensePlate\":\"ZH123456\",\"value\":15000,\"ps\":200,\"km\":5000,\"userId\":\"user123\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.brand").value("Yamaha"))
                .andExpect(jsonPath("$.model").value("R1"))
                .andExpect(jsonPath("$.year").value(2022))
                .andExpect(jsonPath("$.color").value("Blau"))
                .andExpect(jsonPath("$.licensePlate").value("ZH123456"))
                .andExpect(jsonPath("$.value").value(15000))
                .andExpect(jsonPath("$.ps").value(200))
                .andExpect(jsonPath("$.km").value(5000))
                .andExpect(jsonPath("$.userId").value("user123"));
    }

    

    // Test für den Endpunkt: /api/motorcycles
    @Test
    public void testGetAllMotorcycles() throws Exception {
        Motorcycle motorcycle1 = new Motorcycle();
        motorcycle1.setId("1");
        motorcycle1.setModel("Yamaha R1");

        Motorcycle motorcycle2 = new Motorcycle();
        motorcycle2.setId("2");
        motorcycle2.setModel("Honda CB500");

        when(motorcycleRepository.findAll()).thenReturn(List.of(motorcycle1, motorcycle2));

        mockMvc.perform(get("/api/motorcycles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].model").value("Yamaha R1"))
                .andExpect(jsonPath("$[1].id").value("2"))
                .andExpect(jsonPath("$[1].model").value("Honda CB500"));
    }

    // Test für den Endpunkt: /api/motorcycles/{id}
    @Test
    public void testGetMotorcycleById() throws Exception {
        Motorcycle motorcycle = new Motorcycle();
        motorcycle.setId("1");
        motorcycle.setModel("Yamaha R1");

        when(motorcycleRepository.findById("1")).thenReturn(Optional.of(motorcycle));

        mockMvc.perform(get("/api/motorcycles/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.model").value("Yamaha R1"));
    }

    @Test
    public void testGetMotorcycleByIdNotFound() throws Exception {
        when(motorcycleRepository.findById("999")).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/motorcycles/999"))
                .andExpect(status().isNotFound()); // Status 404 für nicht gefunden
    }

    // Test für den Endpunkt: /api/motorcycles/user/{userId}
    @Test
    public void testGetMotorcyclesByUserId() throws Exception {
        Motorcycle motorcycle1 = new Motorcycle();
        motorcycle1.setId("1");
        motorcycle1.setModel("Yamaha R1");
        motorcycle1.setUserId("user123");

        when(motorcycleRepository.findByUserId("user123")).thenReturn(List.of(motorcycle1));

        mockMvc.perform(get("/api/motorcycles/user/user123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].model").value("Yamaha R1"))
                .andExpect(jsonPath("$[0].userId").value("user123"));
    }

    // Test für den Endpunkt: /api/motorcycles/{id} (Löschen)
    @Test
    public void testDeleteMotorcycle() throws Exception {
        when(motorcycleService.deleteMotorcycleById("1")).thenReturn(Optional.of("Motorcycle deleted successfully"));

        mockMvc.perform(delete("/api/motorcycles/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Motorcycle deleted successfully"));
    }

    @Test
    public void testDeleteMotorcycleNotFound() throws Exception {
        when(motorcycleService.deleteMotorcycleById("2")).thenReturn(Optional.empty());

        mockMvc.perform(delete("/api/motorcycles/2"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Motorcycle not found"));
    }
}
