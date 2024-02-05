package task.manager.entity;

import java.util.List;

public interface NotificationsRepositoryCustom {

    List<Notification> findByUserId(Long userId);

    Boolean existByUserId(Long userId);
}
