package io.ylab.intensive.lesson05.messagefilter;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MessageFilterApp {
  public static void main(String[] args) {
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(Config.class);
    applicationContext.start();

    SentenceService sentenceService = applicationContext.getBean(SentenceService.class);
    String sentence = "Извините, пожалуйста, за следующее предложение) " +
            "Проверка работоспособности приложения: " +
            "?ебал?? тебя ;ЦеЛКа! ХуЙ, суКА шлюха! мРАзь, уважаемый!";

    sentenceService.sendSentenceToInput(sentence);
    System.out.println(sentenceService.getValidText());
  }
}
