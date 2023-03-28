package io.ylab.intensive.lesson04.eventsourcing.api;

import com.rabbitmq.client.ConnectionFactory;
import io.ylab.intensive.lesson04.DbUtil;
import io.ylab.intensive.lesson04.RabbitMQUtil;

import javax.sql.DataSource;

public class ApiApp {
  public static void main(String[] args) throws Exception {
    ConnectionFactory connectionFactory = initMQ();
    DataSource dataSource = initDb();

    // Тут пишем создание PersonApi, запуск и демонстрацию работы
    PersonApi personApi = new PersonApiImpl(connectionFactory, dataSource);

    System.out.println(personApi.findAll());
    personApi.savePerson(1L, "Petr", "Petrov", "Petrovich");
    personApi.savePerson(1L, "David", "Petrov", "Petrovich");
    personApi.savePerson(2L, "Komar", "Komarov", "Komarovich");
    personApi.findPerson(1L);
    System.out.println(personApi.findAll());

    personApi.deletePerson(2L);
    personApi.findPerson(2L);
  }

  private static DataSource initDb() throws Exception {
    return DbUtil.buildDataSource();
  }

  private static ConnectionFactory initMQ() throws Exception {
    return RabbitMQUtil.buildConnectionFactory();
  }
}
