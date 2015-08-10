package lab2;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

/**
 * Little Wall Rock Climbing Copyright 2009 Eric McCreath GNU LGPL
 */

public class Spring {
	double length;
	double k;
	XYPoint p1, p2;
	int p1index, p2index;

	public Spring(double length, double k, XYPoint p1, XYPoint p2, Points pts) {
		super();
		this.length = length;
		this.k = k;
		this.p1 = p1;
		this.p2 = p2;
		p1index = pts.indexOf(p1);
		p2index = pts.indexOf(p2);
	}

	public void draw(Graphics2D of) {
		of.draw(new Line2D.Double(p1.getX(), p1.getY(), p2.getX(), p2.getY()));
	}

	public XYPoint p1force() {
		XYPoint vec = p1.sub(p2);
		double len = vec.length();
		return vec.scale(((length - len) * k) / len);
	}

	public XYPoint p1distance() {
		XYPoint vec = p1.sub(p2);
		double len = vec.length();
		return vec.scale((-(length - len)) / len);
	}

	public XYPoint vector() {
		return p2.sub(p1);
	}

	public XYPoint p2force() {
		XYPoint vec = p1.sub(p2);
		double len = vec.length();
		return vec.scale((-(length - len) * k) / len);
	}
}
