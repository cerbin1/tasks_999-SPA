package task.manager.service;

import org.springframework.stereotype.Service;
import task.manager.entity.Task;

@Service
public interface TaskService {

    Task createTaskWithSubtasks(Task task);
}
