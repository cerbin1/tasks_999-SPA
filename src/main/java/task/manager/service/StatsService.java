package task.manager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import task.manager.entity.repository.TasksRepository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class StatsService {

    TasksRepository tasksRepository;

    @Autowired
    public StatsService(TasksRepository tasksRepository) {
        this.tasksRepository = tasksRepository;
    }

    public List<Object> getTasks() {

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
        Map<LocalDate, Long> dateCounts2 = new TreeMap<>();

        groupedByDate.keySet().forEach(localDate -> dateCounts2.put(localDate, groupedByDate.get(localDate)));

//        StreamSupport.stream(tasksRepository.findAll().spliterator(), false)
//                .map(task -> task.getDeadline().toLocalDate())
//                .collect(Collectors.groupingBy(localDate -> localDate.with(TemporalAdjusters.ofDateAdjuster())))


        // Sample map of LocalDate and Long
        Map<LocalDate, Long> dateCounts = new HashMap<>();
        dateCounts.put(LocalDate.of(2022, 8, 10), 5L);
        dateCounts.put(LocalDate.of(2022, 8, 5), 3L);
        dateCounts.put(LocalDate.of(2022, 8, 15), 7L);

        // Sort the map by date
        Map<LocalDate, Long> sortedByDate = dateCounts.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, HashMap::new));

        // Output the sorted map
        sortedByDate.forEach((date, count) ->
                System.out.println("Date: " + date + " Count: " + count));


//        Asd asd = new Asd(null, null);
        return null;
    }
}

record Asd(List<Integer> count, List<LocalDate> dates) {
}
