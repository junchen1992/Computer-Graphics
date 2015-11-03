package tut;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.beans.Transient;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class ModelingDemo extends JComponent implements Runnable,
		MouseMotionListener {

	/**
	 * ModelingDemo - Some code that give a demo of Hierarhical Modeling. Eric
	 * McCreath 2015 - GPL
	 */

	JFrame jframe;
	Point devMousePos = new Point(0,0);

	public ModelingDemo() {
		SwingUtilities.invokeLater(this);
	}

	public static void main(String[] args) {
		new ModelingDemo();
	}

	public void run() {
		jframe = new JFrame("Modeling Demo");
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.addMouseMotionListener(this);

		jframe.getContentPane().add(this);

		jframe.setVisible(true);
		jframe.pack();

	}

	@Override
	protected void paintComponent(Graphics g) {

		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		int dw = this.getWidth();
		int dh = this.getHeight();

		double uw = 400.0;
		double uh = 300.0;

		// g2.scale(w/400.0, h/300.0);

		if (dw * uh > dh * uw) {

			g2.translate((dw - uw * dh / uh) / 2.0, 0.0);
			g2.scale(dh / uh, dh / uh);
		} else {
			g2.translate(0.0, (dh - uh * dw / uw) / 2.0);
			g2.scale(dw / uw, dw / uw);
		}

		double boarder = 10.0;

		g2.setColor(Color.white);
		g2.fill(new Rectangle2D.Double(0.0, 0.0, uw, uh));
		g2.setColor(Color.red);
		double di = uh - 2.0 * boarder;
		g2.draw(new Ellipse2D.Double(uw / 2.0 - di / 2.0, boarder, di, di));

		try {
			Point2D userMousePos = (g2.getTransform().inverseTransform(
					devMousePos, null));
			g2.fill(new Ellipse2D.Double(userMousePos.getX() - 5.0,
					userMousePos.getY() - 5.0, 10.0, 10.0));
		} catch (NoninvertibleTransformException e1) {
		}

		g2.setColor(Color.blue);
		g2.draw(new Rectangle2D.Double(boarder, boarder, uw - 2.0 * boarder, uh
				- 2.0 * boarder));

		// load and draw an image
		BufferedImage image;
		try {
			image = ImageIO.read(new File(
					"/home/ericm/courses/cg/comp4610/compgraphicslogo.png"));

			AffineTransform af = new AffineTransform();
			Point2D vec = new Point2D.Double(uw / 2.0, uh / 2.0);
			double vecl = vec.distance(0.0, 0.0);
			double vect = Math.atan2(vec.getY(), vec.getX());
			af.translate(uw / 4.0, uh / 4.0);
			af.rotate(vect);
			af.scale(vecl / image.getWidth(), vecl / image.getWidth());

			// the below transformation can be used to replace the scale and
			// rotate and translation, transforms saving of the calculation of vector length and
			// angle.
			// af.concatenate(new AffineTransform(vec.getX()/image.getWidth(),
			// vec.getY()/image.getWidth(), -vec.getY()/image.getWidth(),
			// vec.getX()/image.getWidth(), uw/4.0 ,uh/4.0));

			af.translate(0.0, -image.getHeight() / 2.0);
			g2.drawImage(image, af, null);
		} catch (IOException e) {

		}

		// draw hello centered
		g2.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
		String str = "Hello!!!!!";
		int fw = g2.getFontMetrics().stringWidth(str);
		int fh = g2.getFontMetrics().getHeight();

		AffineTransform oa = g2.getTransform();

		g2.translate(uw / 4.0, uh / 4.0);
		flower(g2, 6);
		g2.translate(uw / 2.0, 0.0);
		flower(g2, 8);
		g2.translate(0.0, uh / 2.0);
		flower(g2, 4);
		g2.translate(-uw / 2.0, 0.0);
		flower(g2, 13);

		g2.setTransform(oa);

		g2.drawString(str, 10.0f + (380.0f - fw) / 2.0f, 10.0f + (280.0f - fh)
				/ 2.0f + fh);

	}

	private void flower(Graphics2D g2, int p) {
		g2.fill(new Ellipse2D.Double(-5.0, -5.0, 10.0, 10.0));
		Path2D pet = new Path2D.Double();
		Point2D si = new Point2D.Double(0.0, 5.0);
		Point2D so = new Point2D.Double(0.0, 30.0);
		AffineTransform r = AffineTransform
				.getRotateInstance(Math.PI * 2.0 / p);
		Point2D so1 = r.transform(so, null);
		Point2D si1 = r.transform(si, null);

		pet.moveTo(si.getX(), si.getY());
		pet.curveTo(so.getX(), so.getY(), so1.getX(), so1.getY(), si1.getX(),
				si1.getY());

		g2.draw(pet);

		for (int i = 0; i < p; i++) {
			g2.draw(pet);
			g2.rotate(Math.PI * 2.0 / p);
		}
	}

	@Override
	@Transient
	public Dimension getPreferredSize() {
		return new Dimension(400, 300);
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent me) {
		devMousePos = me.getPoint();
		repaint();
	}

}
