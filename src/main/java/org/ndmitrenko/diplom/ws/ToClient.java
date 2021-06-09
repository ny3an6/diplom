package org.ndmitrenko.diplom.ws;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.ndmitrenko.diplom.configuration.ApplicationContextProvider;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ToClient {
    private final String channel;
    private final String error;
    private final String message;
    private final JsonNode data;

    public ToClient(String channel, String error, String message, JsonNode data) {
        this.channel = channel;
        this.error = error;
        this.message = message;
        this.data = data;
    }

    public String getChannel() {
        return channel;
    }

    public String getError() {
        return error;
    }

    public JsonNode getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public static ToClient data(String channel, JsonNode data) {
        return new ToClient(channel, "", "", data);
    }

    public static ToClient error(String error) {
        return new ToClient("", error, "", null);
    }

    public static ToClient message(String message) {
        return new ToClient("", "", message, null);
    }

    public static class ToClientEncoder implements Encoder.Text<ToClient> {
        private ObjectMapper om;

        @Override
        public String encode(ToClient object) throws EncodeException {
            try {
                return om.writeValueAsString(object);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                throw new EncodeException(object, e.getMessage());
            }
        }

        @Override
        public void init(EndpointConfig config) {
            om = ApplicationContextProvider.getApplicationContext()
                    .getBean(ObjectMapper.class);
        }

        @Override
        public void destroy() {

        }
    }
}
