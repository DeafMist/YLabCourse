package lesson2.task5;

public class StatsAccumulatorImpl implements StatsAccumulator {
    private int min;
    private int max;
    private int sum;
    private int count;

    @Override
    public void add(int value) {
        if (this.count == 0) {
            this.min = value;
            this.max = value;
        }

        if (this.min > value) {
            this.min = value;
        }

        if (this.max < value) {
            this.max = value;
        }

        this.sum += value;
        this.count++;
    }

    @Override
    public int getMin() {
        return min;
    }

    @Override
    public int getMax() {
        return max;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public Double getAvg() {
        if (count == 0) {
            return null;
        }
        else {
            return (double) this.sum / this.count;
        }
    }
}
