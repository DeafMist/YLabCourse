package io.ylab.intensive.lesson05.eventsourcing.db;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class DbApp {
  private static String queueName = "queue";

  public static void main(String[] args) throws Exception {
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(Config.class);
    applicationContext.start();

    // тут пишем создание и запуск приложения работы с БД
    MessageConsumer messageConsumer = applicationContext.getBean(MessageConsumer.class);
    messageConsumer.getMessages();
  }
}
