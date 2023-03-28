package lesson2.task5;

public interface StatsAccumulator {
    void add(int value);
    int getMin();
    int getMax();
    int getCount();
    Double getAvg();
}
