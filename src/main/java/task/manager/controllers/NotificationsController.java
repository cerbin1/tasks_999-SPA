package task.manager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import task.manager.entity.Notification;
import task.manager.entity.NotificationsRepository;
import task.manager.entity.UsersRepository;

import java.util.Optional;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/notifications")
public class NotificationsController {

    private final NotificationsRepository notificationsRepository;
    private final UsersRepository usersRepository;

    @Autowired
    public NotificationsController(NotificationsRepository notificationsRepository, UsersRepository usersRepository) {
        this.notificationsRepository = notificationsRepository;
        this.usersRepository = usersRepository;
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

    @GetMapping("/count")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getUserNotificationsCount(@RequestParam Long userId) {
        if (usersRepository.existsById(userId)) {
            int notificationsCount = notificationsRepository
                    .findByUserId(userId)
                    .stream()
                    .filter(Notification::getRead)
                    .toList()
                    .size();
            return new ResponseEntity<>(notificationsCount, OK);
        } else {
            return new ResponseEntity<>(NOT_FOUND);
        }
    }
}
