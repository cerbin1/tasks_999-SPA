package task.manager.service;

import task.manager.entity.Task;

import java.util.List;

public interface TaskService {

    Task createTaskWithSubtasks(Task task);

    Task updateTaskWithSubtasks(Task task);

    List<Task> getUserAssignedOrCreatedTasks(Long userId);
}
