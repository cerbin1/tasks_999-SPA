package task.manager.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity(name = "priorities")
@Getter
public class Priority {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String value;
}
