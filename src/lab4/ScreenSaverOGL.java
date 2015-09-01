package lab4;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLException;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.gl2.GLUT;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

public class ScreenSaverOGL implements GLEventListener {

	/**
	 * ScreenSaverOGL - this is a simple screen saver that uses JOGL2 Eric
	 * McCreath 2009, 2011, 2015
	 * 
	 * You need to include the jogl jar files (gluegen-rt.jar and jogl.jar). In
	 * eclipse use "add external jars" in Project->Properties->Libaries
	 * otherwise make certain they are in the class path. In the current linux
	 * computers there files are in the /usr/share/java directory.
	 * 
	 * If you are executing from the command line then something like: javac -cp
	 * .:/usr/share/java/jogl2.jar:/usr/share/java/gluegen2-2.2.4-rt.jar
	 * ScreenSaverOGL.java java -cp
	 * .:/usr/share/java/jogl2.jar:/usr/share/java/gluegen2-2.2.4-rt.jar
	 * ScreenSaverOGL should work.
	 * 
	 * On our lab machine you may also need to check you are using Java 7. You
	 * can run it directly using: /usr/lib/jvm/java-7-openjdk-amd64/bin/javac
	 * and /usr/lib/jvm/java-7-openjdk-amd64/bin/java
	 * 
	 */

	JFrame jf;
	GLCanvas canvas;
	GLProfile profile;
	GLCapabilities caps;
	Dimension dim = new Dimension(800, 600);
	FPSAnimator animator;

	float xpos;
	float xvel;

	/***/
	float ypos;
	float yvel;
	private final static String imageSrc1 = "/Users/Jason/git/Computer-Graphics/index.png";
	private final static String imageSrc2 = "/students/u5485230/git/Computer-Graphics/climber.png";
	Texture cgtexture;

	public ScreenSaverOGL() {
		jf = new JFrame();
		profile = GLProfile.getDefault();
		caps = new GLCapabilities(profile);
		canvas = new GLCanvas(caps);
		canvas.addGLEventListener(this);
		canvas.requestFocusInWindow();
		jf.getContentPane().add(canvas);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setVisible(true);
		jf.setPreferredSize(dim);
		jf.pack();
		animator = new FPSAnimator(canvas, 20);
		xpos = 100.0f;

		/***/
		ypos = 100.0f;
		yvel = 2.0f;

		xvel = 1.0f;
		animator.start();
	}

	public static void main(String[] args) {
		new ScreenSaverOGL();
	}

	@SuppressWarnings("unused")
	@Override
	public void init(GLAutoDrawable dr) { // set up openGL for 2D drawing
		GL2 gl2 = dr.getGL().getGL2();
		GLU glu = new GLU();
		GLUT glut = new GLUT();
		gl2.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		gl2.glMatrixMode(GL2.GL_PROJECTION);
		gl2.glLoadIdentity();
		glu.gluOrtho2D(0.0, dim.getWidth(), 0.0, dim.getHeight());
		gl2.glMatrixMode(GL2.GL_MODELVIEW);
		gl2.glLoadIdentity();

		// !TODO
		// load an image:
		try {
			cgtexture = TextureIO.newTexture(new File(imageSrc1), true);
			cgtexture.enable(gl2);
			cgtexture.bind(gl2);
		} catch (GLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unused")
	@Override
	public void display(GLAutoDrawable dr) { // clear the screen and draw "Save
												// the Screens"
		GL2 gl2 = dr.getGL().getGL2();
		GLU glu = new GLU();
		GLUT glut = new GLUT();

		gl2.glClear(GL.GL_COLOR_BUFFER_BIT);

		// step 1:
		// draw a line:
		gl2.glColor3f(0.0f, 1.0f, 0.0f);
		gl2.glBegin(GL.GL_LINES);
		gl2.glVertex2f(xpos + 180, 15);
		gl2.glVertex2f(xpos + 10, 145);
		gl2.glEnd();

		// draw a line:
		gl2.glColor3f(0.0f, 1.0f, 0.0f);
		gl2.glBegin(GL.GL_LINES);
		gl2.glVertex2f(xpos + 180, 15);
		gl2.glVertex2f(xpos + 10, 15);
		gl2.glEnd();

		// draw a filled polygon:
		gl2.glColor3f(0.0f, 0.0f, 1.0f);
		gl2.glBegin(GL2.GL_POLYGON);
		gl2.glVertex2f(xpos + 100, 100);
		gl2.glVertex2f(xpos + 100, 200);
		gl2.glVertex2f(xpos + 200, 200);
		gl2.glVertex2f(xpos + 200, 100);
		gl2.glEnd();

		// draw a filled polygon:
		gl2.glColor3f(0.5f, 0.5f, 0.5f);
		gl2.glBegin(GL2.GL_POLYGON);
		gl2.glVertex2f(xpos + 150, 100 + ypos);
		gl2.glVertex2f(xpos + 200, 100 + ypos);
		gl2.glVertex2f(xpos + 150, 50 + ypos);
		gl2.glVertex2f(xpos + 200, 50 + ypos);
		gl2.glEnd();

		// step 2:
		cgtexture.enable(gl2);
		cgtexture.bind(gl2);

		gl2.glColor3f(1.0f, 1.0f, 1.0f);
		gl2.glPushMatrix();
		gl2.glTranslated(xpos, 100.0 + ypos, 0.0);
		gl2.glRotated(xpos - 100.0, 0.0, 0.0, 1.0);
		gl2.glBegin(GL2.GL_POLYGON);

		gl2.glTexCoord2d(0.0, 0.0);
		gl2.glVertex2d(10.0, 10.0);
		gl2.glTexCoord2d(1.0, 0.0);
		gl2.glVertex2d(110.0, 10.0);
		gl2.glTexCoord2d(1.0, 1.0);
		gl2.glVertex2d(110.0, 40.0);
		gl2.glTexCoord2d(0.0, 1.0);
		gl2.glVertex2d(10.0, 40.0);

		gl2.glEnd();
		gl2.glPopMatrix();

		cgtexture.disable(gl2);

		gl2.glColor3f(1.0f, 0.0f, 0.0f);
		gl2.glRasterPos2f(xpos, 300.0f);
		glut.glutBitmapString(GLUT.BITMAP_HELVETICA_18, "Save the Screens");
		gl2.glFlush();

		xpos += xvel;
		if (xpos > dim.getWidth())
			xpos = 0.0f;

		/***/
		ypos += yvel;
		if (ypos > dim.getHeight())
			ypos = 0.0f;
	}

	@Override
	public void dispose(GLAutoDrawable glautodrawable) {
	}

	@Override
	public void reshape(GLAutoDrawable dr, int x, int y, int width, int height) {
	}

}
