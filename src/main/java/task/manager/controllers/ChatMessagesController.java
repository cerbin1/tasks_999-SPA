package task.manager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import task.manager.entity.ChatMessage;
import task.manager.entity.Task;
import task.manager.entity.User;
import task.manager.entity.dto.ChatMessageDto;
import task.manager.entity.repository.ChatMessagesRepository;
import task.manager.entity.repository.NotificationsRepository;
import task.manager.entity.repository.TasksRepository;
import task.manager.entity.repository.UsersRepository;
import task.manager.security.jwt.MessageResponse;
import task.manager.utils.AuthenticationUtils;

import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/chat/messages")
public class ChatMessagesController {

    private final ChatMessagesRepository chatMessagesRepository;
    private final UsersRepository usersRepository;
    private final TasksRepository tasksRepository;
    private final NotificationsRepository notificationsRepository;

    @Autowired
    public ChatMessagesController(ChatMessagesRepository chatMessagesRepository, UsersRepository usersRepository, TasksRepository tasksRepository, NotificationsRepository notificationsRepository) {
        this.chatMessagesRepository = chatMessagesRepository;
        this.usersRepository = usersRepository;
        this.tasksRepository = tasksRepository;
        this.notificationsRepository = notificationsRepository;
    }

    @PostMapping
    public ResponseEntity<?> createMessage(@RequestBody ChatMessageDto chatMessageDto) {
        String content = chatMessageDto.content();
        if (content.isBlank()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Message content is required."));
        }
        Long taskId = chatMessageDto.taskId();
        if (taskId == null) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Task id is required."));
        }

        User sender = usersRepository.findById(AuthenticationUtils.getLoggedUserId()).orElseThrow();
        Task task = tasksRepository.findById(taskId).orElseThrow();

        ChatMessage messageCreated = chatMessagesRepository.save(new ChatMessage(content, sender, LocalDateTime.now(), task.getMessages().size()));

        notificationsRepository.createForMessage(task);
        return new ResponseEntity<>(messageCreated, CREATED);
    }
}
