package vectordrawing;

import java.util.ArrayList;

public class Lines {

	ArrayList<Line> lines;

	public Lines() {
		super();
		this.lines = new ArrayList<Line>();
	}

	public void addLine(int x0, int y0, int xn, int yn) {
		Line line = new Line(x0, y0, xn, yn);
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

	public ArrayList<Line> getLines() {
		return this.lines;
	}

}
