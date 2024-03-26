package task.manager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import task.manager.entity.repository.TasksRepository;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class StatsService {

    TasksRepository tasksRepository;

    @Autowired
    public StatsService(TasksRepository tasksRepository) {
        this.tasksRepository = tasksRepository;
    }

    public List<XY> getTasks() {

        Map<LocalDate, Long> groupedByDate =
                StreamSupport.stream(tasksRepository.findAll().spliterator(), false)
                        .map(task -> task.getDeadline().toLocalDate())
                        .sorted()
                        .toList()
                        .stream()
                        .collect(Collectors.groupingBy(date -> date, Collectors.counting()))
                        .entrySet().stream()
                        .sorted(Map.Entry.comparingByKey())
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                                (oldValue, newValue) -> oldValue, HashMap::new));
        Map<LocalDate, Long> dateCounts = new TreeMap<>();

        groupedByDate.keySet().forEach(localDate -> dateCounts.put(localDate, groupedByDate.get(localDate)));

//        StreamSupport.stream(tasksRepository.findAll().spliterator(), false)
//                .map(task -> task.getDeadline().toLocalDate())
//                .collect(Collectors.groupingBy(localDate -> localDate.with(TemporalAdjusters.ofDateAdjuster())))


        Map<LocalDate, Long> sortedByDate = dateCounts.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, HashMap::new));
        List<XY> list = new ArrayList<>();

        sortedByDate.forEach((date, count) ->
                list.add(new XY(date, count)));

        sortedByDate.forEach((date, count) ->
                System.out.println("Date: " + date + " Count: " + count));


        return list;
    }
}

