package ch.zhaw.rentmybike.controller;

import ch.zhaw.rentmybike.model.dtos.CreateMotorcycleDTO;
import ch.zhaw.rentmybike.model.entities.Motorcycle;
import ch.zhaw.rentmybike.services.MotorcycleService;
import ch.zhaw.rentmybike.services.RoleService;
import ch.zhaw.rentmybike.repository.MotorcycleRepository;
import ch.zhaw.rentmybike.security.TestSecurityConfig;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
@Import(TestSecurityConfig.class)
public class MotorcycleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MotorcycleService motorcycleService;

    @MockBean
    private RoleService roleService;

    @MockBean
    private MotorcycleRepository motorcycleRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private static String motorcycleId; // Speichert die ID eines erstellten Motorrads

    // Test 1: Erstellen eines Motorrads
    @Test
    @Order(1)
    @WithMockUser(roles = "user")
    void testCreateMotorcycle() throws Exception {

        // Mock: Rolle "user" ist vorhanden
        Mockito.when(roleService.userHasRole("user")).thenReturn(true);

        // Arrange: DTO und Mock-Motorrad erstellen
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

        // Mocken des Service-Aufrufs
        Mockito.when(motorcycleService.createMotorcycle(Mockito.any(CreateMotorcycleDTO.class))).thenReturn(motorcycle);

        // Act: POST-Request
        String jsonBody = objectMapper.writeValueAsString(motorcycleDTO);
        var result = mockMvc.perform(post("/api/motorcycles/create")
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                .content(jsonBody))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.brand").value("Yamaha"))
                .andExpect(jsonPath("$.model").value("R1"))
                .andReturn();

        // ID speichern
        String jsonResponse = result.getResponse().getContentAsString();
        JsonNode jsonNode = objectMapper.readTree(jsonResponse);
        motorcycleId = jsonNode.get("id").asText();
    }

    // Test 2: Abrufen eines Motorrads nach ID
    @Test
    @Order(2)
    @WithMockUser(roles = "user")
    void testGetMotorcycleById() throws Exception {
        // Arrange: Mock für ein Motorrad
        Motorcycle motorcycle = new Motorcycle();
        motorcycle.setId(motorcycleId);
        motorcycle.setBrand("Yamaha");
        motorcycle.setModel("R1");

        Mockito.when(motorcycleRepository.findById(motorcycleId)).thenReturn(Optional.of(motorcycle));
        Mockito.when(roleService.userHasRole("user")).thenReturn(true); // Mock der Rolle

        // Act: GET-Request
        mockMvc.perform(get("/api/motorcycles/" + motorcycleId)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer token"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(motorcycleId))
                .andExpect(jsonPath("$.brand").value("Yamaha"))
                .andExpect(jsonPath("$.model").value("R1"));
    }

    // Test 3: Löschen eines Motorrads
    @Test
    @Order(3)
    @WithMockUser(roles = "user")
    void testDeleteMotorcycle() throws Exception {
        // Arrange: Mock für das Löschen
        Mockito.when(motorcycleService.deleteMotorcycleById(motorcycleId))
                .thenReturn(Optional.of("Motorcycle deleted successfully"));
        Mockito.when(roleService.userHasRole("user")).thenReturn(true); // Mock der Rolle

        // Act: DELETE-Request
        mockMvc.perform(delete("/api/motorcycles/" + motorcycleId)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer token"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    // Test 4: Abrufen eines gelöschten Motorrads
    @Test
    @Order(4)
    @WithMockUser(roles = "user")
    void testGetDeletedMotorcycleById() throws Exception {
        // Arrange: Simuliere, dass das Motorrad nicht gefunden wird
        Mockito.when(motorcycleRepository.findById(motorcycleId)).thenReturn(Optional.empty());

        Mockito.when(roleService.userHasRole("user")).thenReturn(true); // Mock der Rolle

        // Act: GET-Request
        mockMvc.perform(get("/api/motorcycles/" + motorcycleId)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer token"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}
