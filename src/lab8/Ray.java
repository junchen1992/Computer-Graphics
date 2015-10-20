package lab8;

/**
 * Ray Eric McCreath 2009
 */

public class Ray {
	P3D position;
	P3D direction; // normalized to length 1

	public Ray(P3D position, P3D direction) {
		super();
		this.position = position;
		this.direction = direction;
	}

	@Override
	public String toString() {

		return "pos : " + position + " dir : " + direction;
	}

}
