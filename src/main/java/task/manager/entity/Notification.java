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
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String taskName;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column
    private LocalDateTime createDate;

    @Column
    @Setter
    private Boolean read;

    @Column
    @Setter
    private LocalDateTime readDate;

    public Notification(String name, String taskName, User user, LocalDateTime createDate, Boolean read, LocalDateTime readDate) {
        this.name = name;
        this.taskName = taskName;
        this.user = user;
        this.createDate = createDate;
        this.read = read;
        this.readDate = readDate;
    }
}
