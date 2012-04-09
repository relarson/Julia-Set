package julia;

import static org.junit.Assert.*;

import org.junit.Test;


public class ComplexRasterTest {
	
	final private int size = 256;
	final private Complex ul = new Complex(-2, 2);
	final private Complex lr = new Complex(2, -2);
	ComplexRaster cr = new ComplexRaster(ul, lr, size, size);
	
	private void checkComplex(double r, double i, Complex c) {
		assertEquals(r, c.getReal(),      .1);
		assertEquals(i, c.getImaginary(), .1);
	}
	
	@Test
	public void testCorners() {
		int max = size-1;
		Complex center = cr.getPoint(max/2.0, max/2.0);
		Complex ul     = cr.getPoint(0,0);
		Complex ur     = cr.getPoint(max, 0);
		Complex lr     = cr.getPoint(max, max);
		Complex ll     = cr.getPoint(0, max);
		
		checkComplex( 0,  0, center);
		checkComplex(-2,  2, ul);
		checkComplex( 2,  2, ur);
		checkComplex( 2, -2, lr);
		checkComplex(-2, -2, ll);
	}
	
	@Test
	public void testMiddle() {
		
		for (int i=0; i < size; ++i) {
			for (int j=0; j < size; ++j) {
				Complex p = cr.getPoint(i,j);
				assertTrue(-2 <= p.getReal() && p.getReal() <= 2);
				assertTrue(-2 <= p.getImaginary() && p.getImaginary() <= 2);
			}
		}
		
		
	}

}
