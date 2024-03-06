package task.manager.service;

import task.manager.entity.User;

import java.util.UUID;

public interface UsersService {

    void createUser(User user);

    boolean activateUserBy(UUID userLinkId);
}
