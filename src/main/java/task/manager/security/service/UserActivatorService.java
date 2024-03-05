package task.manager.security.service;

public interface UserActivatorService {

    void generateActivationLinkForUserId(Long userId);
}

