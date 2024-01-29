package task.manager.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity(name = "users")
@Getter
public class User {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;
}
