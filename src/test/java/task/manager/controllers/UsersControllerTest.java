package task.manager.controllers;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import task.manager.entity.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static task.manager.utils.ObjectMapperInstance.getObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class UsersControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UsersRepository usersRepository;

    @Test
    public void shouldGetListOfUsers() throws Exception {
        // given
        when(usersRepository.findAll())
                .thenReturn(Arrays.asList(
                        new User(1L, "Mike Smith"),
                        new User(2L, "John Locker"))
                );

        // when & then
        mvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Mike Smith")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("John Locker")));
    }
}
