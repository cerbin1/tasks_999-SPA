package task.manager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import task.manager.entity.Notification;
import task.manager.entity.NotificationsRepository;

import java.util.Optional;

import static org.springframework.http.HttpStatus.*;

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

    @PostMapping("/{notificationId}/read")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> markAsRead(@PathVariable Long notificationId) {
        Optional<Notification> maybeNotification = notificationsRepository.findById(notificationId);
        if (maybeNotification.isEmpty()) {
            return new ResponseEntity<>(NOT_FOUND);
        }

        Notification notification = maybeNotification.get();
        Boolean alreadyReadNotification = notification.getRead();
        if (alreadyReadNotification) {
            return new ResponseEntity<>(BAD_REQUEST);
        }
        notificationsRepository.markNotificationAsRead(notification);
        return new ResponseEntity<>(OK);
    }
}
