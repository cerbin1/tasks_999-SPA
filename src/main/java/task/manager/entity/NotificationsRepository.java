package task.manager.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationsRepository extends JpaRepository<Notification, Long>, NotificationsRepositoryCustom {
    List<Notification> findByUserId(Long userId);

    Boolean existByUserId(Long userId);
}
