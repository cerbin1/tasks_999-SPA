package task.manager.controllers.dto;

public record StatisticDto(Long usersCount,
                           Long tasksCreated,
                           Long tasksCompleted,
                           Long subtasksCount,
                           Long notificationsCount) {
}
