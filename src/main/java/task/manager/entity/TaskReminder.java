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
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Boolean sent = false;

    @Column
    private LocalDateTime plannedSendDate;

    @Column
    private LocalDateTime sentDate;

    //    @OneToOne(mappedBy = "taskReminder")
    @OneToOne
    @JoinColumn(name = "task_id")
    private Task task;

/*    public TaskReminder(Boolean sent, Task task, LocalDateTime sendDate) {
        this.sent = sent;
        this.task = task;
        this.sendDate = sendDate;
    }*/
}
