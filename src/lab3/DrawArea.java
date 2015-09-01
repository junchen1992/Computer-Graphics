package lab3;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
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

public class DrawArea extends JComponent implements MouseMotionListener, MouseListener {

	private static final long serialVersionUID = 1L;
	private BufferedImage offscreen;
	Dimension dim;
	DrawIt drawit;

	// last starting point:
	private int x = -1, y = -1;

	public DrawArea(Dimension dim, DrawIt drawit) {
		this.setPreferredSize(dim);
		offscreen = new BufferedImage(dim.width, dim.height, BufferedImage.TYPE_INT_RGB);
		this.dim = dim;
		this.drawit = drawit;
		this.addMouseMotionListener(this);
		this.addMouseListener(this);

		clearOffscreen();
	}

	public void clearOffscreen() {
		Graphics2D g = offscreen.createGraphics();
		g.setColor(Color.white);
		g.fillRect(0, 0, dim.width, dim.height);

		// set initial thickness and transparency of the line:
		if (this.drawit.colorToolbar != null) {
			g.setStroke(new BasicStroke((float) this.drawit.colorToolbar.getThickness()));
			g.setColor(new Color(1, 0, 0, (float) (this.drawit.colorToolbar.getTransparency() * 1.0 / 100)));
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
		Graphics2D g = offscreen.createGraphics();
		g.setColor((Color) drawit.colorToolbar.getSelectCommand());

		// g.fill(new Ellipse2D.Double(m.getX() - 1.0, m.getY() - 1.0, 2.0,
		// 2.0));

		// change thickness of the line:
		g.setStroke(new BasicStroke((float) this.drawit.colorToolbar.getThickness()));

		// change transparency of the line:
		Color color = (Color) drawit.colorToolbar.getSelectCommand();

		// set the default color to white indicates an eraser:
		Color newColor = Color.WHITE;
		// color options:
		if (color.equals(Color.RED)) {
			newColor = new Color(1, 0, 0, (float) (this.drawit.colorToolbar.getTransparency() * 1.0 / 100));
		} else if (color.equals(Color.GREEN)) {
			newColor = new Color(0, 1, 0, (float) (this.drawit.colorToolbar.getTransparency() * 1.0 / 100));
		} else if (color.equals(Color.BLUE)) {
			newColor = new Color(0, 0, 1, (float) (this.drawit.colorToolbar.getTransparency() * 1.0 / 100));
		}

		// set transparency:
		g.setColor(newColor);

		if (newColor.equals(Color.WHITE)) {
			g.setStroke(new BasicStroke((float) 5));
		}

		// function options:
		if (((String) drawit.optionToolbar.getSelectCommand()).equals("SPRAY")) {
			spray(g, m.getX(), m.getY());
		} else if (((String) drawit.optionToolbar.getSelectCommand()).equals("FLOODFILL")) {
			if (this.offscreen.getRGB(x, y) == -1) {
				g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				floodFill(g, m.getX(), m.getY());
			}
		} else {
			g.drawLine(x, y, m.getX(), m.getY());
		}

		x = m.getX();
		y = m.getY();
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
		// System.out.println(this.offscreen.getRGB(x, y));
	}

	/**
	 * after the mouse released, reset the starting drawing point.
	 */
	public void mouseReleased(MouseEvent e) {
		this.x = -1;
		this.y = -1;
	}

	public void export(File file) {
		try {
			ImageIO.write(offscreen, "png", file);
		} catch (IOException e) {
			System.out.println("problem saving file");
		}
	}
}
