package task.manager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import task.manager.entity.Subtask;
import task.manager.entity.Task;
import task.manager.entity.TaskReminder;
import task.manager.entity.Worklog;
import task.manager.entity.repository.TasksRepository;
import task.manager.entity.repository.UsersRepository;
import task.manager.utils.AuthenticationUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class TaskServiceImpl implements TaskService {

    private final TasksRepository tasksRepository;
    private final UsersRepository usersRepository;

    @Autowired
    public TaskServiceImpl(TasksRepository tasksRepository, UsersRepository usersRepository) {
        this.tasksRepository = tasksRepository;
        this.usersRepository = usersRepository;
    }

    @Override
    public Task createTaskWithSubtasks(Task task) {
        Long loggedUserId = AuthenticationUtils.getLoggedUserId();
        task.setCreator(usersRepository.findById(loggedUserId).orElseThrow());
        TaskReminder taskReminder = new TaskReminder();
        taskReminder.setPlannedSendDate(task.getDeadline().minusHours(1));
        task.setTaskReminder(taskReminder);
        taskReminder.setTask(task);
        return saveTaskWithSubtasksInSequence(task);
    }

    @Override
    public Task updateTaskWithSubtasks(Task task) {
        return saveTaskWithSubtasksInSequence(task);
    }

    @Override
    public List<Task> getUserAssignedOrCreatedTasks(Long userId) {
        return StreamSupport.stream(tasksRepository.findAll().spliterator(), false)
                .filter(task -> task.getAssignee().getId().equals(userId) || task.getCreator().getId().equals(userId))
                .collect(Collectors.toList());
    }

    @Override
    public void appendWorklogToTask(Worklog worklog, Task task) {
        Long loggedUserId = AuthenticationUtils.getLoggedUserId();
        worklog.setCreator(usersRepository.findById(loggedUserId).orElseThrow());
        worklog.setModificationDate(LocalDateTime.now());
        deletePreviousWorklogIfExistToUpdateItsValue(worklog, task);
        task.getWorklogs().add(worklog);
        tasksRepository.save(task);
    }

    private void deletePreviousWorklogIfExistToUpdateItsValue(Worklog worklog, Task task) {
        task.getWorklogs()
                .stream()
                .filter(nextWorklog -> nextWorklog.getId().equals(worklog.getId()))
                .findFirst()
                .ifPresent(value -> task.getWorklogs().remove(value));
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
