package task.manager.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity(name = "user_activation_links")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserActivationLink {
    @Id
    @Column
    private UUID linkId = UUID.randomUUID();

    @Column
    private Long userId;

    public UserActivationLink(Long userId) {
        this.userId = userId;
    }

}
