package lab8;

/**
 * View Eric McCreath 2009
 */

public class View {
	P3D camera;
	P3D direction; // to the viewing plain (distance and direction are
					// important)
	P3D down; // vector down the side of the viewing plain
	P3D across; // across the top of the viewing plain

	public View(P3D camera, P3D direction, P3D across, P3D down) {
		super();
		this.camera = camera;
		this.direction = direction;
		this.down = down;
		this.across = across;
	}

}
