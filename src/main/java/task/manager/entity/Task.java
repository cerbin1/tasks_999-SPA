package task.manager.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity(name = "tasks")
@AllArgsConstructor
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

    @OneToOne
    @JoinColumn(name = "ASSIGNEE_ID", nullable = false)
    private User assignee;

    @OneToOne
    @JoinColumn(name = "PRIORITY_ID", nullable = false)
    private Priority priority;
}
