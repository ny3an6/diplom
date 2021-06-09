package org.ndmitrenko.diplom.ws;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;
import java.util.Locale;

public class FromClient {
    public enum Action {
        SUBSCRIBE,
        UNSUBSCRIBE
    }

    private final Action action;
    private final String channel;

    public FromClient(Action action, String channel) {
        this.action = action;
        this.channel = channel;
    }

    public FromClient(String action, String channel) {
        this(Action.valueOf(action.trim().toUpperCase(Locale.ROOT)), channel);
    }

    public Action getAction() {
        return action;
    }

    public String getChannel() {
        return channel;
    }

    public static class FromClientDecoder implements Decoder.Text<FromClient> {
        @Override
        public FromClient decode(String s) throws DecodeException {
            String[] ss = s.split(" ");
            if (ss.length != 2)
                throw new DecodeException(s, "unrecognized message format: expected \"(subscribe | unsubscribe) <channel>\"");
            if (!ss[0].equals("subscribe") && !ss[0].equals("unsubscribe"))
                throw new DecodeException(s, "unrecognized command: expected \"subscribe\" or \"unsubscribe\"");
            return new FromClient(ss[0], ss[1]);
        }

        @Override
        public boolean willDecode(String s) {
            return true;
        }

        @Override
        public void init(EndpointConfig config) {

        }

        @Override
        public void destroy() {

        }
    }
}
