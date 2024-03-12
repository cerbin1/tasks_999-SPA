package task.manager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import task.manager.entity.TaskReminder;
import task.manager.entity.repository.TaskRemindersRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.StreamSupport;

@Service
public class TaskRemindersServiceImpl implements TaskRemindersService {

    private final TaskRemindersRepository taskRemindersRepository;

    @Autowired
    public TaskRemindersServiceImpl(TaskRemindersRepository taskRemindersRepository) {
        this.taskRemindersRepository = taskRemindersRepository;
    }

    @Override
    public List<TaskReminder> getRemindersToSend() {
        return StreamSupport.stream(taskRemindersRepository.findAll().spliterator(), false)
                .filter(taskReminder -> !taskReminder.getSent())
                .filter(taskReminder -> taskReminder.getPlannedSendDate().isBefore(LocalDateTime.now()))
                .toList();
    }

    @Override
    public void markReminderAsSent(TaskReminder taskReminder) {
        taskReminder.setSent(true);
        taskReminder.setSentDate(LocalDateTime.now());
        taskRemindersRepository.save(taskReminder);
    }
}
