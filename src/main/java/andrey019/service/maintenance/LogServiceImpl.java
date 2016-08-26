package andrey019.service.maintenance;

import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;

@Service("logService")
public class LogServiceImpl implements LogService {

    private final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private final static long TIMEZONE_CORRECTION_MILLISECONDS = 7 * 60 * 60 * 1000;


    @Override
    public void accessToPage(String message) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        stringBuilder.append(DATE_FORMAT.format(System.currentTimeMillis() + TIMEZONE_CORRECTION_MILLISECONDS));
        stringBuilder.append("] [Access to Page] ");
        stringBuilder.append(message);
        System.out.println(stringBuilder.toString());
    }

    @Override
    public void mailSent(String message, int queued) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        stringBuilder.append(DATE_FORMAT.format(System.currentTimeMillis() + TIMEZONE_CORRECTION_MILLISECONDS));
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
        stringBuilder.append(DATE_FORMAT.format(System.currentTimeMillis() + TIMEZONE_CORRECTION_MILLISECONDS));
        stringBuilder.append("] [AJAX/JSON] ");
        stringBuilder.append(message);
        System.out.println(stringBuilder.toString());
    }
}
