package lab2;
import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.Test;


public class XYPointTest {

	@Test
	public void testAngle() {
		XYPoint p = new XYPoint(1.0,0.0);
		assertEquals(0.0, p.angle(), 0.01);
		p = new XYPoint(0.0,1.0);
		assertEquals(Math.PI/2.0, p.angle(), 0.01);
		assertEquals(Math.PI, (new XYPoint(-10.0,0.0)).angle(), 0.01);
		assertEquals(-Math.PI/2.0, (new XYPoint(0.0,-1.0)).angle(), 0.01);
		assertEquals(Math.PI/4.0, (new XYPoint(1.0,1.0)).angle(), 0.01);
	}

	@Test
	public void testRotate() {
		XYPoint p = new XYPoint(1.0,0.0);
		p = p.rotate(Math.PI/2.0);
		assertEquals(0.0, p.x, 0.01);
		assertEquals(1.0, p.y, 0.01);
		
		p = new XYPoint(1.0,0.0);
		p = p.rotate(-Math.PI/2.0);
		assertEquals(0.0, p.x, 0.01);
		assertEquals(-1.0, p.y, 0.01);
		
		p = new XYPoint(1.0,0.0);
		p = p.rotate(0.0);
		assertEquals(1.0, p.x, 0.01);
		assertEquals(0.0, p.y, 0.01);
		
		p = new XYPoint(1.0,0.0);
		p = p.rotate(Math.PI/4.0);
		assertEquals(Math.sqrt(0.5), p.x, 0.01);
		assertEquals(Math.sqrt(0.5), p.y, 0.01);
		
		p = new XYPoint(1.0,-1.0);
		p = p.rotate(Math.PI/2.0);
		assertEquals(1.0, p.x, 0.01);
		assertEquals(1.0, p.y, 0.01);
		
		
		
		
	}

	@Test
	public void testAngleXYPoint() {
		XYPoint p1 = new XYPoint(1.0,0.0);
		XYPoint p2 = new XYPoint(0.0,1.0);
		assertEquals(Math.PI/2.0, p1.angle(p2), 0.01);
		
		p1 = new XYPoint(1.0,0.0);
		p2 = new XYPoint(0.0,-1.0);
		assertEquals(-Math.PI/2.0, p1.angle(p2), 0.01);
		
		p1 = new XYPoint(1.0,0.0);
		p2 = new XYPoint(1.0,0.0);
		assertEquals(0.0, p1.angle(p2), 0.01);
		
		p1 = new XYPoint(0.0,-1.0);
		p2 = new XYPoint(0.0,-1.0);
		assertEquals(0.0, p1.angle(p2), 0.01);
		
		p1 = new XYPoint(1.0,1.0);
		p2 = new XYPoint(1.0,0.0);
		assertEquals(-Math.PI/4.0, p1.angle(p2), 0.01);
		
		
	}

}
