package lab3;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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

	public void mouseDragged(MouseEvent m) {
		Graphics2D g = offscreen.createGraphics();
		g.setColor((Color) drawit.colorToolbar.getSelectCommand());
		g.fill(new Ellipse2D.Double(m.getX() - 1.0, m.getY() - 1.0, 2.0, 2.0));
		drawOffscreen();
	}

	public void mouseMoved(MouseEvent m) {
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void export(File file) {
		try {
			ImageIO.write(offscreen, "png", file);
		} catch (IOException e) {
			System.out.println("problem saving file");
		}
	}
}
