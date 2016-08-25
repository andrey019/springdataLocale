package andrey019.service.maintenance;


import andrey019.repository.UserConfirmationRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class ConfirmationCleanUpService extends Thread {

    private final static ConfirmationCleanUpService CONFIRMATION_CLEAN_UP_SERVICE = new ConfirmationCleanUpService();
    private final static long INITIAL_DELAY = 10000;
    private final static long CLEANUP_AFTER = 86400000;
    private final static long CHECK_INTERVAL = 3600000;


    @Autowired
    private UserConfirmationRepository userConfirmationRepository;

    private ConfirmationCleanUpService() {}

    @Override
    public void run() {
        try {
            Thread.sleep(INITIAL_DELAY);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while (!isInterrupted()) {
            try {
                Thread.sleep(CHECK_INTERVAL);
                userConfirmationRepository.deleteByDateLessThan(System.currentTimeMillis() - CLEANUP_AFTER);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public static ConfirmationCleanUpService getInstance() {
        return CONFIRMATION_CLEAN_UP_SERVICE;
    }
}
