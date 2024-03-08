package task.manager.entity.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import task.manager.entity.UserActivationLink;

import java.util.UUID;

@Repository
public interface UserActivationLinksRepository extends CrudRepository<UserActivationLink, UUID> {
}
