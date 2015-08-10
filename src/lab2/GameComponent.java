package lab2;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

/**
 * 
 * GameJComponent - This class enables double buffering for a simple game. To use
 * this class construct the GameJComponent of your desired size. Draw the required fixed
 * background using the Graphics object obtained from getBackgroundGraphics.
 * Then in the simulation loop: + clearOffscreen + either add images to the
 * offscreen with drawImage or draw using the Graphics object obtained via
 * getOffscreenGraphic() + drawOffscreen
 * 
 * This class also listens for key presses.  
 * 
 * 
 * @author Eric McCreath
 * 
 * Copyright 2005, 2009
 * 
 */
public class GameComponent extends JComponent {

	Dimension dim;

	private BufferedImage background;

	private BufferedImage offscreen;

	

	public GameComponent(Dimension d) {
		dim = d;
		
		this.setSize(dim);
		this.setPreferredSize(dim);
		background = new BufferedImage(dim.width, dim.height, BufferedImage.TYPE_INT_ARGB_PRE);
		offscreen = new BufferedImage(dim.width, dim.height, BufferedImage.TYPE_INT_ARGB_PRE);
		
		this.setFocusable(true);
		
	}

	public void clearOffscreen() {
		Graphics g = offscreen.getGraphics();
		g.drawImage(background, 0, 0, null);
	}

	public Graphics2D getBackgroundGraphics() {
		return background.createGraphics();
	}

	public Graphics2D getOffscreenGraphics() {
		return offscreen.createGraphics();
	}


	public void drawOffscreen() {
		Graphics g;
		g = this.getGraphics();
		g.drawImage(offscreen, 0, 0, null);
	}

	public void paint(Graphics g) {
		g.drawImage(offscreen, 0, 0, null);
	}

}