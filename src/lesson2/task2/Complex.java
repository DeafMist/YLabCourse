package lesson2.task2;

public class Complex {
    private final double re;
    private final double im;

    public Complex(double re) {
        this(re, 0);
    }

    public Complex(double re, double im) {
        this.re = re;
        this.im = im;
    }

    public Complex sum(Complex c) {
        return new Complex(this.getRe() + c.getRe(), this.getIm() + c.getIm());
    }

    public Complex sub(Complex c) {
        return new Complex(this.getRe() - c.getRe(), this.getIm() - c.getIm());
    }

    public Complex mult(Complex c) {
        return new Complex(
                this.getRe() * c.getRe() - this.getIm() * c.getIm(),
                this.getRe() * c.getIm() + this.getIm() * c.getRe()
        );
    }

    public double abs() {
        return Math.sqrt(this.getRe() * this.getRe() + this.getIm() * this.getIm());
    }

    public double getRe() {
        return re;
    }

    public double getIm() {
        return im;
    }

    @Override
    public String toString() {
        return this.getRe() + " + " + this.getIm() + "i";
    }
}
