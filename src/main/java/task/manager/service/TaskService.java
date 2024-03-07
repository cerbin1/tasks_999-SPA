package task.manager.service;

import task.manager.entity.Task;

public interface TaskService {

    Task createTaskWithSubtasks(Task task);
}
