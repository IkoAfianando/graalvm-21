package graalvm.ikoafianando;

import graalvm.ikoafianando.controller.IdentityController;
import graalvm.ikoafianando.model.IdentityModel;
import graalvm.ikoafianando.service.IdentityService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class IdentityControllerTest {

    private MockMvc mockMvc;

    @Mock
    private IdentityService identityService;

    @InjectMocks
    private IdentityController identityController;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(identityController).build();
    }

    @Test
    @DisplayName("Test Create Identity")
    public void shouldCreateNewIdentity() throws Exception {
        // Given
        IdentityModel returnIdentity = new IdentityModel();
        returnIdentity.setName("Iko Afianando");
        returnIdentity.setAge(17);
        returnIdentity.setId(); // This will set a random UUID

        // Using BDDMockito for better readability
        given(identityService.createIdentity(any(IdentityModel.class)))
                .willReturn(returnIdentity);

        // When & Then
        mockMvc.perform(post("/api/identities")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Iko Afianando\",\"age\":17}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Iko Afianando"))
                .andExpect(jsonPath("$.age").value(17))
                .andExpect(jsonPath("$.id").exists());
    }
}