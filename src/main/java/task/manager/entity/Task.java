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
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private LocalDateTime deadline;

    @Column
    private LocalDateTime completeDate;

    @Column
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

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "task_id")
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

    @Column
    @ElementCollection
    private List<String> labels;

    public void markAsCompleted() {
        this.completed = true;
        this.completeDate = LocalDateTime.now();
    }
}
