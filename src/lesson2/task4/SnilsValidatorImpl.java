package lesson2.task4;

public class SnilsValidatorImpl implements SnilsValidator {
    @Override
    public boolean validate(String snils) {
        if (simpleCheck(snils)) return false;

        int n = 11;
        int total = 0;
        for (int i = 9; i >= 1; i--) {
            char symbol = snils.charAt(n - i - 2);
            if (!Character.isDigit(symbol)) {
                return false;
            }
            total += Character.digit(symbol, 10) * i;
        }

        int lastTwoSymbols = Character.digit(snils.charAt(n - 2), 10) * 10 + Character.digit(snils.charAt(n - 1), 10);

        int number;
        if (total < 100) {
            number = total;
        }
        else if (total == 100) {
            number = 0;
        }
        else {
            int tmp = total % 101;
            if (tmp == 100) {
                number = 0;
            }
            else {
                number = tmp;
            }
        }

        return number == lastTwoSymbols;
    }

    private static boolean simpleCheck(String snils) {
        return snils == null || snils.isEmpty() || snils.isBlank() || snils.length() != 11 ||
                !Character.isDigit(snils.charAt(9)) || !Character.isDigit(snils.charAt(10));
    }
}
