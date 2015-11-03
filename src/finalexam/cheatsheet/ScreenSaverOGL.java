package finalexam.cheatsheet;

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
		animator = new FPSAnimator(canvas, 60);
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
		// glu.gluOrtho2D(0.0, dim.getWidth(), 0.0, dim.getHeight());
		glu.gluPerspective(60.0, 1.0, 100.0, 1000.0);
		gl2.glMatrixMode(GL2.GL_MODELVIEW);
		gl2.glLoadIdentity();
		// glu.gluLookAt(100.0, 100.0, 500.0, 100.0, 100.0, 25.0, 0.0, 1.0,
		// 0.0);
		glu.gluLookAt(400.0, 300.0, 500.0, 400.0, 300.0, 0.0, 0.0, 1.0, 0.0);

		try {
			texture = TextureIO.newTexture(new File("lab5_pic1.png"), true);
			texture.enable(gl2);
			texture.bind(gl2);
		} catch (GLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void display(GLAutoDrawable dr) { // clear the screen and draw "Save
												// the Screens"
		GL2 gl2 = dr.getGL().getGL2();
		GLU glu = new GLU();
		GLUT glut = new GLUT();

		gl2.glClear(GL.GL_COLOR_BUFFER_BIT);
		// gl2.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

		// draw a 2D polygon with texture:
		gl2.glColor3d(1, 1, 1);
		drawTexPoly(gl2);

		// draw a simple polygon:
		gl2.glColor3d(1, 0, 1);
		drawPoly(gl2, 300, 300, 0, 0, 0, 0);

		// duplicate it:
		drawPoly(gl2, 300, 300, 0, -30, 0, 0);
		drawPoly(gl2, 300, 300, 0, -60, 0, 0);
		drawPoly(gl2, 300, 300, 0, -90, 0, 0);
		drawPoly(gl2, 300, 300, 0, -120, 0, 0);

		gl2.glColor3f(1.0f, 0.0f, 0.0f);
		gl2.glRasterPos2f(xpos, 300.0f);
		glut.glutBitmapString(GLUT.BITMAP_HELVETICA_18, "Save the Screens");
		gl2.glFlush();

		xpos += xvel;
		if (xpos > dim.getWidth())
			xpos = 0.0f;
	}

	public void drawTexPoly(GL2 gl2) {
		// double xoffset = xpos;
		double ow = 100, oh = 100;
		double olbx = (dim.getWidth() - ow) / 2;
		double olby = (dim.getHeight() - oh) / 2;

		texture.enable(gl2);
		texture.bind(gl2);

		gl2.glPushMatrix();

		// translate the polygon to the origin, rotate about the origin, then
		// translate back to the previous position (center of the canvas)
		gl2.glTranslated(dim.getWidth() / 2, dim.getHeight() / 2, 0);
		gl2.glRotated(xpos, 0, 1, 0);
		gl2.glTranslated(-dim.getWidth() / 2, -dim.getHeight() / 2, 0);
		// gl2.glRotated(20, 0, 0, 1);
		// gl2.glScaled(-1, -1, 1);

		// draw a polygon with texture:
		gl2.glBegin(GL2.GL_POLYGON);
		gl2.glVertex3d(olbx, olby, 0);
		gl2.glTexCoord2d(0, 0);
		gl2.glVertex3d(olbx + ow, olby, 0);
		gl2.glTexCoord2d(1, 0);
		gl2.glVertex3d(olbx + ow, olby + oh, 0);
		gl2.glTexCoord2d(1, 1);
		gl2.glVertex3d(olbx, olby + oh, 0);
		gl2.glTexCoord2d(0, 1);
		gl2.glEnd();

		// make the polygon move along x axis:
		// gl2.glBegin(GL2.GL_POLYGON);
		// gl2.glVertex3d(0 + xoffset, 0, 0);
		// gl2.glTexCoord2d(0, 0);
		// gl2.glVertex3d(100 + xoffset, 0, 0);
		// gl2.glTexCoord2d(1, 0);
		// gl2.glVertex3d(100 + xoffset, 100, 0);
		// gl2.glTexCoord2d(1, 1);
		// gl2.glVertex3d(0 + xoffset, 100, 0);
		// gl2.glTexCoord2d(0, 1);
		// gl2.glEnd();

		gl2.glPopMatrix();

		texture.disable(gl2);
	}

	/**
	 * Draw a simple polygon.
	 * 
	 * @param gl2
	 * @param x0
	 * @param y0
	 * @param z0
	 * @param xoff
	 * @param yoff
	 * @param zoff
	 */
	public void drawPoly(GL2 gl2, double x0, double y0, double z0, double xoff, double yoff, double zoff) {
		gl2.glPushMatrix();

		gl2.glTranslated(x0 + xoff, y0 + yoff, 0);
		gl2.glRotated(xpos, 1, 0, 0);
		gl2.glTranslated(-(x0 + xoff), -(y0 + yoff), 0);
		gl2.glTranslated(xoff, yoff, zoff);

		gl2.glBegin(GL2.GL_POLYGON);
		gl2.glVertex2d(x0, y0);
		gl2.glVertex2d(x0 + 20, y0);
		gl2.glVertex2d(x0 + 20, y0 + 20);
		gl2.glVertex2d(x0, y0 + 20);
		gl2.glEnd();

		gl2.glPopMatrix();
	}

	public void dispose(GLAutoDrawable glautodrawable) {
	}

	public void reshape(GLAutoDrawable dr, int x, int y, int width, int height) {
	}
}