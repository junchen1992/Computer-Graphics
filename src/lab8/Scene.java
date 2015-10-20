package lab8;

import java.awt.Color;
import java.util.ArrayList;

/**
 * Scene Eric McCreath 2009
 */

public class Scene extends ArrayList<Sphere> {

	private static final long serialVersionUID = 1L;

	Color background = Color.black;

	/*
	 * Modify the 'raytrace' method in 'Scene.java' such that it's illumination
	 * model includes both diffuse and specular reflection. This may be from a
	 * single light source. You should adjust lighting parameters such that both
	 * the diffuse and specular effects are viewable. Make a note of the formula
	 * you used for your illumination model.
	 * 
	 * Note, you do not need to trace rays to the light sources in this part of
	 * the lab. Hence you should not see shadows.
	 */
	public Color raytrace(Ray r) {
		Sphere hit = null;
		Double mindis = null;

		for (Sphere s : this) {
			Double t = s.intersect(r);
			if (t != null) {
				if (mindis == null || t < mindis) {
					mindis = t;
					hit = s;
				}
			}
		}
		if (hit != null) {
			return hit.color;
		} else {
			return background;
		}
	}

}
