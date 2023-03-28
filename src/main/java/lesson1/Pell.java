package lesson1;

import java.util.Scanner;

public class Pell {
    public static void main(String[] args) throws Exception {
        try (Scanner scanner = new Scanner(System.in)) {
            int n = scanner.nextInt();

            int prev = 0;
            int cur = 1;

            if (n == 0) {
                System.out.println(prev);
            }
            else {
                for (int i = 0; i < n - 1; i++) {
                    int tmp = 2 * cur + prev;
                    prev = cur;
                    cur = tmp;
                }
                System.out.println(cur);
            }
        }
    }
}
