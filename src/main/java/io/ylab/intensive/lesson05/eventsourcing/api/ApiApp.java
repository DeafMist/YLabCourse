package io.ylab.intensive.lesson05.eventsourcing.api;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ApiApp {
  public static void main(String[] args) throws Exception {
    // Тут пишем создание PersonApi, запуск и демонстрацию работы
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(Config.class);
    applicationContext.start();

    PersonApi personApi = applicationContext.getBean(PersonApi.class);

    // пишем взаимодействие с PersonApi
    System.out.println(personApi.findAll());

    personApi.deletePerson(4L);

    personApi.savePerson(1L, "Petr", "Petrov", "Petrovich");
    personApi.savePerson(3L, "Bogart", "Ramzanov", "Bugaevich");
    personApi.savePerson(2L, "Komar", "Komarov", "Komarovich");
    personApi.savePerson(4L, "Duglas", "Bortovich", "Mandrogovich");

    System.out.println(personApi.findPerson(1L));

    personApi.deletePerson(2L);
    System.out.println(personApi.findPerson(2L));
  }
}
