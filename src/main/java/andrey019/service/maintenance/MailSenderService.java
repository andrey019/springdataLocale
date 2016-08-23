package andrey019.service.maintenance;


import andrey019.model.CustomMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MailSenderService extends Thread {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private LogService logService;

    private final static MailSenderService MAIL_SENDER_SERVICE = new MailSenderService();
    private final static ConcurrentLinkedQueue<CustomMessage> QUEUE = new ConcurrentLinkedQueue<>();
    private final static long INITIAL_DELAY = 10000;
    private final static long SEND_INTERVAL = 70000;
    private final static String CONTENT_TYPE = "text/html;charset=UTF-8";

    private MailSenderService() {}

    @Override
    public void run() {
        try {
            Thread.sleep(INITIAL_DELAY);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while (!isInterrupted()) {
            try {
                Thread.sleep(SEND_INTERVAL);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (!QUEUE.isEmpty()) {
                CustomMessage message = QUEUE.peek();
                MimeMessagePreparator preparator = getMessagePreparator(message);
                try {
                    mailSender.send(preparator);
                    QUEUE.poll();
                    logService.mailSent(message.getTo(), QUEUE.size());
                } catch (MailException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
    }

    private MimeMessagePreparator getMessagePreparator(final CustomMessage message) {
        MimeMessagePreparator preparator = new MimeMessagePreparator() {
            public void prepare(MimeMessage mimeMessage) throws Exception {
                mimeMessage.setFrom(message.getFrom());
                mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(message.getTo()));
                mimeMessage.setSubject(message.getSubject());
                mimeMessage.setContent(message.getText(), CONTENT_TYPE);
            }
        };
        return preparator;
    }

    public static MailSenderService getInstance() {
        return MAIL_SENDER_SERVICE;
    }

    public void addMessage(CustomMessage message) {
        QUEUE.add(message);
    }
}
