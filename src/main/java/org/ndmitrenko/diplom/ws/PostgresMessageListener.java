package org.ndmitrenko.diplom.ws;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.postgresql.PGConnection;
import org.postgresql.PGNotification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import javax.websocket.EncodeException;
import javax.websocket.Session;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

@Component
public class PostgresMessageListener {
    private final Logger logger = LoggerFactory.getLogger(PostgresMessageListener.class);

    private final ConcurrentMap<Session, Set<String>> sessions = new ConcurrentHashMap<>();

    private final ObjectMapper om;
    private final ThreadPoolTaskScheduler tpts;
    private final Connection connection;

    @Autowired
    PostgresMessageListener(DBConnectionProvider dbcp, ObjectMapper om, @Qualifier("dbScheduler") ThreadPoolTaskScheduler tpts) {
        this.connection = dbcp.getConnection();
        this.om = om;
        this.tpts = tpts;
        startListening();
    }

    public ObjectMapper getOm() {
        return om;
    }

    private void startListening() {
        tpts.scheduleWithFixedDelay(() -> {
            logger.info("polling..." + Thread.currentThread().getName());
            // issue a dummy query to contact the backend
            // and receive any pending notifications.
            try {
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT 1");
                rs.close();
                stmt.close();
                PGNotification[] notifications = ((PGConnection) connection).getNotifications();
                if (notifications != null) {
                    for (PGNotification notification : notifications) {
                        logger.info("Got notification: " + notification.getName());
                        logger.info("Got parameter: " + notification.getParameter());
                        // ObjectMapper.readTree некорректно обрабатывает пустые строки
                        JsonNode json = notification.getParameter().isEmpty() ?
                                null :
                                om.readTree(notification.getParameter());
                        sendReplyBack(notification.getName(), json);
                    }
                }
            } catch (SQLException | IOException e) {
                logger.error("Error in NOTIFY processing", e);
            }
        }, 500);
    }

    public void subscribe(String channel, Session session) {
        try {
            sessions.computeIfAbsent(session, s -> new HashSet<>()).add(channel);
            Statement stmt = connection.createStatement();
            stmt.execute("LISTEN \"" + channel + "\"");
            stmt.close();
        } catch (SQLException ex) {
            logger.error("Error", ex);
        }
    }

    private void unlisten(String channel) {
        try {
            Statement stmt = connection.createStatement();
            stmt.execute("UNLISTEN \"" + channel + "\"");
            stmt.close();
        } catch (SQLException ex) {
            logger.error("Error", ex);
        }
    }

    public void unsubscribe(String channel, Session session) {
        sessions.getOrDefault(session, Collections.emptySet()).remove(channel);
        unlisten(channel);
    }

    public void unsubscribeAll(Session session) {
        sessions.getOrDefault(session, Collections.emptySet()).forEach(this::unlisten);
        sessions.remove(session);
    }

    private void sendReplyBack(String channel, JsonNode data) {
        // Удаляем закрытые сессии до итерации, чтобы избежать ConcurrencyException в foreach
        List<Session> closedSessions = sessions.keySet().stream()
                .filter(s -> !s.isOpen())
                .collect(Collectors.toList());
        closedSessions.forEach(sessions::remove);
        System.out.println(closedSessions);
        sessions.forEach((session, channels) -> {
            if (channels.contains(channel)) {
                try {
                    ToClient message = ToClient.data(channel, data);
                    System.out.println(message);
                    session.getBasicRemote().sendObject(message);
                } catch (IOException | EncodeException e) {
                    logger.error("Error in sending reply", e);
                }
            }
        });
    }

    public void stopListening() {
        tpts.shutdown();
    }
}