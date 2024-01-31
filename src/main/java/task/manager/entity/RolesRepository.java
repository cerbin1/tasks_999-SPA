package task.manager.entity;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolesRepository extends CrudRepository<Role, Long> {

    Optional<Role> findByName(RoleName name);
}
