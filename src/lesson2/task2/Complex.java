package lesson2.task2;

public class Complex {
    private final double realPart;
    private final double imaginaryPart;

    public Complex(double realPart) {
        this(realPart, 0);
    }

    public Complex(double realPart, double imaginaryPart) {
        this.realPart = realPart;
        this.imaginaryPart = imaginaryPart;
    }

    public Complex sum(Complex c) {
        return new Complex(
                this.getRealPart() + c.getRealPart(),
                this.getImaginaryPart() + c.getImaginaryPart());
    }

    public Complex sub(Complex c) {
        return new Complex(
                this.getRealPart() - c.getRealPart(),
                this.getImaginaryPart() - c.getImaginaryPart());
    }

    public Complex mult(Complex c) {
        return new Complex(
                this.getRealPart() * c.getRealPart() - this.getImaginaryPart() * c.getImaginaryPart(),
                this.getRealPart() * c.getImaginaryPart() + this.getImaginaryPart() * c.getRealPart()
        );
    }

    public double abs() {
        return Math.sqrt(this.getRealPart() * this.getRealPart() + this.getImaginaryPart() * this.getImaginaryPart());
    }

    public double getRealPart() {
        return realPart;
    }

    public double getImaginaryPart() {
        return imaginaryPart;
    }

    @Override
    public String toString() {
        return this.getRealPart() + " + " + this.getImaginaryPart() + "i";
    }
}
