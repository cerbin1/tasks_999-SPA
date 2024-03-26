package task.manager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import task.manager.controllers.dto.StatisticDto;
import task.manager.controllers.dto.TasksCountForDateDto;
import task.manager.controllers.dto.TimeLoggedDto;
import task.manager.entity.Task;
import task.manager.entity.repository.NotificationsRepository;
import task.manager.entity.repository.SubtasksRepository;
import task.manager.entity.repository.TasksRepository;
import task.manager.entity.repository.UsersRepository;

import java.time.LocalDate;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.StreamSupport;

import static java.util.Map.Entry.comparingByKey;
import static java.util.stream.Collectors.*;

@Service
public class StatsService {

    private final TasksRepository tasksRepository;
    private final UsersRepository usersRepository;
    private final SubtasksRepository subtasksRepository;
    private final NotificationsRepository notificationsRepository;

    @Autowired
    public StatsService(TasksRepository tasksRepository, UsersRepository usersRepository,
                        SubtasksRepository subtasksRepository, NotificationsRepository notificationsRepository) {
        this.tasksRepository = tasksRepository;
        this.usersRepository = usersRepository;
        this.subtasksRepository = subtasksRepository;
        this.notificationsRepository = notificationsRepository;
    }

    public List<TasksCountForDateDto> getNumberOfTasks() {
        Map<LocalDate, Long> groupedByDate =
                StreamSupport.stream(tasksRepository.findAll().spliterator(), false)
                        .map(task -> task.getDeadline().toLocalDate())
                        .sorted()
                        .toList()
                        .stream()
                        .collect(groupingBy(date -> date, counting()))
                        .entrySet().stream()
                        .sorted(comparingByKey())
                        .collect(toMap(Entry::getKey, Entry::getValue, (oldValue, newValue) -> oldValue, HashMap::new));

        Map<LocalDate, Long> counts = new TreeMap<>();
        groupedByDate.keySet().forEach(localDate -> counts.put(localDate, groupedByDate.get(localDate)));

        Map<LocalDate, Long> sortedByDate = counts.entrySet().stream()
                .sorted(comparingByKey())
                .collect(toMap(Entry::getKey, Entry::getValue, (oldValue, newValue) -> oldValue, HashMap::new));

        List<TasksCountForDateDto> result = new ArrayList<>();
        sortedByDate.forEach((date, count) -> result.add(new TasksCountForDateDto(date, count)));

        return result;
    }

    public StatisticDto getStatistics() {
        long usersCount = usersRepository.count();
        long tasksCreated = tasksRepository.count();
        long tasksCompleted = StreamSupport
                .stream(tasksRepository.findAll().spliterator(), false)
                .filter(task -> task.getCompleted() != null)
                .count();
        long subtasksCreated = subtasksRepository.count();
        long notificationsCreated = notificationsRepository.count();
        return new StatisticDto(usersCount, tasksCreated, tasksCompleted, subtasksCreated, notificationsCreated);
    }

    public List<TimeLoggedDto> getTimeLoggedByUsers() {
        Map<String, List<Task>> tasksByUserName = getTasksByUserName();
        List<TimeLoggedDto> timeLoggedByUsersList = new ArrayList<>();
        tasksByUserName
                .keySet().forEach(
                        nextUsername -> {
                            AtomicReference<Long> sum = new AtomicReference<>(0L);
                            List<Task> tasks = tasksByUserName.get(nextUsername);
                            tasks.forEach(task -> task.getWorklogs().forEach(worklog -> sum.updateAndGet(v -> v + worklog.getMinutes())));
                            timeLoggedByUsersList.add(new TimeLoggedDto(nextUsername, sum.get()));
                        }
                );
        return timeLoggedByUsersList;
    }

    private Map<String, List<Task>> getTasksByUserName() {
        return StreamSupport.stream(tasksRepository.findAll().spliterator(), false)
                .filter(task -> task.getAssignee() != null)
                .collect(groupingBy(task -> task.getAssignee().getName()));
    }
}

