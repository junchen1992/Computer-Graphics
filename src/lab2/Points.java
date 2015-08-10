package lab2;
import java.util.ArrayList;

/**
 * Little Wall Rock Climbing 
 * Copyright 2009 Eric McCreath GNU LGPL
 */

public class Points extends ArrayList<XYPoint> {
	public Points(int size) {
		super();
		for (int i = 0; i < size; i++) {
			this.add(new XYPoint(0.0, 0.0));
		}
	}

	public Points() {
		super();
	}

	public void translate(XYPoint t) {
		for (XYPoint p : this) {
			p.translate(t);
		}
	}

	public void zero() {
		for (XYPoint p : this) {
			p.zero();
		}
	}
}
