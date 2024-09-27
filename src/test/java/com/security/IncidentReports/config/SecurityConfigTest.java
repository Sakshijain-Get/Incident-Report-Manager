package com.security.IncidentReports.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Base64;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
    }

    @Test
    public void testUnauthenticatedAccess_Unauthorized() throws Exception {
        mockMvc.perform(get("/api/incident")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized()); // Expect 401 status for unauthenticated access
    }
    @Test
    @WithMockUser(username = "admin", password = "123456", roles = {"USER"})
    public void testAuthenticatedAccess_Success() throws Exception {
        mockMvc.perform(get("/api/incident")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()); // Expect 200 status for authenticated access
    }

    @Test
    public void testAuthenticatedAccess_WithBadCredentials() throws Exception {
        mockMvc.perform(get("/api/incident")
                        .with(httpBasic("badUser", "wrongPassword"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized()); // Expect 401 status for incorrect credentials
    }

    @Test
    @WithMockUser(username = "admin", password = "123456", roles = {"GUEST"}) // Using a different role
    public void testForbiddenAccess() throws Exception {
        mockMvc.perform(get("/api/incident")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden()); // Expect 403 status for forbidden access
    }


}
