package task.manager.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity(name = "user_activation_links")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserActivationLink {
    @Id
    private UUID linkId = UUID.randomUUID();

    private Long userId;

    @Setter
    private boolean expired = false;

    public UserActivationLink(Long userId) {
        this.userId = userId;
    }

}
