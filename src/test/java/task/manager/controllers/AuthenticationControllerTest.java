package task.manager.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static task.manager.utils.ObjectMapperInstance.getObjectMapper;

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
                        .content(getObjectMapper().writeValueAsString(loginRequest))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void shouldReturn401WhenLoginRequestParameterPasswordIsNull() throws Exception {
        // given
        LoginRequest loginRequest = new LoginRequest("email", null);

        // when & then
        mvc.perform(post("/auth/login")
                        .content(getObjectMapper().writeValueAsString(loginRequest))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void shouldReturn401WhenLoginRequestParameterEmailIsEmpty() throws Exception {
        // given
        LoginRequest loginRequest = new LoginRequest("", "password");

        // when & then
        mvc.perform(post("/auth/login")
                        .content(getObjectMapper().writeValueAsString(loginRequest))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void shouldReturn401WhenLoginRequestParameterPasswordIsEmpty() throws Exception {
        // given
        LoginRequest loginRequest = new LoginRequest("email", "");

        // when & then
        mvc.perform(post("/auth/login")
                        .content(getObjectMapper().writeValueAsString(loginRequest))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void shouldReturn401WhenLoginCredentialsNotExist() throws Exception {
        // given
        LoginRequest loginRequest = new LoginRequest("wrong", "wrong");

        // when & then
        mvc.perform(post("/auth/login")
                        .content(getObjectMapper().writeValueAsString(loginRequest))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void shouldReturn200WhenLoginCredentialsExist() throws Exception {
        // given
        LoginRequest loginRequest = new LoginRequest("admin", "admin");

        // when & then
        mvc.perform(post("/auth/login")
                        .content(getObjectMapper().writeValueAsString(loginRequest))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturn400WhenMissingRegisterRequestObject() throws Exception {
        // when & then
        mvc.perform(post("/auth/register"))
                .andExpect(status().isBadRequest());
    }


    @Test
    public void shouldReturn401WhenRegisterRequestParameterEmailIsNull() throws Exception {
        // given
        RegisterRequest registerRequest = new RegisterRequest(null, "password");

        // when & then
        mvc.perform(post("/auth/register")
                        .content(getObjectMapper().writeValueAsString(registerRequest))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void shouldReturn401WhenRegisterRequestParameterPasswordIsNull() throws Exception {
        // given
        RegisterRequest registerRequest = new RegisterRequest("email", null);

        // when & then
        mvc.perform(post("/auth/register")
                        .content(getObjectMapper().writeValueAsString(registerRequest))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void shouldReturn401WhenRegisterRequestParameterEmailIsEmpty() throws Exception {
        // given
        RegisterRequest registerRequest = new RegisterRequest("", "password");

        // when & then
        mvc.perform(post("/auth/register")
                        .content(getObjectMapper().writeValueAsString(registerRequest))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void shouldReturn401WhenRegisterRequestParameterPasswordIsEmpty() throws Exception {
        // given
        RegisterRequest registerRequest = new RegisterRequest("email", "");

        // when & then
        mvc.perform(post("/auth/register")
                        .content(getObjectMapper().writeValueAsString(registerRequest))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void shouldReturn401WhenRegisterCredentialsAreCorrect() throws Exception {
        // given
        RegisterRequest registerRequest = new RegisterRequest("email", "password");

        // when & then
        mvc.perform(post("/auth/register")
                        .content(getObjectMapper().writeValueAsString(registerRequest))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

}
