package task.manager.entity.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import task.manager.entity.User;

import java.util.Optional;

@Repository
public interface UsersRepository extends CrudRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    @Query(value = "SELECT COUNT(*) FROM CHAT_MESSAGES m WHERE m.sender_id=:userId", nativeQuery = true)
    long getUserMessagesCount(@Param("userId") Long userId);
}
