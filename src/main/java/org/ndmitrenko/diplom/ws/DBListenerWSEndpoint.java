package org.ndmitrenko.diplom.ws;

import org.ndmitrenko.diplom.configuration.ApplicationContextProvider;
import org.ndmitrenko.diplom.configuration.CustomSpringConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.server.standard.SpringConfigurator;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@ServerEndpoint(
        value = "/ws/dblistener",
        encoders = {ToClient.ToClientEncoder.class},
        decoders = {FromClient.FromClientDecoder.class},
        configurator = CustomSpringConfigurator.class
)
public class DBListenerWSEndpoint {
    private final Logger logger = LoggerFactory.getLogger(DBListenerWSEndpoint.class);

    private final PostgresMessageListener listener =
            ApplicationContextProvider.getApplicationContext()
                    .getBean(PostgresMessageListener.class);

    @OnOpen
    public void onOpen(final Session session) {
        try {
            session.getBasicRemote().sendText("accepted");
        } catch (IOException e) {
            logger.error("Error in onOpen", e);
        }
    }

    @OnError
    public void onError(final Session session, final Throwable throwable) {
        logger.debug("in onError");
        logger.error("Error in WS Endpoint", throwable);

        if(session.isOpen()) {
            try {
                session.getBasicRemote().sendObject(ToClient.error(throwable.getMessage()));
            } catch (IOException | EncodeException e) {
                logger.error("Error in onError", e);
            }
        }
    }

    @OnMessage
    public ToClient onMessage(final FromClient message, final Session session) {
        switch (message.getAction()) {
            case SUBSCRIBE:
                listener.subscribe(message.getChannel(), session);
                return ToClient.message("subscribed to channel " + message.getChannel());
            case UNSUBSCRIBE:
                listener.unsubscribe(message.getChannel(), session);
                return ToClient.message("unsubscribed from channel " + message.getChannel());
            default:
                logger.error("illegal action type: " + message.getAction());
                return null;
        }
    }

    @OnClose
    public void onClose(final Session session) {
        logger.debug("in onClose");
        listener.unsubscribeAll(session);
        try {
            session.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
