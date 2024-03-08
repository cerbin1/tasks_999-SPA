package task.manager.entity.repository;

import task.manager.entity.Notification;
import task.manager.entity.Task;

import java.util.List;

public interface NotificationsRepositoryCustom {

    List<Notification> findByUserId(Long userId);

    Boolean existByUserId(Long userId);

    void markNotificationAsRead(Notification notification);

    void createForTask(Task task);
}
