package task.manager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import task.manager.entity.Task;
import task.manager.entity.TasksRepository;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/tasks")
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
}
