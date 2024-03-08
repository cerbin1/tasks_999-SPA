package task.manager.entity.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import task.manager.entity.Task;

@Repository
public interface TasksRepository extends CrudRepository<Task, Long> {
}
