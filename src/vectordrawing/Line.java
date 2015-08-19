package vectordrawing;

import java.awt.Color;
import java.awt.Stroke;

public class Line {

	private int x0;
	private int y0;
	private int xn;
	private int yn;

	private Color color;
	private Stroke stroke;

	public Line() {

	}

	public Line(int x0, int y0, int xn, int yn, Color c, Stroke s) {
		super();
		this.x0 = x0;
		this.y0 = y0;
		this.xn = xn;
		this.yn = yn;
		this.color = c;
		this.stroke = s;
	}

	public int getX0() {
		return x0;
	}

	public void setX0(int x0) {
		this.x0 = x0;
	}

	public int getY0() {
		return y0;
	}

	public void setY0(int y0) {
		this.y0 = y0;
	}

	public int getXn() {
		return xn;
	}

	public void setXn(int xn) {
		this.xn = xn;
	}

	public int getYn() {
		return yn;
	}

	public void setYn(int yn) {
		this.yn = yn;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Stroke getStroke() {
		return stroke;
	}

	public void setStroke(Stroke stroke) {
		this.stroke = stroke;
	}

}
