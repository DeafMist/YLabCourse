package io.ylab.intensive.lesson05.messagefilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SentenceService {
    private final String inputExchange = "inputExchange";

    private final String outputExchange = "outputExchange";

    private final String inputQueue = "input";

    private final String outputQueue = "output";

    private DbClient dbClient;

    private MessageProducer messageProducer;

    private MessageConsumer messageConsumer;

    public SentenceService(@Autowired DbClient dbClient,
                           @Autowired MessageProducer messageProducer,
                           @Autowired MessageConsumer messageConsumer) {
        this.dbClient = dbClient;
        this.messageProducer = messageProducer;
        this.messageConsumer = messageConsumer;
    }

    public String getValidText() {
        String text = messageConsumer.getMessage(inputQueue);

        text = validateSentence(text);

        messageProducer.sendText(outputQueue, outputExchange, text);

        return messageConsumer.getMessage(outputQueue);
    }

    // Не очень понял, как разделить логику с отправкой сообщений в одну очередь и получением из другой, так как у нас всего
    // один сервис, поэтому и сделал этот метод отдельно.
    public void sendSentenceToInput(String sentence) {
        messageProducer.sendText(inputQueue, inputExchange, sentence);
    }

    private String validateSentence(String sentence) {
        String[] words = sentence.split("[ .,;?!\\n]");

        for (String word : words) {
            String newWord = validateWord(word);
            sentence = sentence.replace(word, newWord);
        }

        return sentence;
    }

    private String validateWord(String word) {
        char[] wordArray = word.toCharArray();
        if (dbClient.findWord(word.toLowerCase())) {
            for (int i = 1; i < word.length() - 1; i++) {
                wordArray[i] = '*';
            }
        }
        return String.valueOf(wordArray);
    }
}
