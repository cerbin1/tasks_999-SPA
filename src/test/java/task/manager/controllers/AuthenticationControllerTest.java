package task.manager.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import task.manager.entity.UsersRepository;

import java.util.Set;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
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

    @MockBean
    private
    UsersRepository usersRepository;

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

    @Test
    public void shouldReturn400WhenRegisterRequestParamEmailIsNull() throws Exception {
        // when & then
        mvc.perform(post("/auth/register")
                        .content(getObjectMapper()
                                .writeValueAsString(
                                        new RegisterRequest(null, "username", "password", "name", "surname", Set.of("role"))))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Error: Email is required.")));
    }

    @Test
    public void shouldReturn400WhenRegisterRequestParamEmailIsEmpty() throws Exception {
        // when & then
        mvc.perform(post("/auth/register")
                        .content(getObjectMapper()
                                .writeValueAsString(
                                        new RegisterRequest("", "username", "password", "name", "surname", Set.of("role"))))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Error: Email is required.")));
    }

    @Test
    public void shouldReturn400WhenRegisterRequestParamUsernameIsNull() throws Exception {
        // when & then
        mvc.perform(post("/auth/register")
                        .content(getObjectMapper()
                                .writeValueAsString(
                                        new RegisterRequest("email", null, "password", "name", "surname", Set.of("role"))))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Error: Username is required.")));
    }

    @Test
    public void shouldReturn400WhenRegisterRequestParamUsernameIsEmpty() throws Exception {
        // when & then
        mvc.perform(post("/auth/register")
                        .content(getObjectMapper()
                                .writeValueAsString(
                                        new RegisterRequest("email", "", "password", "name", "surname", Set.of("role"))))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Error: Username is required.")));
    }

    @Test
    public void shouldReturn400WhenRegisterRequestParamPasswordIsNull() throws Exception {
        // when & then
        mvc.perform(post("/auth/register")
                        .content(getObjectMapper()
                                .writeValueAsString(
                                        new RegisterRequest("email", "username", null, "name", "surname", Set.of("role"))))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Error: Password is required.")));
    }

    @Test
    public void shouldReturn400WhenRegisterRequestParamPasswordIsEmpty() throws Exception {
        // when & then
        mvc.perform(post("/auth/register")
                        .content(getObjectMapper()
                                .writeValueAsString(
                                        new RegisterRequest("email", "username", "", "name", "surname", Set.of("role"))))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Error: Password is required.")));
    }

    @Test
    public void shouldReturn400WhenRegisterRequestParamNameIsNull() throws Exception {
        // when & then
        mvc.perform(post("/auth/register")
                        .content(getObjectMapper()
                                .writeValueAsString(
                                        new RegisterRequest("email", "username", "password", null, "surname", Set.of("role"))))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Error: Name is required.")));
    }

    @Test
    public void shouldReturn400WhenRegisterRequestParamNameIsEmpty() throws Exception {
        // when & then
        mvc.perform(post("/auth/register")
                        .content(getObjectMapper()
                                .writeValueAsString(
                                        new RegisterRequest("email", "username", "password", "", "surname", Set.of("role"))))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Error: Name is required.")));
    }

    @Test
    public void shouldReturn400WhenRegisterRequestParamSurnameIsNull() throws Exception {
        // when & then
        mvc.perform(post("/auth/register")
                        .content(getObjectMapper()
                                .writeValueAsString(
                                        new RegisterRequest("email", "username", "password", "name", null, Set.of("role"))))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Error: Surname is required.")));
    }

    @Test
    public void shouldReturn400WhenRegisterRequestParamSurnameIsEmpty() throws Exception {
        // when & then
        mvc.perform(post("/auth/register")
                        .content(getObjectMapper()
                                .writeValueAsString(
                                        new RegisterRequest("email", "username", "password", "name", "", Set.of("role"))))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Error: Surname is required.")));
    }

    @Test
    public void shouldReturn400WhenUsernameAlreadyExists() throws Exception {
        // given
        when(usersRepository.existsByUsername("username")).thenReturn(true);

        // when & then
        mvc.perform(post("/auth/register")
                        .content(getObjectMapper()
                                .writeValueAsString(
                                        new RegisterRequest("email", "username", "password", "name", "surname", Set.of("role"))))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Error: Username is already taken!")));
    }

    @Test
    public void shouldReturn400WhenEmailAlreadyExists() throws Exception {
        // given
        when(usersRepository.existsByEmail("email")).thenReturn(true);

        // when & then
        mvc.perform(post("/auth/register")
                        .content(getObjectMapper()
                                .writeValueAsString(
                                        new RegisterRequest("email", "username", "password", "name", "surname", Set.of("role"))))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Error: Email is already in use!")));
    }
}
