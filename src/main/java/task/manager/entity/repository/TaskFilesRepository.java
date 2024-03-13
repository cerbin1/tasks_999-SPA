package task.manager.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import task.manager.entity.TaskFile;

@Repository
public interface TaskFilesRepository extends JpaRepository<TaskFile, String> {
}
