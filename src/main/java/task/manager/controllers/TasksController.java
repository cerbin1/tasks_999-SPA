package task.manager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import task.manager.entity.NotificationsRepository;
import task.manager.entity.Task;
import task.manager.entity.TasksRepository;
import task.manager.security.jwt.MessageResponse;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/tasks")
public class TasksController {

    private final TasksRepository tasksRepository;
    private final NotificationsRepository notificationsRepository;

    @Autowired
    public TasksController(TasksRepository tasksRepository, NotificationsRepository notificationsRepository) {
        this.tasksRepository = tasksRepository;
        this.notificationsRepository = notificationsRepository;
    }

    @GetMapping
    public ResponseEntity<?> getAllTasks() {
        Iterable<Task> allTasks = tasksRepository.findAll();
        return new ResponseEntity<>(allTasks, OK);
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
        Task taskCreated = tasksRepository.save(task);
        notificationsRepository.createForTask(task);
        return new ResponseEntity<>(taskCreated, CREATED);
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
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Deadline can have to be future time."));
        }
        if (tasksRepository.existsById(id)) {
            Task taskUpdated = tasksRepository.save(task);
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

    @GetMapping("/search")
    public ResponseEntity<?> getTasksForSearch(@RequestParam String value) {
        List<Task> foundTasks = new ArrayList<>();
        tasksRepository.findAll().forEach(task -> {
            if (task.getName().contains(value)) {
                foundTasks.add(task);
            }
        });
        return new ResponseEntity<>(foundTasks, OK);
    }
}
