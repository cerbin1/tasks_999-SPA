package task.manager.entity.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import task.manager.entity.Subtask;

@Repository
public interface SubtasksRepository extends CrudRepository<Subtask, Long> {
}
