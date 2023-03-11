package lesson2.task2;

public class ComplexTest {
    public static void main(String[] args) {
        Complex complex1 = new Complex(2);
        System.out.println(complex1);

        Complex complex2 = new Complex(1, 1);
        System.out.println(complex2);

        Complex complex3 = new Complex(4, -3);

        System.out.println(complex3 + " + " + complex2 + " = " + complex3.sum(complex2));

        System.out.println(complex3 + " - " + "(" + complex2 + ")" + " = " + complex3.sub(complex2));

        System.out.println("(" + complex3 + ")" + " * " + "(" + complex2 + ")" + " = " + complex3.mult(complex2));

        System.out.println("Модуль комплексного числа " + complex3 + " = " + complex3.abs());
    }
}
