package lesson2.task1;

public class SequenceGeneratorImpl implements SequenceGenerator {
    @Override
    public void a(int n) {
        for (int i = 2; i < 2 * n + 2; i += 2) {
            System.out.print(i + " ");
        }
    }

    @Override
    public void b(int n) {
        for (int i = 1; i < 2 * n + 1; i += 2) {
            System.out.print(i + " ");
        }
    }

    @Override
    public void c(int n) {
        for (int i = 1; i <= n; i++) {
            System.out.print(i * i + " ");
        }
    }

    @Override
    public void d(int n) {
        for (int i = 1; i <= n; i++) {
            System.out.print(i * i * i + " ");
        }
    }

    @Override
    public void e(int n) {
        int number = 1;
        for (int i = 0; i < n; i++) {
            System.out.print(number + " ");
            number *= -1;
        }
    }

    @Override
    public void f(int n) {
        int sign = 1;
        for (int i = 1; i <= n; i++) {
            System.out.print(i * sign + " ");
            sign *= -1;
        }
    }

    @Override
    public void g(int n) {
        int sign = 1;
        for (int i = 1; i <= n; i++) {
            System.out.print(i * i * sign + " ");
            sign *= -1;
        }
    }

    @Override
    public void h(int n) {
        for (int i = 1; i <= n; i++) {
            if (i % 2 == 0) {
                System.out.print(0 + " ");
            }
            else {
                System.out.print((i + 1) / 2 + " ");
            }
        }
    }

    @Override
    public void i(int n) {
        int acc = 1;
        for (int i = 1; i <= n; i++) {
            acc *= i;
            System.out.print(acc + " ");
        }
    }

    @Override
    public void j(int n) {
        int prev = 0;
        int cur = 1;
        for (int i = 0; i < n; i++) {
            System.out.print(cur + " ");
            int tmp = cur;
            cur += prev;
            prev = tmp;
        }
    }
}
