package andrey019.service;


import andrey019.model.CustomMessage;
import andrey019.service.maintenance.MailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("mailService")
public class MailServiceImpl implements MailService {

    private static final String FROM_EMAIL = "wunderwaffelapp@mail.ru";

    @Autowired
    private MailSenderService mailSenderService;


    @Override
    public void sendMail(String toEmail, String subject, String text) {
        final CustomMessage message = new CustomMessage();
        message.setFrom(FROM_EMAIL);
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(text);
        mailSenderService.addMessage(message);
    }
}
