package lab2;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

/**
 * Little Wall Rock Climbing 
 * Copyright 2009 Eric McCreath
 * GNU LGPL
 */

public class Hold {
	double x,y;
	static double w = 0.1;
	public Hold(double x, double y) {
		this.x = x;
		this.y = y;
	}
	public void draw(Graphics2D g) {
		g.setColor(Color.blue);
		
		g.fill(new Ellipse2D.Double(x-w/2.0,y-w/2.0,w,w));
		//g.fillOval((int) Math.round(x-2.0), (int) Math.round(y-2.0), 4, 4);
	}

}
