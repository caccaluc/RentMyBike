package ch.zhaw.rentmybike.controller;

import ch.zhaw.rentmybike.model.dtos.CreateUserDTO;
import ch.zhaw.rentmybike.model.entities.Adress;
import ch.zhaw.rentmybike.security.TestSecurityConfig;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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

public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    private static ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule())
            .disable(com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    private static String userId;

    @Test
    @Order(1)
    @WithMockUser
    public void testCreateUser() throws Exception {
        // Arrange: Erstelle ein User-Objekt
        CreateUserDTO createUserDTO = new CreateUserDTO();
        createUserDTO.setFirstName("Max");
        createUserDTO.setLastName("Mustermann");
        createUserDTO.setEmail("max.mustermann@example.com");
        createUserDTO.setLicenseCode("12345-ABCDE");
        createUserDTO.setPhoneNumber("0123456789");
        createUserDTO.setBirthdate(LocalDate.of(1990, 1, 1));

        Adress adress = new Adress();
        adress.setCountry("Schweiz");
        adress.setCity("Zürich");
        adress.setPostalCode(8000);
        adress.setStreet("Bahnhofstrasse");
        adress.setStreetNumber(1);
        createUserDTO.setAdress(adress);

        String jsonBody = objectMapper.writeValueAsString(createUserDTO);

        // Act: Führe POST-Request aus
        var result = mvc.perform(post("/api/users/create")
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                .content(jsonBody))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

        // Extract: ID des erstellten Users
        String jsonResponse = result.getResponse().getContentAsString();
        JsonNode jsonNode = objectMapper.readTree(jsonResponse);
        userId = jsonNode.get("id").asText();
    }

    @Test
    @Order(2)
    @WithMockUser
    void testGetUserById() throws Exception {
        // Act: Führe GET-Request aus
        mvc.perform(get("/api/users/" + userId)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer token"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId))
                .andExpect(jsonPath("$.firstName").value("Max"))
                .andExpect(jsonPath("$.lastName").value("Mustermann"))
                .andExpect(jsonPath("$.email").value("max.mustermann@example.com"));
    }

    @Test
    @Order(3)
    @WithMockUser
    void testDeleteUserById() throws Exception {
        // Act: Führe DELETE-Request aus
        mvc.perform(delete("/api/users/" + userId)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer token"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    @Order(4)
    @WithMockUser
    void testGetDeletedUserById() throws Exception {
        // Act: Prüfe, dass der gelöschte User nicht mehr existiert
        mvc.perform(get("/api/users/" + userId)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer token"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}
