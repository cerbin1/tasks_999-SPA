package task.manager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import task.manager.entity.Notification;
import task.manager.entity.repository.NotificationsRepository;

import java.util.List;

@Service
public class NotificationsServiceImpl implements NotificationsService {

    private final NotificationsRepository notificationsRepository;

    @Autowired
    public NotificationsServiceImpl(NotificationsRepository notificationsRepository) {
        this.notificationsRepository = notificationsRepository;
    }

    @Override
    public void markAllUserNotificationsAsRead(Long userId) {
        List<Notification> userNotifications = notificationsRepository.findByUserId(userId);
        for (Notification notification : userNotifications) {
            if (!notification.getRead()) {
                notificationsRepository.markNotificationAsRead(notification);
            }
        }
    }
}
