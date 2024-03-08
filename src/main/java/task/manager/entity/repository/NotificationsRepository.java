package task.manager.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import task.manager.entity.Notification;

@Repository
public interface NotificationsRepository extends JpaRepository<Notification, Long>, NotificationsRepositoryCustom {
}
