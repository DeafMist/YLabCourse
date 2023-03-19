package lesson3.task4;

public class PasswordValidatorTest {
    public static void main(String[] args) {
        System.out.println(PasswordValidator.isValidLogAndPas("deafmist_", "123asd", "123asd")); // true
        System.out.println(PasswordValidator.isValidLogAndPas("1ы5аыод234", "jsakl", "jsakl")); // false
        System.out.println(PasswordValidator.isValidLogAndPas("jsakl", "1ы5аыод234", "1ы5аыод234")); // false
        System.out.println(PasswordValidator.isValidLogAndPas("jsakl", "12345", "12890")); // false
        System.out.println(PasswordValidator.isValidLogAndPas(null, "   ", "")); // false
        System.out.println(PasswordValidator.isValidLogAndPas("asdjkl123456123adjsak", "123", "123")); // false
    }
}
