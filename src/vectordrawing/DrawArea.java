package vectordrawing;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

/*
 * DrawArea - a simple JComponent for drawing.  The "offscreen" BufferedImage is 
 * used to draw to,  this image is then used to paint the component.
 * Eric McCreath 2009 2015
 */

public class DrawArea extends JComponent implements MouseMotionListener,
		MouseListener {

	private static final long serialVersionUID = 1L;
	private BufferedImage offscreen;
	Dimension dim;
	DrawIt drawit;

	Lines lines;
	int linesCount = 0;
	boolean isDragging = false;
	Line selectedLine = null;

	// last starting point:
	private int x = -1, y = -1;
	// pos to be cleared:
	int xClear = 0, yClear = 0;

	public DrawArea(Dimension dim, DrawIt drawit) {
		this.setPreferredSize(dim);
		offscreen = new BufferedImage(dim.width, dim.height,
				BufferedImage.TYPE_INT_RGB);
		this.dim = dim;
		this.drawit = drawit;
		this.addMouseMotionListener(this);
		this.addMouseListener(this);

		this.lines = new Lines();
		this.linesCount = this.lines.getLines().size();

		clearOffscreen();
	}

	public void clearOffscreen() {
		Graphics2D g = offscreen.createGraphics();
		g.setColor(Color.white);
		g.fillRect(0, 0, dim.width, dim.height);

		// set initial thickness and transparency of the line:
		if (this.drawit.colorToolbar != null) {
			g.setStroke(new BasicStroke((float) this.drawit.colorToolbar
					.getThickness()));
			g.setColor(new Color(1, 0, 0, (float) (this.drawit.colorToolbar
					.getTransparency() * 1.0 / 100)));
		}

		repaint();
	}

	public Graphics2D getOffscreenGraphics() {
		return offscreen.createGraphics();
	}

	public void drawOffscreen() {
		repaint();
	}

	protected void paintComponent(Graphics g) {
		g.drawImage(offscreen, 0, 0, null);
	}

	/**
	 * when dragging the mouse, draw line on screen.
	 */
	public void mouseDragged(MouseEvent m) {
		// do not draw new line when in editing-line mode:
		if (drawit.colorToolbar.isEditingMode()) {
			if (this.selectedLine == null) {
				return;
			}

			System.out.println("a line selected");
			/*
			 * double dist1 = Point2D.distance(this.selectedLine.getX0(),
			 * this.selectedLine.getY0(), m.getX(), m.getY()); double dist2 =
			 * Point2D.distance(this.selectedLine.getXn(),
			 * this.selectedLine.getYn(), m.getX(), m.getY()); if (dist1 <
			 * dist2) { this.x = this.selectedLine.getXn(); this.y =
			 * this.selectedLine.getYn(); } else { this.x =
			 * this.selectedLine.getX0(); this.y = this.selectedLine.getY0(); }
			 */
			lines.deleteLastLine();

		}

		// this.selectedLine = null;

		// clear the current screen:
		this.clearOffscreen();

		Graphics2D g = offscreen.createGraphics();
		g.setColor((Color) drawit.colorToolbar.getSelectCommand());

		// add move boxes to the ends of the line:
		if (drawit.colorToolbar.isEditingMode() && this.selectedLine != null) {
			g.drawRect(this.selectedLine.getX0() - 5,
					this.selectedLine.getY0() - 5, 10, 10);
			g.drawRect(this.selectedLine.getXn() - 5,
					this.selectedLine.getYn() - 5, 10, 10);
		}

		// g.fill(new Ellipse2D.Double(m.getX() - 1.0, m.getY() - 1.0, 2.0,
		// 2.0));

		// change thickness of the line:
		g.setStroke(new BasicStroke((float) this.drawit.colorToolbar
				.getThickness()));

		// change transparency of the line:
		Color color = (Color) drawit.colorToolbar.getSelectCommand();

		// set the default color to white indicates an eraser:
		Color newColor = Color.WHITE;
		// color options:
		if (color.equals(Color.RED)) {
			newColor = new Color(
					1,
					0,
					0,
					(float) (this.drawit.colorToolbar.getTransparency() * 1.0 / 100));
		} else if (color.equals(Color.GREEN)) {
			newColor = new Color(
					0,
					1,
					0,
					(float) (this.drawit.colorToolbar.getTransparency() * 1.0 / 100));
		} else if (color.equals(Color.BLUE)) {
			newColor = new Color(
					0,
					0,
					1,
					(float) (this.drawit.colorToolbar.getTransparency() * 1.0 / 100));
		}

		// set transparency:
		g.setColor(newColor);

		int xn = m.getX();
		int yn = m.getY();

		if (lines.getLines().size() == this.linesCount + 1) {
			lines.deleteLastLine();
		}

		if (drawit.colorToolbar.isEditingMode() && this.selectedLine != null) {
			lines.addLine(x, y, xn, yn, this.selectedLine.getColor(),
					this.selectedLine.getStroke());
		} else {
			lines.addLine(x, y, xn, yn, g.getColor(), g.getStroke());
		}

		// clear previous move boxes:
		Color bg = g.getBackground();
		g.setBackground(Color.WHITE);
		g.clearRect(xClear - 6, yClear - 6, 12, 12);
		g.setBackground(bg);

		Line line = null;
		for (int i = 0; i < lines.getLines().size(); i++) {
			line = lines.getLines().get(i);
			g.setColor(line.getColor());
			g.setStroke(line.getStroke());
			g.drawLine(line.getX0(), line.getY0(), line.getXn(), line.getYn());
		}

		if (drawit.colorToolbar.isEditingMode() && this.selectedLine != null) {
			// g.clearRect(this.x, this.y, 4, 4);
			g.drawRect(m.getX() - 5, m.getY() - 5, 10, 10);
		}

		drawOffscreen();
	}

	/**
	 * 
	 */
	public void smudge() {
		// TODO!
	}

	/**
	 * spray paint at point (x, y).
	 */
	public void spray(Graphics2D g, int x, int y) {
		// sample using RNG:
		Random random = new Random();

		// inner:
		int innerPoints = 35;
		int innerBound = 5;
		int xRng1 = 0, yRng1 = 0;
		for (int i = 0; i < innerPoints; i++) {
			xRng1 = x + random.nextInt(innerBound);
			yRng1 = y + random.nextInt(innerBound);
			g.drawLine(xRng1, yRng1, xRng1, yRng1);
		}

		// outer:
		int outerPoints = 12;
		int outerBound = 10;
		int xRng2 = 0, yRng2 = 0;
		for (int j = 0; j < outerPoints; j++) {
			xRng2 = x + random.nextInt(outerBound);
			yRng2 = y + random.nextInt(outerBound);
			g.drawLine(xRng2, yRng2, xRng2, yRng2);
		}
	}

	/**
	 * area flood fill in a selected area specified by a given point. b
	 * 
	 * Bug exists using recursion: StackOverflow.
	 * 
	 * @param g
	 */
	public void floodFill(Graphics2D g, int x, int y) {
		// TODO!
		g.drawLine(x, y, x, y);
		if (this.offscreen.getRGB(x + 1, y) == -1) {
			floodFill(g, x + 1, y);
		}
		if (this.offscreen.getRGB(x - 1, y) == -1) {
			floodFill(g, x - 1, y);
		}
		if (this.offscreen.getRGB(x, y + 1) == -1) {
			floodFill(g, x, y + 1);
		}
		if (this.offscreen.getRGB(x, y - 1) == -1) {
			floodFill(g, x, y - 1);
		}
	}

	public void mouseMoved(MouseEvent m) {
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	/**
	 * set the point when the mouse presses as the drawing starting point.
	 */
	public void mousePressed(MouseEvent e) {
		this.x = e.getX();
		this.y = e.getY();
		this.isDragging = true;
		if (drawit.colorToolbar.isEditingMode()) {
			Line nearestLine = lines.nearestLine(x, y);
			double dist = Line2D.ptLineDist(nearestLine.getX0(),
					nearestLine.getY0(), nearestLine.getXn(),
					nearestLine.getYn(), x, y);
			System.out.println("dist = " + dist);
			double threshold = 7.0;
			if (dist < threshold) {
				this.selectedLine = nearestLine;
				lines.getLines().remove(nearestLine);
				lines.getLines().add(nearestLine);

				double dist1 = Point2D.distance(this.selectedLine.getX0(),
						this.selectedLine.getY0(), e.getX(), e.getY());
				double dist2 = Point2D.distance(this.selectedLine.getXn(),
						this.selectedLine.getYn(), e.getX(), e.getY());
				if (dist1 < dist2) {
					this.x = this.selectedLine.getXn();
					this.y = this.selectedLine.getYn();
					this.xClear = this.selectedLine.getX0();
					this.yClear = this.selectedLine.getY0();
				} else {
					this.x = this.selectedLine.getX0();
					this.y = this.selectedLine.getY0();
					this.xClear = this.selectedLine.getXn();
					this.yClear = this.selectedLine.getYn();
				}

				Graphics2D g = offscreen.createGraphics();

				// clear previous move boxes:
				Color bg = g.getBackground();
				g.setBackground(Color.WHITE);
				Line line = null;
				for (int i = 0; i < lines.getLines().size(); i++) {
					line = lines.getLines().get(i);
					g.clearRect(line.getX0() - 6, line.getY0() - 6, 12, 12);
					g.clearRect(line.getXn() - 6, line.getYn() - 6, 12, 12);
				}
				g.setBackground(bg);

				// add move boxes:

				g.setColor(this.selectedLine.getColor());
				g.drawRect(this.selectedLine.getX0() - 5,
						this.selectedLine.getY0() - 5, 10, 10);
				g.drawRect(this.selectedLine.getXn() - 5,
						this.selectedLine.getYn() - 5, 10, 10);

				drawOffscreen();

			} else {
				this.selectedLine = null;
			}
		} else {
			this.selectedLine = null;
		}
	}

	/**
	 * after the mouse released, reset the starting drawing point.
	 */
	public void mouseReleased(MouseEvent e) {
		this.x = -1;
		this.y = -1;
		if (!drawit.colorToolbar.isEditingMode()) {
			this.linesCount++;
		}
		this.isDragging = false;
	}

	public void export(File file) {
		try {
			ImageIO.write(offscreen, "png", file);
		} catch (IOException e) {
			System.out.println("problem saving file");
		}
	}
}
