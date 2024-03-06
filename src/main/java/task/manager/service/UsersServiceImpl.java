package task.manager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import task.manager.entity.User;
import task.manager.entity.UserActivationLink;
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

    @Override
    public boolean activateUserBy(UUID userLinkId) {
        UserActivationLink link = userActivatorService.findByUuid(userLinkId);
        if (link.isExpired()) {
            return false;
        }
        User user = usersRepository.findById(link.getUserId()).orElseThrow();
        user.setActive(true);
        usersRepository.save(user);
        return userActivatorService.expire(link);
    }
}
