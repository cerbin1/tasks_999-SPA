package task.manager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import task.manager.entity.Notification;
import task.manager.entity.NotificationsRepository;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/notifications")
public class NotificationsController {

    private final NotificationsRepository notificationsRepository;

    @Autowired
    public NotificationsController(NotificationsRepository notificationsRepository) {
        this.notificationsRepository = notificationsRepository;
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getNotificationsForUserId(@RequestParam Long userId) {
        if (notificationsRepository.existByUserId(userId)) {
            Iterable<Notification> allNotifications = notificationsRepository.findByUserId(userId);
            return new ResponseEntity<>(allNotifications, OK);
        } else {
            return new ResponseEntity<>(NOT_FOUND);
        }
    }
}
