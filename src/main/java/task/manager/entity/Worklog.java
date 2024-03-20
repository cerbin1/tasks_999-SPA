package task.manager.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity(name = "worklogs")
@Getter
public class Worklog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private Long minutes;

    private String comment;

    @Setter
    private LocalDateTime modificationDate;

    @Setter
    @ManyToOne
    @JoinColumn(name = "CREATOR_ID", referencedColumnName = "id")
    private User creator;
}
