package julia;

import static org.junit.Assert.*;

import org.junit.Test;


public class ComplexTest {

	@Test
	public void init() {
		Complex c = new Complex(1, 2);
		assertEquals(1, c.getReal(), .1);
		assertEquals(2, c.getImaginary(), .2);
	}
	
	@Test
	public void arith() {
		Complex c = new Complex(1, 2);
		Complex d = new Complex(100, 1000);
		assertEquals(101, c.plus(d).getReal(), .1);
		assertEquals(1002, d.plus(c).getImaginary(), .1);
		//
		// Old values should not have changed
		//
		assertEquals(1, c.getReal(), .1);
		assertEquals(2, c.getImaginary(), .1);
		
		assertEquals(0, c.minus(c).getReal(), .1);
		assertEquals(998, d.minus(c).getImaginary(), .1);
		assertEquals(-99, c.minus(d).getReal(), .1);
		
		Complex i = new Complex(0,1);
		assertEquals(-1, i.times(i).getReal(), .1);
		assertEquals(0,  i.times(i).getImaginary(), .1);
	}
	
	@Test
	public void abs() {
		Complex c = new Complex(3,4);
		assertEquals(5, c.abs(), .1);
	}
	
	@Test
	public void print() {
		Complex c = new Complex(1,2);
		System.out.println(c);
		System.out.println(c.times(c));
	}
	
}
