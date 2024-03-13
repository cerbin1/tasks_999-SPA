package task.manager.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "task_files")
@NoArgsConstructor
@Getter
public class TaskFile {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;

    private String type;

    @Lob
    private byte[] data;

    public TaskFile(String name, String type, byte[] data) {
        this.name = name;
        this.type = type;
        this.data = data;
    }
}
