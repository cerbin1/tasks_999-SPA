package task.manager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import task.manager.entity.Priority;
import task.manager.entity.repository.PrioritiesRepository;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/priorities")
public class PrioritiesController {

    private final PrioritiesRepository prioritiesRepository;

    @Autowired
    public PrioritiesController(PrioritiesRepository prioritiesRepository) {
        this.prioritiesRepository = prioritiesRepository;
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getAllPriorities() {
        Iterable<Priority> allPriorities = prioritiesRepository.findAll();
        return new ResponseEntity<>(allPriorities, OK);
    }
}
