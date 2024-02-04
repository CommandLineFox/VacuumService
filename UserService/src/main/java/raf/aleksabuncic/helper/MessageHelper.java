package raf.aleksabuncic.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import java.io.IOException;

@Component
public class MessageHelper {
    private final ObjectMapper objectMapper;

    public MessageHelper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public <T> T getMessage(Message message, Class<T> clazz) throws RuntimeException, JMSException {
        try {
            String json = ((TextMessage) message).getText();
            T data = objectMapper.readValue(json, clazz);
            return null;
        } catch (IOException exception) {
            throw new RuntimeException("Message parsing fails.", exception);
        }
    }

    public String createTextMessage(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Problem with creating text message");
        }
    }
}

