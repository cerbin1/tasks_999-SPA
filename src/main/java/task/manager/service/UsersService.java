package task.manager.service;

import task.manager.entity.User;

import java.util.UUID;

public interface UsersService {

    UUID createUser (User user);
}
