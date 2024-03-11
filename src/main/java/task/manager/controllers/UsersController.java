package task.manager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import task.manager.entity.User;
import task.manager.entity.repository.TasksRepository;
import task.manager.entity.repository.UsersRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/users")
public class UsersController {

    private final UsersRepository usersRepository;

    @Autowired
    public UsersController(UsersRepository usersRepository, TasksRepository tasksRepository) {
        this.usersRepository = usersRepository;
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllUsers() {
        List<UserForAdminPanel> users = StreamSupport
                .stream(usersRepository.findAll().spliterator(), false)
                .map(user -> new UserForAdminPanel(user.getId(), user.getEmail(), user.getUsername(), user.getName(), user.getSurname(), user.getActive(), usersRepository.getUserMessagesCount(user.getId())))
                .toList();
        return new ResponseEntity<>(users, OK);
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getUsersForTask() {
        Iterable<User> allUsers = usersRepository.findAll();
        List<UserForTask> usersForTask = new ArrayList<>();
        allUsers.forEach(user -> usersForTask.add(new UserForTask(user.getId(), user.getName(), user.getSurname())));
        return new ResponseEntity<>(usersForTask, OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteBy(@PathVariable Long id) {
        if (usersRepository.existsById(id)) {
            Optional<User> userToDelete = usersRepository.findById(id);
            if (userToDelete.isPresent()) {
                usersRepository.delete(userToDelete.get());
                return new ResponseEntity<>(OK);
            }
            return new ResponseEntity<>(BAD_REQUEST);
        }
        return new ResponseEntity<>(NOT_FOUND);
    }

    record UserForAdminPanel(Long id, String email, String username, String name, String surname, Boolean active,
                             Long messagesCount) {
    }

    record UserForTask(Long id, String name, String surname) {
    }
}
