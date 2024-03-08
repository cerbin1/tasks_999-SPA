package task.manager.entity.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import task.manager.entity.Role;
import task.manager.entity.RoleName;

import java.util.Optional;

@Repository
public interface RolesRepository extends CrudRepository<Role, Long> {

    Optional<Role> findByName(RoleName name);
}
