package task.manager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import task.manager.entity.PrioritiesRepository;
import task.manager.entity.Priority;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/priorities")
public class PrioritiesController {

    private final PrioritiesRepository prioritiesRepository;

    @Autowired
    public PrioritiesController(PrioritiesRepository prioritiesRepository) {
        this.prioritiesRepository = prioritiesRepository;
    }

    @GetMapping
    public ResponseEntity<?> getAllPriorities() {
        Iterable<Priority> allPriorities = prioritiesRepository.findAll();
        return new ResponseEntity<>(allPriorities, OK);
    }
}
