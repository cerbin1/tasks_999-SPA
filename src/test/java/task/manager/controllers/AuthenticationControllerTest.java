package task.manager.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static task.manager.utils.ObjectMapperInstance.getObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class AuthenticationControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void shouldReturn400WhenLoginRequestParamUsernameIsNull() throws Exception {
        // when & then
        mvc.perform(post("/auth/login")
                        .content(getObjectMapper().writeValueAsString(new LoginRequest(null, "password")))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Error: Username is required.")));
    }

    @Test
    public void shouldReturn400WhenLoginRequestParamUsernameIsEmpty() throws Exception {
        // when & then
        mvc.perform(post("/auth/login")
                        .content(getObjectMapper().writeValueAsString(new LoginRequest("", "password")))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Error: Username is required.")));
    }

    @Test
    public void shouldReturn400WhenLoginRequestParamPasswordIsNull() throws Exception {
        // when & then
        mvc.perform(post("/auth/login")
                        .content(getObjectMapper().writeValueAsString(new LoginRequest("username", null)))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Error: Password is required.")));
    }

    @Test
    public void shouldReturn400WhenLoginRequestParamPasswordIsEmpty() throws Exception {
        // when & then
        mvc.perform(post("/auth/login")
                        .content(getObjectMapper().writeValueAsString(new LoginRequest("username", null)))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Error: Password is required.")));
    }
}
