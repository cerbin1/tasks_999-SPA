package task.manager.entity.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import task.manager.entity.Priority;

@Repository
public interface PrioritiesRepository extends CrudRepository<Priority, Long> {
}
