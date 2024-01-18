package task.manager.entity;

import jakarta.persistence.*;

@Entity(name = "priorities")
public class Priority {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String value;
}
