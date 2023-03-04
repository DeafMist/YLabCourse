package lesson1;

import java.util.Random;
import java.util.Scanner;

public class Guess {
    public static void main(String[] args) throws Exception {
//        int number = new Random().nextInt(99) + 1;
        int number = 22;
        int maxAttempts = 10;
        System.out.println("Я загадал число от 1 до 99. У тебя " + maxAttempts + " попыток угадать.");
        try (Scanner scanner = new Scanner(System.in)) {
            int ans;
            int attempt = 1;
            do {
                ans = scanner.nextInt();
                if (ans == number) {
                    System.out.println("Ты угадал с " + attempt + " попытки!");
                    return;
                }
                else if (ans < number) {
                    System.out.println("Мое число больше! У тебя осталось " + (maxAttempts - attempt) + " попыток");
                }
                else if (ans > number) {
                    System.out.println("Мое число меньше! У тебя осталось " + (maxAttempts - attempt) + " попыток");
                }
                attempt++;
            }
            while (attempt <= maxAttempts);

            System.out.println("Ты не угадал");
        }
    }
}
