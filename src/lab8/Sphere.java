package lab8;

import java.awt.Color;

/**
 * Sphere Eric McCreath 2009
 */

public class Sphere {
	P3D center;
	double radius;
	Color color;

	public Sphere(P3D center, double radius, Color color) {
		super();
		this.center = center;
		this.radius = radius;
		this.color = color;
	}

	// intersect - returns either the distance to the sphere or null if the ray
	// misses
	public Double intersect(Ray ray) {

		// see http://en.wikipedia.org/wiki/Ray_tracing_(graphics)

		P3D v = ray.position.sub(center);
		double vdotd = v.dot(ray.direction);
		double insqrt = vdotd * vdotd - (v.dot(v) - radius * radius);
		if (insqrt < 0.0)
			return null;

		double t = -vdotd - Math.sqrt(insqrt); // we just take the smallest
		if (t < 0.0)
			return null; // only in a positive direction of shooting out from
							// the ray

		return t;
	}

	public String toString() {
		return "sphere center : " + center + " radius : " + radius + " color : " + color;
	}

}
