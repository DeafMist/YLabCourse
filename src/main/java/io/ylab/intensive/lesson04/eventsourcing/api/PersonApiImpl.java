package io.ylab.intensive.lesson04.eventsourcing.api;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import io.ylab.intensive.lesson04.eventsourcing.Person;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 * Тут пишем реализацию
 */
public class PersonApiImpl implements PersonApi {
  private ConnectionFactory connectionFactory;

  private DataSource dataSource;

  private final String exchangeName = "exc";

  private final String queueName = "queue";

  public PersonApiImpl(ConnectionFactory connectionFactory, DataSource dataSource) {
    this.connectionFactory = connectionFactory;
    this.dataSource = dataSource;
  }

  @Override
  public void deletePerson(Long personId) {
    try (Connection connection = connectionFactory.newConnection();
         Channel channel = connection.createChannel()) {
      channel.exchangeDeclare(exchangeName, BuiltinExchangeType.DIRECT);
      channel.queueDeclare(queueName, true, false, false, null);
      channel.queueBind(queueName, exchangeName, "*");

      channel.basicPublish(exchangeName, "key", null, ("DELETE " + personId).getBytes());
    } catch (IOException | TimeoutException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void savePerson(Long personId, String firstName, String lastName, String middleName) {
    try (Connection connection = connectionFactory.newConnection();
         Channel channel = connection.createChannel()) {
      channel.exchangeDeclare(exchangeName, BuiltinExchangeType.DIRECT);
      channel.queueDeclare(queueName, true, false, false, null);
      channel.queueBind(queueName, exchangeName, "*");

      Person person = new Person(personId, firstName, lastName, middleName);

      channel.basicPublish(exchangeName, "key", null,
              ("SAVE " + person).getBytes());
    } catch (IOException | TimeoutException e) {
      e.printStackTrace();
    }
  }

  @Override
  public Person findPerson(Long personId) {
    Person person = null;
    try (java.sql.Connection connection = dataSource.getConnection();
         PreparedStatement statement = connection.prepareStatement("SELECT * FROM person WHERE person_id=?")) {
      statement.setLong(1, personId);

      ResultSet resultSet = statement.executeQuery();

      if (resultSet.next()) {
        person = new Person();

        person.setId(resultSet.getLong(1));
        person.setName(resultSet.getString(2));
        person.setLastName(resultSet.getString(3));
        person.setMiddleName(resultSet.getString(4));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return person;
  }

  @Override
  public List<Person> findAll() {
    List<Person> persons = new ArrayList<>();
    try (java.sql.Connection connection = dataSource.getConnection();
         Statement statement = connection.createStatement()) {

      ResultSet resultSet = statement.executeQuery("SELECT * FROM person");

      while (resultSet.next()) {
        Person person = new Person();

        person.setId(resultSet.getLong(1));
        person.setName(resultSet.getString(2));
        person.setLastName(resultSet.getString(3));
        person.setMiddleName(resultSet.getString(4));

        persons.add(person);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return persons;
  }
}
