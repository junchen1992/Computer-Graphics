package lab2;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

/*
 * 
 * Copyright 2009 Eric McCreath 
 * 
 * This file is part of Circle.
 * 
 * Circle is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Circle is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Circle.  If not, see <http://www.gnu.org/licenses/>.
 *
 * 
 */

public class Circle {
	XYPoint c;
	double r;

	double l[] = { 1.0, 1.0 };

	public Circle(XYPoint c, double r) {
		this.c = c;
		this.r = r;
	}

	public XYPoint[] intersect(Circle b) {
		double d = c.distance(b.c);
		if (d >= r + b.r || r + d < b.r || b.r + d < r)
			return null;

		double ax2 = c.x * c.x;
		double ay2 = c.y * c.y;
		double bx2 = b.c.x * b.c.x;
		double by2 = b.c.y * b.c.y;
		double ar2 = r * r;
		double br2 = b.r * b.r;
		double lx = 2.0 * (b.c.x - c.x);
		double ly = 2.0 * (b.c.y - c.y);
		double l = ar2 - br2 - ax2 - ay2 + bx2 + by2;

		double qa = lx * lx + ly * ly;
		double qb = 2.0 * (lx * ly * c.y - lx * l - c.x * ly * ly);
		double qc = ax2 * ly * ly + l * l - 2.0 * l * ly * c.y + ay2 * ly * ly
				- ar2 * ly * ly;
		double sqrt = Math.sqrt(qb * qb - 4.0 * qa * qc);

		double p1x = (-qb + sqrt) / (2.0 * qa);
		double p2x = (-qb - sqrt) / (2.0 * qa);

		double qyb = 2.0 * (lx * ly * c.x - ly * l - c.y * lx * lx);
		double qyc = ay2 * lx * lx + l * l - 2.0 * l * lx * c.x + ax2 * lx * lx
				- ar2 * lx * lx;

		double sqrty = Math.sqrt(qyb * qyb - 4.0 * qa * qyc);

		double p1y = (-qyb + sqrty) / (2.0 * qa);
		double p2y = (-qyb - sqrty) / (2.0 * qa);

		if ((lx > 0.0 && ly > 0.0) || (lx < 0.0 && ly < 0.0)) {
			double swap = p1y;
			p1y = p2y;
			p2y = swap;
		}

		XYPoint p1 = new XYPoint(p1x, p1y);
		XYPoint p2 = new XYPoint(p2x, p2y);
		XYPoint res[] = { p1, p2 };
		return res;
	}

	void draw(Graphics2D g) {
		g.draw(new Ellipse2D.Double(c.x - r, c.y - r, 2.0 * r, 2.0 * r));
	}

}
