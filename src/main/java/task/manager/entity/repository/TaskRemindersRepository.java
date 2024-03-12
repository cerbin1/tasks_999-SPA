package task.manager.entity.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import task.manager.entity.TaskReminder;

@Repository
public interface TaskRemindersRepository extends CrudRepository<TaskReminder, Long> {
}
