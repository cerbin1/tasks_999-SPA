package task.manager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import task.manager.entity.User;
import task.manager.entity.UsersRepository;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/users")
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
