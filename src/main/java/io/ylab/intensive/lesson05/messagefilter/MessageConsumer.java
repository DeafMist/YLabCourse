package io.ylab.intensive.lesson05.messagefilter;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.GetResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Component
public class MessageConsumer {
    private ConnectionFactory connectionFactory;

    public MessageConsumer(@Autowired ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public String getMessage(String queue) {
        String text = "";

        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {
            GetResponse message = channel.basicGet(queue, true);

            if (message != null) {
                byte[] bytes = message.getBody();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                baos.write(bytes);
                text = baos.toString();
                baos.close();
            }
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }

        return text;
    }
}
