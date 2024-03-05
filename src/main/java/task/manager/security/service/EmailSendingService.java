package task.manager.security.service;


public interface EmailSendingService {

    boolean sendEmail(String taskContent, String emailReceiver);
}
