package task.manager.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class AuthenticationControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void shouldReturn400WhenMissingLoginRequestObject() throws Exception {
        // when & then
        mvc.perform(post("/auth/login"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturn401WhenLoginRequestParameterEmailIsNull() throws Exception {
        // given
        LoginRequest loginRequest = new LoginRequest(null, "password");

        // when & then
        mvc.perform(post("/auth/login")
                        .content(new ObjectMapper().writeValueAsString(loginRequest))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void shouldReturn401WhenLoginRequestParameterPasswordIsNull() throws Exception {
        // given
        LoginRequest loginRequest = new LoginRequest("email", null);

        // when & then
        mvc.perform(post("/auth/login")
                        .content(new ObjectMapper().writeValueAsString(loginRequest))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void shouldReturn401WhenLoginRequestParameterEmailIsEmpty() throws Exception {
        // given
        LoginRequest loginRequest = new LoginRequest("", "password");

        // when & then
        mvc.perform(post("/auth/login")
                        .content(new ObjectMapper().writeValueAsString(loginRequest))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void shouldReturn401WhenLoginRequestParameterPasswordIsEmpty() throws Exception {
        // given
        LoginRequest loginRequest = new LoginRequest("email", "");

        // when & then
        mvc.perform(post("/auth/login")
                        .content(new ObjectMapper().writeValueAsString(loginRequest))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void shouldReturn401WhenCredentialsIncorrect() throws Exception {
        // given
        LoginRequest loginRequest = new LoginRequest("wrong", "wrong");

        // when & then
        mvc.perform(post("/auth/login")
                        .content(new ObjectMapper().writeValueAsString(loginRequest))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void shouldReturn200WhenCredentialsCorrect() throws Exception {
        // given
        LoginRequest loginRequest = new LoginRequest("admin", "admin");

        // when & then
        mvc.perform(post("/auth/login")
                        .content(new ObjectMapper().writeValueAsString(loginRequest))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
