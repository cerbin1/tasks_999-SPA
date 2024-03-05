package task.manager.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import task.manager.entity.UserActivationLink;
import task.manager.entity.UserActivationLinksRepository;

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
    public void generateActivationLinkForUserId(Long userId) {
        UserActivationLink userActivationLink = new UserActivationLink(userId);
        userActivationLinksRepository.save(userActivationLink);
        emailSendingService.sendEmail(applicationUrl + "activate/" + userActivationLink.getLinkId(), "TODO"); // TODO
    }
}
