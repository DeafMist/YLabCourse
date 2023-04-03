package io.ylab.intensive.lesson05.eventsourcing.db;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.GetResponse;
import io.ylab.intensive.lesson05.eventsourcing.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Component
public class MessageConsumer {
    private ConnectionFactory connectionFactory;

    private DbClient dbClient;

    private final String queueName = "queue";

    public MessageConsumer(@Autowired ConnectionFactory connectionFactory,
                           @Autowired DbClient dbClient) {
        this.connectionFactory = connectionFactory;
        this.dbClient = dbClient;
    }

    public void getMessages() throws IOException, TimeoutException {
        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {
            while (!Thread.currentThread().isInterrupted()) {
                GetResponse message = channel.basicGet(queueName, true);
                if (message != null) {
                    byte[] bytes = message.getBody();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    baos.write(bytes);
                    String[] line = baos.toString().split(" ");
                    baos.close();

                    if (line[0].equals("DELETE")) {
                        dbClient.deletePerson(Long.parseLong(line[1]));
                    }
                    else if (line[0].equals("SAVE")) {
                        dbClient.savePerson(new Person(Long.parseLong(line[1]), line[2], line[3], line[4]));
                    }
                }
            }
        }
    }
}
