package task.manager.controllers;

public record StatisticDto(Long usersCount,
                           Long tasksCreated,
                           Long tasksCompleted,
                           Long subtasksCount,
                           Long notificationsCount) {
}
