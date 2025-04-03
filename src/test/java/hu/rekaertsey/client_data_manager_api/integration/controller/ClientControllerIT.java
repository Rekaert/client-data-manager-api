package hu.rekaertsey.client_data_manager_api.integration.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ClientControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    @WithMockUser(username = "admin", authorities = {"SCOPE_ADMIN"})
    void testCreateClient() throws Exception {

        mockMvc.perform(post("/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createRequest()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value("Tibor"))
                .andExpect(jsonPath("$.lastName").value("Zentai"));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"SCOPE_ADMIN"})
    void testCreateClient_BadRequest() throws Exception {

        mockMvc.perform(post("/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "user", authorities = {"SCOPE_USER"})
    void testCreateClient_Forbidden() throws Exception {

        mockMvc.perform(post("/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createRequest()))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "user", authorities = {"SCOPE_USER"})
    void testGetAllClients() throws Exception {
        mockMvc.perform(get("/clients")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    @WithMockUser(username = "user", authorities = {"SCOPE_USER"})
    void testGetClientById() throws Exception {
        mockMvc.perform(get("/clients/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @WithMockUser(username = "user", authorities = {"SCOPE_USER"})
    void testGetClientById_NotFound() throws Exception {
        mockMvc.perform(get("/clients/100")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"SCOPE_ADMIN"})
    void testUpdateClient() throws Exception {
        Map<String, Object> updateRequest = Map.of(
            "phone", "+36702457878"
        );
        String json = objectMapper.writeValueAsString(updateRequest);

        mockMvc.perform(put("/clients/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.phone").value("+36702457878"));
    }

    @Test
    @WithMockUser(username = "admin", authorities = "SCOPE_ADMIN")
    void tetsUpdateClient_InvalidFormat() throws Exception {
        Map<String, Object> updateRequest = Map.of(
            "phone", "06703458989"
        );
        String json = objectMapper.writeValueAsString(updateRequest);

        mockMvc.perform(put("/clients/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Validation failed"))
                .andExpect(jsonPath("$.errors[0]").value("Valid phone number format is +36201231234"));
    }

    @Test
    @WithMockUser(username = "user", authorities = {"SCOPE_USER"})
    void testUpdateClient_Forbidden() throws Exception {
        mockMvc.perform(put("/clients/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"SCOPE_ADMIN"})
    void testDeleteClient() throws Exception {
        mockMvc.perform(delete("/clients/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = "user", authorities = {"SCOPE_USER"})
    void testDeleteClient_Forbidden() throws Exception {
        mockMvc.perform(delete("/clients/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    private String createRequest() throws JsonProcessingException {
        Map<String, Object> clientRequest = Map.of(
            "firstName", "Tibor",
            "lastName", "Zentai",
            "birthDate", "1990-05-20",
            "email", "tibor.zentai@test.com",
            "phone", "+36201347895",
            "address", "1115 Budapest, Zebra u. 4."
        );
        return objectMapper.writeValueAsString(clientRequest);
    }
}
