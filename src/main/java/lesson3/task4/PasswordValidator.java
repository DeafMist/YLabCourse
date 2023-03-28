package lesson3.task4;

public class PasswordValidator {
    private static String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789_";

    public static boolean isValidLogAndPas(String login, String password, String confirmPassword) {
        try {
            if (!isCorrectString(login)) {
                throw new WrongLoginException("Логин не должен быть пустым");
            }

            if (!isCorrectString(password)) {
                throw new WrongPasswordException("Пароль не должен быть пустым");
            }

            if (!areAllowedAllCharacters(login)) {
                throw new WrongLoginException("Логин содержит недопустимые символы");
            }

            if (!isCorrectLength(login)) {
                throw new WrongLoginException("Логин слишком длинный");
            }

            if (!areAllowedAllCharacters(password)) {
                throw new WrongPasswordException("Пароль содержит недопустимые символы");
            }

            if (!isCorrectLength(password)) {
                throw new WrongPasswordException("Пароль слишком длинный");
            }

            if (!password.equals(confirmPassword)) {
                throw new WrongPasswordException("Пароль и подтверждение не совпадают");
            }
        } catch (WrongLoginException | WrongPasswordException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private static boolean isCorrectString(String arg) {
        return arg != null && !arg.isEmpty() && !arg.isBlank();
    }

    private static boolean isCorrectLength(String arg) {
        return arg.length() < 20;
    }

    private static boolean areAllowedAllCharacters(String arg) {
        for (int i = 0; i < arg.length(); i++) {
            if (!alphabet.contains(Character.toString(arg.charAt(i)))) {
                return false;
            }
        }

        return true;
    }
}
