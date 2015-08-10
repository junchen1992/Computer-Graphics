package lab2;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

/**
 * Little Wall Rock Climbing 
 * Copyright 2009 Eric McCreath
 * GNU LGPL
 */


public class XYPoint {
	public double x, y;
	
	public XYPoint(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}
	
	public XYPoint scale(double c) {
		return new XYPoint(c*x,c*y);
	}
	
	public XYPoint add(XYPoint p) {
		return new XYPoint(p.x+x,p.y+y);
	}
	
	public XYPoint sub(XYPoint p) {
		return new XYPoint(x- p.x,y- p.y);
	}
	
	public double length() {
		return Math.sqrt(x*x+y*y);
	}
	
	public double distance(XYPoint p) {
		double xd = x - p.x;
		double yd = y - p.y;
		return Math.sqrt(xd*xd+yd*yd);
	}
	
	public void translate(XYPoint p) {
		x = p.x+x;
		y = p.y+y;
	}
	
	public void zero() {
		x = 0.0;
		y = 0.0;
	}
	
	public double dot(XYPoint v1) {
		return x*v1.x + y*v1.y;
	}

	public double angle() {
	/*	double l1 = length();
		double l2 = v2.length();
		return dot(v2)/(l1*l2);*/
		return  Math.atan2(this.y,this.x);
	}
	
	public XYPoint rotate(double angle) {
		
		double s = Math.sin(angle);
		double c = Math.cos(angle);
		
		return new XYPoint(x*c-y*s,x*s+y*c);

	}

	public void set(XYPoint p) {
		x = p.x;
		y = p.y;
	}

	public double angle(XYPoint v2) {
		double ta = angle();
		XYPoint v2t = v2.rotate(-ta);
		double r = v2t.angle();
		return ((r > Math.PI) ? r - 2.0 * Math.PI : r);
	}
	
	public String toString() {	
		return String.format("(%.2f, %.2f)", x, y);
	}
	
	public void draw(Graphics2D g) {
		g.draw(new Ellipse2D.Double(x-2.0, y-2.0, 4.0, 4.0));
	}
}
