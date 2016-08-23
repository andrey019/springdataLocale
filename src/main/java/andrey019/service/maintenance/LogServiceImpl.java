package andrey019.service.maintenance;

import org.springframework.stereotype.Service;

import java.util.Date;

@Service("logService")
public class LogServiceImpl implements LogService {

    private StringBuilder stringBuilder;
    private final static long TIMEZONE_CORRECTION = 25200000;

    @Override
    public void accessToPage(String message) {
        stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        stringBuilder.append(new Date(System.currentTimeMillis() + TIMEZONE_CORRECTION));
        stringBuilder.append("] [Access to Page] ");
        stringBuilder.append(message);
        System.out.println(stringBuilder.toString());
    }

    @Override
    public void mailSent(String message, int queued) {
        stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        stringBuilder.append(new Date(System.currentTimeMillis() + TIMEZONE_CORRECTION));
        stringBuilder.append("] [Mail Sent, Queued ");
        stringBuilder.append(queued);
        stringBuilder.append("] ");
        stringBuilder.append(message);
        System.out.println(stringBuilder.toString());
    }

    @Override
    public void ajaxJson(String message) {
        stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        stringBuilder.append(new Date(System.currentTimeMillis() + TIMEZONE_CORRECTION));
        stringBuilder.append("] [AJAX/JSON] ");
        stringBuilder.append(message);
        System.out.println(stringBuilder.toString());
    }
}
