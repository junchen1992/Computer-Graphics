package lab2;
import java.util.ArrayList;


public class TestAngleSpring {
public static void main(String[] args) {
	
	XYPoint lh, le, ls, n, p, ln, lf;
	Points points = new Points();
	points.add(lh = new XYPoint(2.0,0.01));
	points.add(le = new XYPoint(1.0,0.0));
	points.add(ls = new XYPoint(0.0,0.0));
	
	double k = 0.2;
	
	points.add(n = new XYPoint(0.0,1.58));
	points.add(p = new XYPoint(0.0,1.0));
	
	
	points.add(ln = new XYPoint(-0.2,0.7));
	points.add(lf = new XYPoint(-0.2,0.2));
	
	
	Spring lRad = new Spring(0.33,k,le,lh,points);
	Spring lHum = new Spring(0.3,k,ls,le,points);
	
	
	Spring Lum1 = new Spring(0.58,k,n,p,points);
	Spring Lum2 = new Spring(0.58,k,p,n,points);
	 
	Spring lFem = new Spring(0.44,k,p,ln,points);
	Spring lTib = new Spring(0.5,k,ln,lf,points);
	
	Joint j =  new Joint(Lum1, lFem, -0.1, 0.1);
	
	
	XYPoint v1 = j.s1.vector();
	XYPoint v2 = j.s2.vector();

	System.out.println("v1 " + v1);
	System.out.println("v2 " + v2);
	
	double angle = v1.angle(v2);
	
	System.out.println(String.format("angle : %.2f",angle));
	System.out.println(String.format("mina : %.2f",j.mina));
		
		if (angle < j.mina) {
			XYPoint v2r = v2.rotate(-(angle-j.mina));
			System.out.println(String.format("new angle : %.2f",v1.angle(v2r)));
			points.get(j.s2.p2index).set(points.get(j.s2.p1index).add(v2r));
		} else if (angle > j.maxa) {
			XYPoint v2r = v2.rotate((j.maxa-angle));
			points.get(j.s2.p2index).set(points.get(j.s2.p1index).add(v2r));
		}
		
		
		System.out.println("lh " + lh);
		System.out.println("le " + le);
		System.out.println("ls " + ls);
		
		v1 = j.s1.vector();
	    v2 = j.s2.vector();

		System.out.println("v1 " + v1);
		System.out.println("v2 " + v2);
		
		angle = v1.angle(v2);
		System.out.println(String.format("angle : %.2f",angle));
}
}
