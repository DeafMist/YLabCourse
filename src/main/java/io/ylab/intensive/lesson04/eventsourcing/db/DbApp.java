package io.ylab.intensive.lesson04.eventsourcing.db;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.GetResponse;
import io.ylab.intensive.lesson04.DbUtil;
import io.ylab.intensive.lesson04.RabbitMQUtil;
import io.ylab.intensive.lesson04.eventsourcing.Person;

import javax.sql.DataSource;
import java.io.ByteArrayOutputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DbApp {
  private static String queueName = "queue";

  private static final String insertQuery = "INSERT INTO person (first_name, last_name, middle_name, person_id) VALUES (?, ?, ?, ?)";

  private static final String updateQuery = "UPDATE person SET first_name=?, last_name=?, middle_name=? WHERE person_id=?";

  public static final String selectQuery = "SELECT * FROM person WHERE person_id=?";

  public static void main(String[] args) throws Exception {
    DataSource dataSource = initDb();
    ConnectionFactory connectionFactory = initMQ();

    try (Connection connection = connectionFactory.newConnection();
         Channel channel = connection.createChannel()) {
      GetResponse message = channel.basicGet(queueName, true);

      while (!Thread.currentThread().isInterrupted()) {
        if (message != null) {
          byte[] bytes = message.getBody();
          ByteArrayOutputStream baos = new ByteArrayOutputStream();
          baos.write(bytes);
          String[] line = baos.toString().split(" ");
          baos.close();

          if (line[0].equals("DELETE")) {
            deletePerson(dataSource, Long.parseLong(line[1]));
          }
          else if (line[0].equals("SAVE")) {
            savePerson(dataSource, new Person(Long.parseLong(line[1]), line[2], line[3], line[4]));
          }
        }
      }
    }
  }

  private static void savePerson(DataSource dataSource, Person person) {
    try (java.sql.Connection connection = dataSource.getConnection();
         PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
         PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
         PreparedStatement selectStatement = connection.prepareStatement(selectQuery)) {
      selectStatement.setLong(1, person.getId());

      ResultSet selectSet = selectStatement.executeQuery();

      if (selectSet.next()) {
        sendStatement(person, updateStatement, "обновление");
      }
      else {
        sendStatement(person, insertStatement, "добавление");
      }

      selectSet.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  private static void sendStatement(Person person, PreparedStatement updateStatement, String message) throws SQLException {
    updateStatement.setString(1, person.getName());
    updateStatement.setString(2, person.getLastName());
    updateStatement.setString(3, person.getMiddleName());
    updateStatement.setLong(4, person.getId());

    updateStatement.executeUpdate();
    System.out.println("Произошло " + message + " данных человека с id = " + person.getId());
  }

  private static void deletePerson(DataSource dataSource, long personId) {
    try (java.sql.Connection connection = dataSource.getConnection();
         PreparedStatement statement = connection.prepareStatement("DELETE FROM person WHERE id=?")) {
      statement.setLong(1, personId);

      int res = statement.executeUpdate();
      if (res == 0) {
        System.out.println("Была попытка удаления человека с номером id = " + personId +
                ", но данные о данном человеке были не найдены!");
      }
      else {
        System.out.println("Удаление человека с id = " + personId + " прошло успешно!");
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  private static ConnectionFactory initMQ() throws Exception {
    return RabbitMQUtil.buildConnectionFactory();
  }
  
  private static DataSource initDb() throws SQLException {
    String ddl = "" 
                     + "drop table if exists person;" 
                     + "create if not exists table person (\n"
                     + "person_id bigint primary key,\n"
                     + "first_name varchar,\n"
                     + "last_name varchar,\n"
                     + "middle_name varchar\n"
                     + ")";
    DataSource dataSource = DbUtil.buildDataSource();
    DbUtil.applyDdl(ddl, dataSource);
    return dataSource;
  }
}
