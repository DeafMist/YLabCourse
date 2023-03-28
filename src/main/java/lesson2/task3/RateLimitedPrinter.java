package lesson2.task3;

public class RateLimitedPrinter {
    private final int interval;
    private long lastCall;

    public RateLimitedPrinter(int interval) {
        this.interval = interval;
    }

    public void print(String message) {
        long curCall = System.currentTimeMillis();
        if (curCall - this.lastCall > this.interval) {
            System.out.println(message);
            this.lastCall = curCall;
        }
    }
}
