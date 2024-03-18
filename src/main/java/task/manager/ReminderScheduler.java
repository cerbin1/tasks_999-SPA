package task.manager;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import task.manager.entity.TaskReminder;
import task.manager.security.service.EmailSendingService;
import task.manager.service.TaskRemindersService;

import java.util.List;

@Component
public class ReminderScheduler {

    private final EmailSendingService emailSendingService;
    private final TaskRemindersService taskRemindersService;

    @Value("${application.front.url}")
    private String applicationFrontUrl;

    @Autowired
    public ReminderScheduler(EmailSendingService emailSendingService, TaskRemindersService taskRemindersService) {
        this.emailSendingService = emailSendingService;
        this.taskRemindersService = taskRemindersService;
    }

    @Scheduled(fixedDelay = 10000)
    public void sendEmailReminders() {
        List<TaskReminder> remindersToSend = taskRemindersService.getRemindersToSend();
        remindersToSend.forEach(taskReminder -> {
            String linkToTaskDetails = applicationFrontUrl + taskReminder.getTask().getId() + "/details";
            String emailContent = String.format("I would like to remind you that you have a task to accomplish. Task name: %s Link: %s",
                    taskReminder.getTask().getName(), linkToTaskDetails);
            emailSendingService.sendEmail("Task reminder", emailContent, taskReminder.getTask().getAssignee().getEmail());
            taskRemindersService.markReminderAsSent(taskReminder);
        });
    }
}