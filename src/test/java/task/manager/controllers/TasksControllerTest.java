package task.manager.controllers;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import task.manager.entity.Priority;
import task.manager.entity.Task;
import task.manager.entity.TasksRepository;
import task.manager.entity.User;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class TasksControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private TasksRepository tasksRepository;

    @Test
    public void shouldGetListOfTasks() throws Exception {
        // given
        when(tasksRepository.findAll())
                .thenReturn(Arrays.asList(
                        new Task(0L, "Mike", LocalDateTime.of(2024, 1, 1, 15, 15), new User(), new Priority()),
                        new Task(1L, "John", LocalDateTime.of(2024, 1, 15, 21, 30), new User(), new Priority()))
                );

        // when & then
        mvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(0)))
                .andExpect(jsonPath("$[0].name", is("Mike")))
                .andExpect(jsonPath("$[0].deadline", is("2024-01-01T15:15:00")))
                .andExpect(jsonPath("$[0].assignee", Matchers.anEmptyMap()))
                .andExpect(jsonPath("$[0].priority", Matchers.anEmptyMap()))
                .andExpect(jsonPath("$[1].id", is(1)))
                .andExpect(jsonPath("$[1].name", is("John")))
                .andExpect(jsonPath("$[1].deadline", is("2024-01-15T21:30:00")))
                .andExpect(jsonPath("$[1].assignee", Matchers.anEmptyMap()))
                .andExpect(jsonPath("$[1].priority", Matchers.anEmptyMap()));
    }
}
