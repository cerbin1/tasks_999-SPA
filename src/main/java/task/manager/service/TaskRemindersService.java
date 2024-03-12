package task.manager.service;

import task.manager.entity.TaskReminder;

import java.util.List;

public interface TaskRemindersService {

    List<TaskReminder> getRemindersToSend();

    void markReminderAsSent(TaskReminder taskReminder);
}
