package Store.Altex.controllers;

import Store.Altex.requests.RegistrationRequest;
import Store.Altex.services.RegistrationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class RegistrationControllerTest {

    @Mock
    private RegistrationService registrationService;

    @InjectMocks
    private RegistrationController registrationController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(registrationController).build();
    }

    @Test
    void confirmShouldReturnConfirmationMessage() throws Exception {

        String token = "token123";
        String confirmationMessage = "Token confirmed";

        when(registrationService.confirmToken(token)).thenReturn(confirmationMessage);


        mockMvc.perform(get("/api/v1/registration/confirm")
                        .param("token", token))
                .andExpect(status().isOk())
                .andExpect(content().string(confirmationMessage));
        verify(registrationService).confirmToken(token);
    }
}
