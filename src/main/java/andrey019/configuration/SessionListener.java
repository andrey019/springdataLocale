package andrey019.configuration;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;


public class SessionListener implements HttpSessionListener {

    private final static int SESSION_VALIDITY_SECONDS = 24 * 60 * 60;

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        httpSessionEvent.getSession().setMaxInactiveInterval(SESSION_VALIDITY_SECONDS);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        httpSessionEvent.getSession().invalidate();
    }
}
