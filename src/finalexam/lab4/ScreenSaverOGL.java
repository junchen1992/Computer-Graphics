package finalexam.lab4;

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

	JFrame jf;
	GLCanvas canvas;
	GLProfile profile;
	GLCapabilities caps;
	Dimension dim = new Dimension(800, 600);
	FPSAnimator animator;

	float xpos;
	float xvel;

	Texture texture;

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
		xvel = 1.0f;
		animator.start();
	}

	public static void main(String[] args) {
		new ScreenSaverOGL();
	}

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

		try {
			texture = TextureIO.newTexture(new File("index.png"), true);
			texture.enable(gl2);
			texture.bind(gl2);
		} catch (GLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void display(GLAutoDrawable dr) { // clear the screen and draw
												// "Save the Screens"
		GL2 gl2 = dr.getGL().getGL2();
		GLU glu = new GLU();
		GLUT glut = new GLUT();

		gl2.glClear(GL.GL_COLOR_BUFFER_BIT);

		// draw a line:
		// gl2.glRotated(360, 0, 1, 0);
		gl2.glBegin(GL2.GL_LINES);
		gl2.glVertex2d(xpos, 10.0);
		gl2.glVertex2d(xpos + 100.0, 100.0);
		gl2.glEnd();

		// draw a line:
		gl2.glBegin(GL2.GL_LINES);
		gl2.glVertex2d(xpos, 10.0);
		gl2.glVertex2d(xpos + 100.0, 10.0);
		gl2.glEnd();

		// draw a polygon:
		gl2.glColor3f(0.5f, 0.5f, 0.5f);
		texture.enable(gl2);
		texture.bind(gl2);
		gl2.glBegin(GL2.GL_POLYGON);
		gl2.glTexCoord2d(0.0, 0.0);
		gl2.glVertex2d(xpos, 200);
		gl2.glTexCoord2d(1.0, 0.0);
		gl2.glVertex2d(xpos + 100, 200);
		gl2.glTexCoord2d(1.0, 1.0);
		gl2.glVertex2d(xpos + 100, 400);
		gl2.glTexCoord2d(0.0, 1.0);
		gl2.glVertex2d(xpos, 400);
		gl2.glEnd();
		texture.disable(gl2);

		gl2.glColor3f(1.0f, 0.0f, 0.0f);
		gl2.glRasterPos2f(xpos, 300.0f);
		glut.glutBitmapString(GLUT.BITMAP_HELVETICA_18, "Save the Screens");
		gl2.glFlush();

		xpos += xvel;
		if (xpos > dim.getWidth())
			xpos = 0.0f;
	}

	public void dispose(GLAutoDrawable glautodrawable) {
	}

	public void reshape(GLAutoDrawable dr, int x, int y, int width, int height) {
	}
}