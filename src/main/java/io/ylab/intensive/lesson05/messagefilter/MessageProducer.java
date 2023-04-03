package io.ylab.intensive.lesson05.messagefilter;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Component
public class MessageProducer {
    private ConnectionFactory connectionFactory;

    public MessageProducer(@Autowired ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public void sendText(String queue, String exchange, String text) {
        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(exchange, BuiltinExchangeType.TOPIC);
            channel.queueDeclare(queue, true, false, false, null);
            channel.queueBind(queue, exchange, "*");

            channel.basicPublish(exchange, "*", null, text.getBytes());
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }
}
