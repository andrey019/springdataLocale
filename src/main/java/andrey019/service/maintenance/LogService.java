package andrey019.service.maintenance;


public interface LogService {

    void accessToPage(String message);

    void mailSent(String message, int queued);

    void ajaxJson(String message);

    void signIn(String message);

    void newUser(String message);

    void donation(String message);

    void exception(String message);
}
