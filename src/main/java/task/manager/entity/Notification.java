package task.manager.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private LocalDateTime createDate;

    @Setter
    private Boolean read;

    @Setter
    private LocalDateTime readDate;

    public Notification(String name, Task task, User user, LocalDateTime createDate, Boolean read, LocalDateTime readDate) {
        this.name = name;
        this.task = task;
        this.user = user;
        this.createDate = createDate;
        this.read = read;
        this.readDate = readDate;
    }
}
