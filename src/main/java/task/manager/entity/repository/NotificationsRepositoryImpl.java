package task.manager.entity.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import task.manager.entity.Notification;
import task.manager.entity.Task;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class NotificationsRepositoryImpl implements NotificationsRepositoryCustom {

    @Autowired
    @Lazy
    private NotificationsRepository notificationsRepository;

    @Override
    public List<Notification> findByUserId(Long userId) {
        return notificationsRepository
                .findAll()
                .stream()
                .filter(notification -> notification.getUser().getId().equals(userId))
                .sorted(inactiveFirst())
                .collect(Collectors.toList());
    }

    private static Comparator<Notification> inactiveFirst() {
        return (o1, o2) -> Boolean.compare(o1.getRead(), o2.getRead());
    }

    @Override
    public Boolean existByUserId(Long userId) {
        return notificationsRepository.findAll().stream().anyMatch(notification -> notification.getId().equals(userId));
    }

    @Override
    public void markNotificationAsRead(Notification notification) {
        notification.setRead(true);
        notification.setReadDate(LocalDateTime.now());
        notificationsRepository.save(notification);
    }

    @Override
    public void createForTask(Task task) {
        Notification notification = new Notification("New Task", task, task.getAssignee(), LocalDateTime.now(), false, null);
        notificationsRepository.save(notification);
    }

    @Override
    public void createForMessage(Task task) {
        Notification notification = new Notification("New Message", task, task.getAssignee(), LocalDateTime.now(), false, null);
        notificationsRepository.save(notification);
    }
}
