package task.manager.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import task.manager.entity.Role;
import task.manager.entity.RoleName;
import task.manager.entity.User;
import task.manager.entity.UsersRepository;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@WithMockUser(roles = "ADMIN")
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
                        new User(1L, "msmith", "mike@smith.com", "password", "Mike", "Smith", Set.of(new Role(RoleName.ROLE_USER))),
                        new User(2L, "jlocker", "john@locker.com", "password", "John", "Locker", Set.of(new Role(RoleName.ROLE_USER))))
                );

        // when & then
        mvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].username", is("msmith")))
                .andExpect(jsonPath("$[0].email", is("mike@smith.com")))
                .andExpect(jsonPath("$[0].password", is("password")))
                .andExpect(jsonPath("$[0].name", is("Mike")))
                .andExpect(jsonPath("$[0].surname", is("Smith")))
                .andExpect(jsonPath("$[0].roles[0].name", is("ROLE_USER")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].username", is("jlocker")))
                .andExpect(jsonPath("$[1].email", is("john@locker.com")))
                .andExpect(jsonPath("$[1].password", is("password")))
                .andExpect(jsonPath("$[1].name", is("John")))
                .andExpect(jsonPath("$[1].surname", is("Locker")))
                .andExpect(jsonPath("$[1].roles[0].name", is("ROLE_USER")));
    }

    @Test
    public void shouldDeleteUser() throws Exception {
        // given
        User user = new User("username", "email","password", "name", "surname");
        when(usersRepository.existsById(1L))
                .thenReturn(true);
        when(usersRepository.findById(1L))
                .thenReturn(Optional.of(user));

        // when & then
        mvc.perform(delete("/api/users/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturn400WhenTryingDeleteNotExistingUser() throws Exception {
        // given
        when(usersRepository.existsById(1L))
                .thenReturn(true);
        when(usersRepository.findById(1L))
                .thenReturn(Optional.empty());

        // when & then
        mvc.perform(delete("/api/users/1"))
                .andExpect(status().isBadRequest());
    }
}
