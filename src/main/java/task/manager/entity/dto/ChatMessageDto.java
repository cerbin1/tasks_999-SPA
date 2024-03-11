package task.manager.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public record ChatMessageDto(String content, Long taskId) {
}
