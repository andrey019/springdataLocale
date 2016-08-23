package andrey019.service;


public interface MailService {

    void sendMail(String toEmail, String subject, String text);
}
