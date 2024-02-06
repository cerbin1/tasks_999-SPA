package task.manager.controllers;

import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import task.manager.entity.*;

import java.time.LocalDate;
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
@WithMockUser
public class TasksControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private TasksRepository tasksRepository;

    @MockBean
    private NotificationsRepository notificationsRepository;

    @Test
    public void shouldGetListOfTasks() throws Exception {
        // given
        when(tasksRepository.findAll())
                .thenReturn(Arrays.asList(
                        new Task(0L, "Mike", LocalDateTime.of(2024, 1, 1, 15, 15), null, null),
                        new Task(1L, "John", LocalDateTime.of(2024, 1, 15, 21, 30), null, null))
                );

        // when & then
        mvc.perform(get("/api/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(0)))
                .andExpect(jsonPath("$[0].name", is("Mike")))
                .andExpect(jsonPath("$[0].deadline", is("2024-01-01T15:15:00")))
                .andExpect(jsonPath("$[0].assignee").value(IsNull.nullValue()))
                .andExpect(jsonPath("$[0].priority").value(IsNull.nullValue()))
                .andExpect(jsonPath("$[1].id", is(1)))
                .andExpect(jsonPath("$[1].name", is("John")))
                .andExpect(jsonPath("$[1].deadline", is("2024-01-15T21:30:00")))
                .andExpect(jsonPath("$[1].assignee").value(IsNull.nullValue()))
                .andExpect(jsonPath("$[1].priority").value(IsNull.nullValue()));
    }

    @Test
    public void shouldReturn400WhenTaskWithGivenIdNotFound() throws Exception {
        // when & then
        mvc.perform(get("/tasks/123"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnTaskById() throws Exception {
        // given
        when(tasksRepository.findById(1L))
                .thenReturn(Optional.of(new Task(1L, "Mike", LocalDateTime.of(2024, 1, 1, 15, 15), null, null)));

        // when & then
        mvc.perform(get("/api/tasks/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Mike")))
                .andExpect(jsonPath("$.deadline", is("2024-01-01T15:15:00")))
                .andExpect(jsonPath("$.assignee").value(IsNull.nullValue()))
                .andExpect(jsonPath("$.priority").value(IsNull.nullValue()));
    }

    @Test
    public void shouldReturn400WhenTaskToUpdateWithGivenIdNotFound() throws Exception {
        // when & then
        mvc.perform(post("/api/tasks"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldCreateTask() throws Exception {
        // given
        Task task = new Task(1L, "name", LocalDateTime.now().plusDays(1), new User(), new Priority());

        // when & then
        mvc.perform(post("/api/tasks")
                        .content(getObjectMapper().writeValueAsString(task))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldReturn400WhenTaskNameToCreateIsEmpty() throws Exception {
        // given
        Task task = new Task(1L, "", LocalDateTime.of(2024, 1, 1, 15, 15), new User(), new Priority());

        // when & then
        mvc.perform(post("/api/tasks")
                        .content(getObjectMapper().writeValueAsString(task))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Error: Name is required.")));
    }

    @Test
    public void shouldReturn400WhenTaskDeadlineToCreateIsNull() throws Exception {
        // given
        Task task = new Task(1L, "name", null, new User(), new Priority());

        // when & then
        mvc.perform(post("/api/tasks")
                        .content(getObjectMapper().writeValueAsString(task))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Error: Deadline is required.")));
    }

    @Test
    public void shouldReturn400WhenTaskDeadlineToCreateIsPastNow() throws Exception {
        // given
        Task task = new Task(1L, "name", LocalDate.of(2020, 1, 1).atStartOfDay(), new User(), new Priority());

        // when & then
        mvc.perform(post("/api/tasks")
                        .content(getObjectMapper().writeValueAsString(task))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Error: Deadline can have to be future time.")));
    }

    @Test
    public void shouldReturn400WhenThereIsNoTaskObjectInRequestWhenUpdatingTask() throws Exception {
        // given
        Task task = new Task(1L, "Mike", LocalDateTime.now().plusDays(1L), new User(), new Priority());
        when(tasksRepository.existsById(123L))
                .thenReturn(false);

        // when & then
        mvc.perform(put("/api/tasks/123")
                        .content(getObjectMapper().writeValueAsString(task))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldUpdateTask() throws Exception {
        // given
        Task task = new Task(1L, "Mike", LocalDateTime.now().plusDays(1L), null, null);
        Task taskToUpdate = new Task(1L, "John", LocalDateTime.of(2024, 1, 10, 10, 10), null, null);
        when(tasksRepository.existsById(1L))
                .thenReturn(true);
        when(tasksRepository.save(ArgumentMatchers.any()))
                .thenReturn(taskToUpdate);

        // when & then
        mvc.perform(put("/api/tasks/1")
                        .content(getObjectMapper().writeValueAsString(task))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("John")))
                .andExpect(jsonPath("$.deadline", is("2024-01-10T10:10:00")))
                .andExpect(jsonPath("$.assignee").value(IsNull.nullValue()))
                .andExpect(jsonPath("$.priority").value(IsNull.nullValue()));
    }

    @Test
    public void shouldReturn400WhenTaskNameToUpdateIsEmpty() throws Exception {
        // given
        Task task = new Task(1L, "", LocalDateTime.of(2024, 1, 1, 15, 15), new User(), new Priority());
        when(tasksRepository.existsById(1L)).thenReturn(true);

        // when & then
        mvc.perform(put("/api/tasks/1")
                        .content(getObjectMapper().writeValueAsString(task))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Error: Name is required.")));
    }

    @Test
    public void shouldReturn400WhenTaskDeadlineToUpdateIsNull() throws Exception {
        // given
        Task task = new Task(1L, "name", null, new User(), new Priority());
        when(tasksRepository.existsById(1L)).thenReturn(true);

        // when & then
        mvc.perform(put("/api/tasks/1")
                        .content(getObjectMapper().writeValueAsString(task))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Error: Deadline is required.")));
    }

    @Test
    public void shouldReturn400WhenTaskDeadlineToUpdateIsPastNow() throws Exception {
        // given
        Task task = new Task(1L, "name", LocalDate.of(2020, 1, 1).atStartOfDay(), new User(), new Priority());
        when(tasksRepository.existsById(1L)).thenReturn(true);

        // when & then
        mvc.perform(put("/api/tasks/1")
                        .content(getObjectMapper().writeValueAsString(task))
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Error: Deadline can have to be future time.")));
    }

    @Test
    public void shouldDeleteTask() throws Exception {
        // given
        Task task = new Task(1L, "Mike", LocalDateTime.of(2024, 1, 1, 15, 15), new User(), new Priority());
        when(tasksRepository.existsById(1L))
                .thenReturn(true);
        when(tasksRepository.findById(1L))
                .thenReturn(Optional.of(task));

        // when & then
        mvc.perform(delete("/api/tasks/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturn400WhenTryingDeleteNotExistingTask() throws Exception {
        // given
        when(tasksRepository.existsById(1L))
                .thenReturn(true);
        when(tasksRepository.findById(1L))
                .thenReturn(Optional.empty());

        // when & then
        mvc.perform(delete("/api/tasks/1"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturn404WhenTaskByIdToDeleteNotExists() throws Exception {
        // given
        when(tasksRepository.existsById(1L))
                .thenReturn(false);

        // when & then
        mvc.perform(delete("/api/tasks/1"))
                .andExpect(status().isNotFound());
    }
}
