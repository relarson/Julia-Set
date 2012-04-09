package julia;

//import studio4.Complex;

/**
 * Complex number, an immutable class
 * @author cytron, edited by Ross Larson
 *
 */
public class Complex {
	
	private double real, imaginary;

	/**
	 * A complex number
	 * @param real part
	 * @param imaginary part
	 */
	public Complex(double real, double imaginary) {
		this.real = real;
		this.imaginary = imaginary;
	}

	public double getReal() {
		return real;
	}

	public double getImaginary() {
		return imaginary;
	}
	
	/**
	 * Return a new Complex number that is the sum of this
	 *   and the other one
	 * @param other
	 * @return sum of this and the other Complex numbers
	 */
	public Complex plus (Complex other) {
		return new Complex(this.real + other.getReal(), this.imaginary + other.getImaginary());
	}

	/**
	 * Return the difference of this and the other Complex numbers
	 * @param other
	 * @return this - other
	 */
	public Complex minus(Complex other) {
		return new Complex(this.real - other.getReal(), this.imaginary - other.getImaginary());
	}
	
	/**
	 * Return a new Complex number that is the product of this
	 *   and the other number.
	 * @param other
	 * @return this * other
	 */
	public Complex times(Complex other) {
		double a, b;
		a = (this.getReal() * other.getReal()) - (this.getImaginary() * other.getImaginary());
		b = (this.getReal() * other.getImaginary()) + (this.getImaginary() * other.getReal());
		return new Complex(a, b);
	}
	
	/**
	 * Return the distance between (0,0) and this Complex number
	 * @return distance from (0,0)
	 */
	public double abs() { 
		return Math.sqrt(((this.imaginary) * (this.imaginary)) + ((this.real) * (this.real)));
	}
	
	public String toString() {
		return "("+ getReal() + " + " + getImaginary() + "i)";
	}
}
