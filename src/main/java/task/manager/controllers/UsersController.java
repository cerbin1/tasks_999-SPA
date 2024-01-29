package task.manager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import task.manager.entity.Task;
import task.manager.entity.TasksRepository;
import task.manager.entity.User;
import task.manager.entity.UsersRepository;

import java.util.Optional;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/users")
public class UsersController {

    private final UsersRepository usersRepository;

    @Autowired
    public UsersController(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @GetMapping
    public ResponseEntity<?> getAllTasks() {
        Iterable<User> allUsers = usersRepository.findAll();
        return new ResponseEntity<>(allUsers, OK);
    }
}
