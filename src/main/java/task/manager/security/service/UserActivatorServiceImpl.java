package task.manager.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import task.manager.entity.User;
import task.manager.entity.UserActivationLink;
import task.manager.entity.repository.UserActivationLinksRepository;

import java.util.UUID;

@Service
public class UserActivatorServiceImpl implements UserActivatorService {
    @Value("${application.url}")
    private String applicationUrl;

    private final UserActivationLinksRepository userActivationLinksRepository;
    private final EmailSendingService emailSendingService;

    @Autowired
    public UserActivatorServiceImpl(UserActivationLinksRepository userActivationLinksRepository,
                                    EmailSendingService emailSendingService) {
        this.userActivationLinksRepository = userActivationLinksRepository;
        this.emailSendingService = emailSendingService;
    }

    @Override
    public void generateActivationLinkFor(User user) {
        UserActivationLink userActivationLink = new UserActivationLink(user.getId());
        userActivationLinksRepository.save(userActivationLink);
        String taskContent = applicationUrl + "auth/activate/" + userActivationLink.getLinkId();
        emailSendingService.sendEmail(taskContent, user.getEmail());
    }

    @Override
    public UserActivationLink findByUuid(UUID uuid) {
        return userActivationLinksRepository.findById(uuid).orElseThrow();
    }

    @Override
    public boolean expire(UserActivationLink link) {
        link.setExpired(true);
        userActivationLinksRepository.save(link);
        return true;
    }
}
