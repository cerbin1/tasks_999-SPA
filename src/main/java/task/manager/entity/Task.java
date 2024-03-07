package task.manager.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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

    @ManyToOne
    @JoinColumn(name = "ASSIGNEE_ID", referencedColumnName= "id")
    private User assignee;

    @ManyToOne
    @JoinColumn(name = "PRIORITY_ID", referencedColumnName= "id")
    private Priority priority;
}
