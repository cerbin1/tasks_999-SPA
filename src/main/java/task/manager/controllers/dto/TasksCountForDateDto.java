package task.manager.controllers.dto;

import java.time.LocalDate;

public record TasksCountForDateDto(LocalDate date, Long count) {

}
