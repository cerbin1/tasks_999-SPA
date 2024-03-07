package task.manager.entity;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubtasksRepository extends CrudRepository<Subtask, Long> {
}
