package task.manager.security.service;

import task.manager.entity.User;

public interface UserActivatorService {

    void generateActivationLinkFor(User user);
}

