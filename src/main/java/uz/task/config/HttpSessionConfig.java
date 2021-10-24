package uz.task.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Configuration
public class HttpSessionConfig {
    private static final HashMap<String, HttpSession> sessions = new HashMap<>();

    public List<HttpSession> getActiveSession() {
        return new ArrayList<>(sessions.values());
    }

    @Bean
    public HttpSessionListener httpSessionListener() {
        return new HttpSessionListener() {
            @Override
            public void sessionCreated(HttpSessionEvent se) {
                sessions.put(se.getSession().getId(), se.getSession());
            }

            @Override
            public void sessionDestroyed(HttpSessionEvent se) {
                sessions.remove(se.getSession().getId());
            }
        };
    }
}
