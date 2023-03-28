package lesson2.task4;

public class SnilsValidatorTest {
    public static void main(String[] args) {
        SnilsValidator snilsValidator = new SnilsValidatorImpl();

        System.out.println(snilsValidator.validate("")); // false
        System.out.println(snilsValidator.validate("    ")); // false
        System.out.println(snilsValidator.validate(null)); // false
        System.out.println(snilsValidator.validate("1234567891011")); // false

        System.out.println(snilsValidator.validate("1326a123122")); // false
        System.out.println(snilsValidator.validate("1326212310s")); // false

        System.out.println(snilsValidator.validate("01468870570")); //false
        System.out.println(snilsValidator.validate("31287389110")); // false

        System.out.println(snilsValidator.validate("08952561308")); // true
        System.out.println(snilsValidator.validate("68947946401")); // true
        System.out.println(snilsValidator.validate("38317069593")); // true
        System.out.println(snilsValidator.validate("90114404441")); // true
    }
}
