package vectordrawing;

import java.awt.Color;
import java.awt.Stroke;
import java.awt.geom.Line2D;
import java.util.ArrayList;

public class Lines {

	ArrayList<Line> lines;

	public Lines() {
		super();
		this.lines = new ArrayList<Line>();
	}

	public void addLine(int x0, int y0, int xn, int yn, Color c, Stroke s) {
		Line line = new Line(x0, y0, xn, yn, c, s);
		lines.add(line);
	}

	public void deleteLastLine() {
		lines.remove(lines.size() - 1);
	}

	public Line getLastLine() {
		return this.lines.get(lines.size() - 1);
	}

	public void deleteLine() {

	}

	public void reposLine() {

	}

	/**
	 * 
	 * @param px
	 * @param py
	 * @return the line that is nearest to the given point (px, py).
	 */
	public Line nearestLine(double px, double py) {
		double minDist = 1000;
		Line line = null;
		Line res = null;
		for (int i = 0; i < lines.size(); i++) {
			line = lines.get(i);
			double dist = Line2D.ptLineDist(line.getX0(), line.getY0(),
					line.getXn(), line.getYn(), px, py);
			if (dist < minDist) {
				minDist = dist;
				res = line;
			}
		}
		return res;
	}

	public ArrayList<Line> getLines() {
		return this.lines;
	}

}
