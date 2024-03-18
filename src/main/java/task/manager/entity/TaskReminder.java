package task.manager.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity(name = "task_reminders")
@NoArgsConstructor
@Getter
@Setter
public class TaskReminder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean sent = false;

    private LocalDateTime plannedSendDate;

    private LocalDateTime sentDate;

    @OneToOne
    @JoinColumn(name = "task_id")
    private Task task;
}
