package lab2;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.Timer;

public class LittleWall implements ActionListener {

	/**
	 * Little Wall Rock Climbing Copyright 2009 Eric McCreath GNU LGPL
	 */

	final static Dimension dim = new Dimension(800, 600);
	final static XYPoint wallsize = new XYPoint(8.0, 6.0);

	JFrame jframe;
	GameComponent canvas;
	Wall wall;
	PlayerSpring player;
	Timer timer;

	public LittleWall() {
		jframe = new JFrame("Little Wall");
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		canvas = new GameComponent(dim);
		jframe.getContentPane().add(canvas);
		jframe.pack();
		jframe.setVisible(true);
	}

	public static void main(String[] args) throws InterruptedException {
		LittleWall lw = new LittleWall();
		lw.drawTitleScreen();
		lw.startRunningGame();
	}

	private void startRunningGame() {
		wall = new Wall(dim, wallsize);
		wall.draw(canvas.getBackgroundGraphics());
		player = new PlayerSpring(wall);
		canvas.addMouseMotionListener(player);
		canvas.addKeyListener(player);

		timer = new Timer(1000 / 15, this);
		timer.start();
	}

	/**
	 * Visually improve the intro screen by adding: some sort of logo that you
	 * have constructed (could be an arrow pointing up, a rock, or anything you
	 * like), an 'interesting' large font for the title, game play instructions,
	 * and some sort of image. To do this you will need to modify the
	 * 'drawTitleScreen' method in 'LittleWall.java'.
	 * 
	 * @throws InterruptedException
	 */
	private void drawTitleScreen() throws InterruptedException {
		Graphics2D bg = canvas.getBackgroundGraphics();
		bg.setColor(Color.white);
		bg.fillRect(0, 0, dim.width, dim.height);
		canvas.clearOffscreen();

		Graphics2D os = canvas.getOffscreenGraphics();
		os.setColor(Color.black);
		// os.drawString("Little Wall Climbing", 100, 100);

		// add your code here to make the title screen more interesting.

		// set title and game instructions:
		os.setFont(new Font(Font.MONOSPACED, Font.BOLD, 50));
		os.drawString("Little Wall Climbing", 100, 100);
		os.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 30));
		os.drawString("*******Game Instructions*******", 50, 150);
		os.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 20));
		os.drawString("Holding down one of the following key, and move your mouse:", 50, 180);
		os.drawString("(1) A: move left hand.", 50, 200);
		os.drawString("(2) S: move right hand.", 50, 220);
		os.drawString("(3) Z: move left foot.", 50, 240);
		os.drawString("(4) X: move right foot.", 50, 260);
		os.drawString("(5) space: move entire body.", 50, 280);
		// System.out.println(os.getFont());

		// add a logo:
		os.setColor(Color.magenta);
		os.drawArc(0, 0, 100, 100, 0, 360);
		os.drawArc(25, 25, 50, 50, 0, 360);
		os.drawLine(50, 50, 50, 12);
		os.drawLine(45, 19, 50, 12);
		os.drawLine(55, 19, 50, 12);
		os.fillArc(47, 47, 5, 5, 0, 360);

		// add some sort of image:
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File("climber.png"));
		} catch (IOException e) {
		}
		AffineTransform trans = new AffineTransform(1.0, 0.0, 0.0, 1.0, 400, 200);
		trans.translate(-400, -200);
		double theta = Math.PI / 10;
		trans.rotate(theta);
		trans.translate(400, 200);
		trans.translate(100, -150);
		os.drawImage(img, trans, null);

		canvas.drawOffscreen();
		Thread.sleep(1000);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == timer) {
			canvas.clearOffscreen();
			Graphics2D of = canvas.getOffscreenGraphics();
			player.draw(of);
			player.update(canvas, wall);
			canvas.drawOffscreen();
		}
	}
}
