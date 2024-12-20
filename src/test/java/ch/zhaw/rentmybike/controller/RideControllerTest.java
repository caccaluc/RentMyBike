package ch.zhaw.rentmybike.controller;

import ch.zhaw.rentmybike.model.dtos.CreateRideDTO;
import ch.zhaw.rentmybike.model.entities.Motorcycle;
import ch.zhaw.rentmybike.model.entities.Ride;
import ch.zhaw.rentmybike.model.entities.User;
import ch.zhaw.rentmybike.model.entities.User.UserState;
import ch.zhaw.rentmybike.repository.MotorcycleRepository;
import ch.zhaw.rentmybike.repository.RideRepository;
import ch.zhaw.rentmybike.repository.UserRepository;
import ch.zhaw.rentmybike.security.TestSecurityConfig;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
@Import(TestSecurityConfig.class)
public class RideControllerTest {


    @Autowired
    private MockMvc mvc;

    @MockBean
    private MotorcycleRepository motorcycleRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private RideRepository rideRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private static String rideId; // Speichert die ID des erstellten Rides

    @Test
    @Order(1)
    @WithMockUser(roles = "user")
    void testCreateRide() throws Exception {
        // Arrange: Simuliere ein Motorrad im Repository
        Motorcycle motorcycle = new Motorcycle();
        motorcycle.setId("12345");
        motorcycle.setUserId("user123");
        Mockito.when(motorcycleRepository.findById("12345")).thenReturn(Optional.of(motorcycle));

        // Simuliere einen Benutzer im Repository
        User user = new User();
        user.setId("user123");
        user.setState(UserState.ACTIVE);
        Mockito.when(userRepository.findById("user123")).thenReturn(Optional.of(user));

        // Simuliere das Verhalten des RideRepository
        Ride ride = new Ride();
        ride.setId("ride123");
        Mockito.when(rideRepository.save(Mockito.any(Ride.class))).thenAnswer(invocation -> {
            Ride savedRide = invocation.getArgument(0);
            savedRide.setId("ride123");
            return savedRide;
        });

        // Erstelle ein CreateRideDTO-Objekt
        CreateRideDTO createRideDTO = new CreateRideDTO();
        createRideDTO.setMotorcycleId("12345");
        createRideDTO.setCountry("Schweiz");
        createRideDTO.setCity("Zürich");
        createRideDTO.setPostalCode(8000);
        createRideDTO.setStreet("Bahnhofstrasse");
        createRideDTO.setStreetNumber(1);
        createRideDTO.setStartingTime(LocalDateTime.of(2024, 12, 20, 10, 0));
        createRideDTO.setEndingTime(LocalDateTime.of(2024, 12, 20, 18, 0));
        createRideDTO.setPrice(100);

        String jsonBody = objectMapper.writeValueAsString(createRideDTO);

        // Act: Führe POST-Request aus
        var result = mvc.perform(post("/api/rides/create")
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                .content(jsonBody))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

        // Extract: Speichere die Ride-ID
        String jsonResponse = result.getResponse().getContentAsString();
        JsonNode jsonNode = objectMapper.readTree(jsonResponse);
        rideId = jsonNode.get("id").asText();
    }

    @Test
    @Order(2)
    @WithMockUser(roles = "user")
    void testGetRideById() throws Exception {
        // Arrange: Simuliere einen vorhandenen Ride
        Ride ride = new Ride();
        ride.setId(rideId);
        ride.setMotorcycleId("12345");
        ride.setPrice(100);
        Mockito.when(rideRepository.findById(rideId)).thenReturn(Optional.of(ride));

        // Act: Führe GET-Request aus
        mvc.perform(get("/api/rides/" + rideId)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer token"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.motorcycleId").value("12345"))
                .andExpect(jsonPath("$.price").value(100));
    }

    @Test
    @Order(3)
    @WithMockUser(roles = "user")
    void testDeleteRideById() throws Exception {
        // Arrange: Simuliere das Vorhandensein eines Rides
        Ride ride = new Ride();
        ride.setId(rideId);
        Mockito.when(rideRepository.findById(rideId)).thenReturn(Optional.of(ride));

        // Act: Führe DELETE-Request aus
        mvc.perform(delete("/api/rides/" + rideId)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer token"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    @Order(4)
    @WithMockUser(roles = "user")
    void testGetDeletedRideById() throws Exception {
        // Arrange: Simuliere, dass der Ride nicht mehr existiert
        Mockito.when(rideRepository.findById(rideId)).thenReturn(Optional.empty());

        // Act: Führe GET-Request aus
        mvc.perform(get("/api/rides/" + rideId)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer token"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}
