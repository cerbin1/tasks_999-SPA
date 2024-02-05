package task.manager.entity;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public class NotificationsRepositoryImpl implements NotificationsRepositoryCustom {

    @Autowired
    private NotificationsRepository notificationsRepository;

    @Override
    public List<Notification> findByUserId(Long userId) {
        return notificationsRepository.findAll().stream().filter(notification -> notification.getId().equals(userId)).collect(Collectors.toList());
    }

    @Override
    public Boolean existByUserId(Long userId) {
        return notificationsRepository.findAll().stream().anyMatch(notification -> notification.getId().equals(userId));
    }
}
