package task.manager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import task.manager.entity.*;
import task.manager.entity.repository.NotificationsRepository;
import task.manager.entity.repository.TasksRepository;
import task.manager.entity.repository.WorklogsRepository;
import task.manager.security.jwt.MessageResponse;
import task.manager.service.TaskService;
import task.manager.utils.AuthenticationUtils;
import task.manager.utils.DateUtils;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/tasks")
public class TasksController {

    private final TasksRepository tasksRepository;
    private final TaskService taskService;
    private final NotificationsRepository notificationsRepository;
    private final WorklogsRepository worklogsRepository;

    @Autowired
    public TasksController(TasksRepository tasksRepository, TaskService taskService, NotificationsRepository notificationsRepository, WorklogsRepository worklogsRepository) {
        this.tasksRepository = tasksRepository;
        this.taskService = taskService;
        this.notificationsRepository = notificationsRepository;
        this.worklogsRepository = worklogsRepository;
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getAllTasks() {
        Iterable<Task> allTasks = tasksRepository.findAll();
        return new ResponseEntity<>(allTasks, OK);
    }

    @GetMapping("/userTasks")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getUserTasks() {
        Long loggedUserId = AuthenticationUtils.getLoggedUserId();
        Iterable<Task> userTasks = taskService.getUserAssignedOrCreatedTasks(loggedUserId);
        return new ResponseEntity<>(userTasks, OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getTaskBy(@PathVariable Long id) {
        Optional<Task> task = tasksRepository.findById(id);
        if (task.isPresent()) {
            return new ResponseEntity<>(task.get(), OK);
        }
        return new ResponseEntity<>(NOT_FOUND);
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> create(@RequestBody Task task) {
        if (task.getName().isBlank()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Name is required."));
        }
        LocalDateTime deadline = task.getDeadline();
        if (deadline == null) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Deadline is required."));
        }
        if (deadline.isBefore(LocalDateTime.now())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Deadline can have to be future time."));
        }
        if (task.getCategory() == TaskCategory.NO_CATEGORY) {
            ResponseEntity.badRequest().body(new MessageResponse("Error: Category have to be changed."));
        }
        Task taskCreated = taskService.createTaskWithSubtasks(task);
        notificationsRepository.createForTask(taskCreated);
        createGoogleCalendarEventFrom(task);
        return new ResponseEntity<>(taskCreated, CREATED);
    }

    private void createGoogleCalendarEventFrom(Task task) {
        GoogleCalendarIntegration googleCalendarIntegration = new GoogleCalendarIntegration();
        try {
            StringBuilder result = new StringBuilder();
            List<String> collect = task.getSubtasks().stream().map(Subtask::getName).toList();
            for (String s : collect) {
                result.append("<li>").append(s).append("</li>");
            }
            googleCalendarIntegration.createGoogleCalendarEvent(task.getName(), result.toString(), DateUtils.localDateTimeToDate(task.getDeadline()));
        } catch (IOException | GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> updateTaskById(@PathVariable Long id, @RequestBody Task task) {
        if (task.getName().isBlank()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Name is required."));
        }
        LocalDateTime deadline = task.getDeadline();
        if (deadline == null) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Deadline is required."));
        }
        if (deadline.isBefore(LocalDateTime.now())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Deadline have to be future time."));
        }
        if (task.getCategory() == TaskCategory.NO_CATEGORY) {
            ResponseEntity.badRequest().body(new MessageResponse("Error: Category have to be changed."));
        }
        if (tasksRepository.existsById(id)) {
            Task taskUpdated = taskService.updateTaskWithSubtasks(task);
            return new ResponseEntity<>(taskUpdated, OK);
        }
        return new ResponseEntity<>(NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> deleteBy(@PathVariable Long id) {
        if (tasksRepository.existsById(id)) {
            Optional<Task> taskToDelete = tasksRepository.findById(id);
            if (taskToDelete.isPresent()) {
                tasksRepository.delete(taskToDelete.get());
                return new ResponseEntity<>(OK);
            }
            return new ResponseEntity<>(BAD_REQUEST);
        }
        return new ResponseEntity<>(NOT_FOUND);
    }

    @GetMapping("/searchByName")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> searchTasksByName(@RequestParam(name = "value") String name) {
        List<Task> foundTasks = new ArrayList<>();
        tasksRepository.findAll().forEach(task -> {
            if (task.getName().contains(name)) {
                foundTasks.add(task);
            }
        });
        return new ResponseEntity<>(foundTasks, OK);
    }

    @GetMapping("/searchByCategory")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> searchTasksByCategory(@RequestParam(name = "value") TaskCategory category) {
        List<Task> foundTasks = new ArrayList<>();
        tasksRepository.findAll().forEach(task -> {
            if (task.getCategory() == category) {
                foundTasks.add(task);
            }
        });
        return new ResponseEntity<>(foundTasks, OK);
    }

    @GetMapping("/searchByLabel")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> searchTasksByLabel(@RequestParam(name = "label") String label) {
        List<Task> foundTasks = new ArrayList<>();
        tasksRepository.findAll().forEach(task -> {
            if (task.getLabels().contains(label)) {
                foundTasks.add(task);
            }
        });
        return new ResponseEntity<>(foundTasks, OK);
    }

    @PutMapping("/markAsCompleted")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> markTaskAsCompleted(@RequestParam Long taskId) {
        if (tasksRepository.existsById(taskId)) {
            Optional<Task> maybeTask = tasksRepository.findById(taskId);
            if (maybeTask.isPresent()) {
                Task task = maybeTask.get();
                task.markAsCompleted();
                tasksRepository.save(task);
                return new ResponseEntity<>(OK);
            }
            return new ResponseEntity<>(BAD_REQUEST);
        }
        return new ResponseEntity<>(NOT_FOUND);
    }

    @GetMapping("/categories")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getAllCategories() {
        return new ResponseEntity<>(TaskCategory.values(), OK);
    }

    @PutMapping("/worklog")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> createWorklog(@RequestParam Long taskId, @RequestBody Worklog worklog) {
        if (tasksRepository.existsById(taskId)) {
            Optional<Task> maybeTask = tasksRepository.findById(taskId);
            if (maybeTask.isPresent()) {
                Task task = maybeTask.get();
                if (worklog.getDate() == null) {
                    return ResponseEntity.badRequest().body(new MessageResponse("Error: Date is required."));
                }
                if (worklog.getMinutes() == null) {
                    return ResponseEntity.badRequest().body(new MessageResponse("Error: Minutes are required."));
                }
                taskService.appendWorklogToTask(worklog, task);
                return new ResponseEntity<>(OK);
            }
            return new ResponseEntity<>(BAD_REQUEST);
        }
        return new ResponseEntity<>(NOT_FOUND);
    }

    @DeleteMapping("/worklog/{worklogId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> deleteWorklogById(@PathVariable Long worklogId) {
        if (worklogsRepository.existsById(worklogId)) {
            Optional<Worklog> maybeWorklog = worklogsRepository.findById(worklogId);
            if (maybeWorklog.isPresent()) {
                Worklog worklog = maybeWorklog.get();
                worklogsRepository.delete(worklog);
                return new ResponseEntity<>(OK);
            }
            return new ResponseEntity<>(BAD_REQUEST);
        }
        return new ResponseEntity<>(NOT_FOUND);
    }
}
