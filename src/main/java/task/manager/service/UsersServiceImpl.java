package task.manager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import task.manager.entity.User;
import task.manager.entity.UsersRepository;
import task.manager.security.service.UserActivatorService;

import java.util.UUID;

@Service
public class UsersServiceImpl implements UsersService {
    private final UsersRepository usersRepository;
    private final UserActivatorService userActivatorService;

    @Autowired
    public UsersServiceImpl(UsersRepository usersRepository, UserActivatorService userActivatorService) {
        this.usersRepository = usersRepository;
        this.userActivatorService = userActivatorService;
    }

    @Override
    public void createUser(User user) {
        User savedUser = usersRepository.save(user);
        userActivatorService.generateActivationLinkFor(savedUser);
    }
}
