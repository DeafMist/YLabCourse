package lesson2.task1;

public class SequenceGeneratorTest {
    public static void main(String[] args) {
        SequenceGenerator sequenceGenerator = new SequenceGeneratorImpl();

        System.out.print("A: ");
        sequenceGenerator.a(7);
        System.out.println();

        System.out.print("B: ");
        sequenceGenerator.b(5);
        System.out.println();

        System.out.print("C: ");
        sequenceGenerator.c(6);
        System.out.println();

        System.out.print("D: ");
        sequenceGenerator.d(4);
        System.out.println();

        System.out.print("E: ");
        sequenceGenerator.e(6);
        System.out.println();

        System.out.print("F: ");
        sequenceGenerator.f(4);
        System.out.println();

        System.out.print("G: ");
        sequenceGenerator.g(7);
        System.out.println();

        System.out.print("H: ");
        sequenceGenerator.h(8);
        System.out.println();

        System.out.print("I: ");
        sequenceGenerator.i(6);
        System.out.println();

        System.out.print("J: ");
        sequenceGenerator.j(8);
        System.out.println();
    }
}
