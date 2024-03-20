package task.manager.entity.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import task.manager.entity.Worklog;

@Repository
public interface WorklogsRepository extends CrudRepository<Worklog, Long> {

}
