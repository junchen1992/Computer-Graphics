package lab8;

/**
 * P3D Eric McCreath 2009
 */

public class P3D {
	final double x, y, z;

	public P3D(double x, double y, double z) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public P3D sub(P3D p) {
		return new P3D(x - p.x, y - p.y, z - p.z);
	}

	public P3D add(P3D p) {
		return new P3D(x + p.x, y + p.y, z + p.z);
	}

	public double dot(P3D p) {
		return x * p.x + y * p.y + z * p.z;
	}

	public P3D scale(double s) {
		return new P3D(x * s, y * s, z * s);
	}

	public P3D normalize() {
		return scale(1.0 / length());
	}

	public double length() {
		return Math.sqrt(dot(this));
	}

	@Override
	public String toString() {

		return String.format("(%.4f,%.4f,%.4f)", x, y, z);
	}
}
