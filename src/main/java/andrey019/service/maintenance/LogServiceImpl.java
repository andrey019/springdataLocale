package andrey019.service.maintenance;

import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

@Service("logService")
public class LogServiceImpl implements LogService {

    private final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    static {
        DATE_FORMAT.setTimeZone(TimeZone.getTimeZone("GMT+2"));
    }


    @Override
    public void accessToPage(String message) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        stringBuilder.append(DATE_FORMAT.format(System.currentTimeMillis()));
        stringBuilder.append("] [Access to Page] ");
        stringBuilder.append(message);
        System.out.println(stringBuilder.toString());
    }

    @Override
    public void mailSent(String message, int queued) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        stringBuilder.append(DATE_FORMAT.format(System.currentTimeMillis()));
        stringBuilder.append("] [Mail Sent, Queued ");
        stringBuilder.append(queued);
        stringBuilder.append("] ");
        stringBuilder.append(message);
        System.out.println(stringBuilder.toString());
    }

    @Override
    public void ajaxJson(String message) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        stringBuilder.append(DATE_FORMAT.format(System.currentTimeMillis()));
        stringBuilder.append("] [AJAX/JSON] ");
        stringBuilder.append(message);
        System.out.println(stringBuilder.toString());
    }

    @Override
    public void signIn(String message) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        stringBuilder.append(DATE_FORMAT.format(System.currentTimeMillis()));
        stringBuilder.append("] [SignIn] ");
        stringBuilder.append(message);
        System.out.println(stringBuilder.toString());
    }

    @Override
    public void newUser(String message) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        stringBuilder.append(DATE_FORMAT.format(System.currentTimeMillis()));
        stringBuilder.append("] [NewUser] ");
        stringBuilder.append(message);
        System.out.println(stringBuilder.toString());
    }

    @Override
    public void donation(String message) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        stringBuilder.append(DATE_FORMAT.format(System.currentTimeMillis()));
        stringBuilder.append("] [Donation] ");
        stringBuilder.append(message);
        System.out.println(stringBuilder.toString());
    }

    @Override
    public void exception(String message) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        stringBuilder.append(DATE_FORMAT.format(System.currentTimeMillis()));
        stringBuilder.append("] [Exception] ");
        stringBuilder.append(message);
        System.out.println(stringBuilder.toString());
    }
}
