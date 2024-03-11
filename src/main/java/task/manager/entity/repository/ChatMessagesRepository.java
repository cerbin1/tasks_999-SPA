package task.manager.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import task.manager.entity.ChatMessage;

@Repository
public interface ChatMessagesRepository extends JpaRepository<ChatMessage, Long> {
}
