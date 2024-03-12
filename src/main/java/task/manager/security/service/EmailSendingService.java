package task.manager.security.service;


public interface EmailSendingService {

    boolean sendEmail(String emailSubject, String emailContent, String emailReceiver);
}
