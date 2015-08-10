package lab2;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Random;

/**
 * Little Wall Rock Climbing Copyright 2009 Eric McCreath GNU LGPL
 */

public class Wall extends ArrayList<Hold> {

	AffineTransform trans;
	Dimension dim;
	XYPoint wallsize;

	public Wall(Dimension dim, XYPoint wallsize) {
		this.dim = dim;
		this.wallsize = wallsize;
		double scale = dim.height / wallsize.getY();
		trans = AffineTransform.getScaleInstance(scale, -scale);
		trans.translate(0, -wallsize.getY());

		Random r = new Random();
		for (int i = 0; i < 30; i++) {
			add(new Hold(r.nextDouble() * wallsize.getX(), r.nextDouble()
					* wallsize.getY()));
		}
	}

	public void draw(Graphics2D g) {
		g.setTransform(trans);
		for (Hold h : this) {
			h.draw(g);
		}
	}

}
