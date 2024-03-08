package task.manager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import task.manager.entity.Subtask;
import task.manager.entity.Task;
import task.manager.entity.TasksRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class TaskServiceImpl implements TaskService {

    private final TasksRepository tasksRepository;

    @Autowired
    public TaskServiceImpl(TasksRepository tasksRepository) {
        this.tasksRepository = tasksRepository;
    }

    @Override
    public Task createTaskWithSubtasks(Task task) {
        return saveTaskWithSubtasksInSequence(task);
    }

    @Override
    public Task updateTaskWithSubtasks(Task task) {
        return saveTaskWithSubtasksInSequence(task);
    }

    @Override
    public List<Task> getUserTasks(Long userId) {
        return StreamSupport.stream(tasksRepository.findAll().spliterator(), false)
                .filter(task -> task.getAssignee().getId().equals(userId))
                .collect(Collectors.toList());
    }

    private Task saveTaskWithSubtasksInSequence(Task task) {
        List<Subtask> subtasks = new ArrayList<>(task.getSubtasks());

        for (int i = 0; i < subtasks.size(); i++) {
            Subtask subtask = subtasks.get(i);
            subtask.setSequence((long) i);
        }
        return tasksRepository.save(task);
    }
}
