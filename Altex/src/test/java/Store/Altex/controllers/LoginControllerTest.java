package Store.Altex.controllers;

import Store.Altex.requests.LoginRequest;
import Store.Altex.services.LoginService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class LoginControllerTest {

    @Mock
    private LoginService loginService;

    @InjectMocks
    private LoginController loginController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(loginController).build();
    }


    @Test
    void getUserRoleByEmailShouldReturnRole() throws Exception {
        when(loginService.getUserRoleByEmail("user@example.com")).thenReturn("ADMIN");

        mockMvc.perform(get("/api/v1/login/role")
                        .param("email", "user@example.com"))
                .andExpect(status().isOk())
                .andExpect(content().string("ADMIN"));
    }

    @Test
    void getIdByEmailShouldReturnUserId() throws Exception {
        when(loginService.getIdByEmail("user@example.com")).thenReturn(1L);

        mockMvc.perform(get("/api/v1/login/id")
                        .param("email", "user@example.com"))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));
    }

    @Test
    void getEnabledByEmailShouldReturnEnabledStatus() throws Exception {
        when(loginService.getEnabledByEmail("user@example.com")).thenReturn(true);

        mockMvc.perform(get("/api/v1/login/enable")
                        .param("email", "user@example.com"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }
}
