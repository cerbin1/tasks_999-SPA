package task.manager.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity(name = "chat_messages")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    @Column(nullable = false)
    private Integer sequence;

    @Column(nullable = false)
    private LocalDateTime sentAt;

    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    @JsonIgnore
    private Task task;

    public ChatMessage(String content, User sender, LocalDateTime sentAt, Task task) {
        this.content = content;
        this.sender = sender;
        this.sentAt = sentAt;
        this.sequence = task.getMessages().size();
        this.task = task;
    }
}
