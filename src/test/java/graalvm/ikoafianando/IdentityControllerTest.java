package graalvm.ikoafianando;

import graalvm.ikoafianando.controller.IdentityController;
import graalvm.ikoafianando.model.IdentityModel;
import graalvm.ikoafianando.service.IdentityService;
import graalvm.ikoafianando.validation.GlobalExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class IdentityControllerTest {

    private MockMvc mockMvc;

    @Mock
    private IdentityService identityService;

    @InjectMocks
    private IdentityController identityController;

    private IdentityModel sampleIdentity1;
    private IdentityModel sampleIdentity2;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(identityController).setControllerAdvice(new GlobalExceptionHandler()).build();

        sampleIdentity1 = new IdentityModel();;
        sampleIdentity1.setName("Iko Afianando");
        sampleIdentity1.setAge(17);
        sampleIdentity1.setId();

        sampleIdentity2 = new IdentityModel();;
        sampleIdentity2.setName("Joko Susilo");
        sampleIdentity2.setAge(20);
        sampleIdentity2.setId();

    }

    @Test
    @DisplayName("Test Create Identity")
    public void shouldCreateNewIdentity() throws Exception {
        // Using BDDMockito for better readability
        given(identityService.createIdentity(any(IdentityModel.class)))
                .willReturn(sampleIdentity1);

        // When & Then
        mockMvc.perform(post("/api/identities")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Iko Afianando\",\"age\":17}"))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Iko Afianando"))
                .andExpect(jsonPath("$.age").value(17))
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    @DisplayName("Test Get Identity By Name")
    public void shouldGetIdentityByName() throws Exception {
        given(identityService.getIdentityByName("Iko Afianando"))
                .willReturn(java.util.Optional.of(sampleIdentity1));

        mockMvc.perform(get("/api/identities/Iko Afianando"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Iko Afianando"))
                .andExpect(jsonPath("$.age").value(17));
    }

    @Test
    @DisplayName("Test Get All Identities")
    public void shouldGetAllIdentity() throws Exception {
        given(identityService.getIdentity())
                .willReturn(List.of(sampleIdentity1, sampleIdentity2));

        mockMvc.perform(get("/api/identities"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Iko Afianando"))
                .andExpect(jsonPath("$[0].age").value(17))
                .andExpect(jsonPath("$[1].name").value("Joko Susilo"))
                .andExpect(jsonPath("$[1].age").value(20));
    }

    @Test
    @DisplayName("Test Update Identity")
    public void shouldUpdateIdentity() throws Exception {
        UUID id = sampleIdentity1.getId();
        IdentityModel updatedIdentity = new IdentityModel();
        updatedIdentity.setName("Afianando Iko");
        updatedIdentity.setAge(20);
        updatedIdentity.setId();

        given(identityService.updateIdentity(eq(id), eq("Afianando Iko"), eq(20)))
                .willReturn(java.util.Optional.of(updatedIdentity));

        mockMvc.perform(put("/api/identities/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Afianando Iko\",\"age\":20}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Afianando Iko"))
                .andExpect(jsonPath("$.age").value(20));
    }

    @Test
    @DisplayName("Test Delete Identity")
    public void shouldDeleteIdentity() throws Exception {
        UUID id = sampleIdentity1.getId();

        given(identityService.deleteIdentity(id))
                .willReturn(true);

        mockMvc.perform(delete("/api/identities/{id}", id))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Should return 404 when trying to delete non existing identity")
    public void shouldReturn404WhenDeleteNonExistingIdentity() throws Exception {
        UUID id = sampleIdentity1.getId();

        given(identityService.deleteIdentity(id))
                .willReturn(false);

        mockMvc.perform(delete("/api/identities/{id}", id))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should return 404 when trying to get non existing identity")
    public void shouldReturn404WhenGetNonExistingIdentity() throws Exception {
        given(identityService.getIdentityByName("Iko Afianando"))
                .willReturn(java.util.Optional.empty());

        mockMvc.perform(get("/api/identities/Iko Afianando"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should return Validation Error when name is empty")
    public void shouldReturn400WhenCreateIdentityWithEmptyName() throws Exception {
        mockMvc.perform(post("/api/identities")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"\",\"age\":18}"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value("Validation error, Please check your input."))
                .andExpect(jsonPath("$.errors[0].field").value("name"))
                .andExpect(jsonPath("$.errors[0].message").value("Name cannot be empty"));
    }
}