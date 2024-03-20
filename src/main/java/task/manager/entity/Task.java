package task.manager.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "tasks")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private LocalDateTime deadline;

    private LocalDateTime completeDate;

    private Boolean completed = false;

    @ManyToOne
    @JoinColumn(name = "CREATOR_ID", referencedColumnName = "id")
    @Setter
    private User creator;

    @ManyToOne
    @JoinColumn(name = "assignee_id", referencedColumnName = "id")
    private User assignee;

    @ManyToOne
    @JoinColumn(name = "priority_id", referencedColumnName = "id")
    private Priority priority;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "task_id")
    private List<Subtask> subtasks;

    @OneToMany(mappedBy = "task")
    private List<ChatMessage> messages;

    @OneToOne(mappedBy = "task", cascade = CascadeType.ALL)
    @Setter
    @JsonIgnore
    private TaskReminder taskReminder;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "task_id")
    @Setter
    private List<TaskFile> taskFiles;

    @Enumerated(EnumType.STRING)
    private TaskCategory category;

    @ElementCollection
    private List<String> labels;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "task_id")

    private List<Worklog> worklogs;

    public void markAsCompleted() {
        this.completed = true;
        this.completeDate = LocalDateTime.now();
    }
}
