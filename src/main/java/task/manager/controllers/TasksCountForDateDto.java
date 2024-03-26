package task.manager.controllers;

import java.time.LocalDate;

public record TasksCountForDateDto(LocalDate date, Long count) {

}
