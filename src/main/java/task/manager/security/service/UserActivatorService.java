package task.manager.security.service;

import task.manager.entity.User;
import task.manager.entity.UserActivationLink;

import java.util.UUID;

public interface UserActivatorService {

    void generateActivationLinkFor(User user);

    UserActivationLink findByUuid(UUID uuid);

    boolean expire(UserActivationLink link);
}

