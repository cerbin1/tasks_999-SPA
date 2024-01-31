package task.manager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import task.manager.entity.Task;
import task.manager.entity.TasksRepository;

import java.util.Optional;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/tasks")
public class TasksController {

    private final TasksRepository tasksRepository;

    @Autowired
    public TasksController(TasksRepository tasksRepository) {
        this.tasksRepository = tasksRepository;
    }

    @GetMapping
    public ResponseEntity<?> getAllTasks() {
        Iterable<Task> allTasks = tasksRepository.findAll();
        return new ResponseEntity<>(allTasks, OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTaskBy(@PathVariable Long id) {
        Optional<Task> task = tasksRepository.findById(id);
        if (task.isPresent()) {
            return new ResponseEntity<>(task.get(), OK);
        }
        return new ResponseEntity<>(NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Task task) {
        Task taskCreated = tasksRepository.save(task);
        return new ResponseEntity<>(taskCreated, CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTaskById(@PathVariable Long id, @RequestBody Task task) {
        if (tasksRepository.existsById(id)) {
            Task taskUpdated = tasksRepository.save(task);
            return new ResponseEntity<>(taskUpdated, OK);
        }
        return new ResponseEntity<>(NOT_FOUND);
    }


    @DeleteMapping("/{id}")
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

}
