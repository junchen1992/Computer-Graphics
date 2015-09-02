package tut;

import java.awt.Dimension;

import javax.swing.JFrame;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.gl2.GLUT;

public class Simple3DOGL implements GLEventListener {

	/**
	 * Simple3DOGL - this is a simple 3D scene that uses JOGL2 Eric McCreath
	 * 2009, 2011, 2015
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

	public Simple3DOGL() {
		jf = new JFrame();
		profile = GLProfile.getDefault();
		caps = new GLCapabilities(profile);
		caps.setDoubleBuffered(false);
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
		new Simple3DOGL();
	}

	public void init(GLAutoDrawable dr) { // set up openGL for 2D drawing
		GL2 gl2 = dr.getGL().getGL2();
		GLU glu = new GLU();
		GLUT glut = new GLUT();
		gl2.glClearColor(1.0f, 1.0f, 1.0f, 0.0f);
		gl2.glEnable(GL2.GL_DEPTH_TEST);

	}

	public void display(GLAutoDrawable dr) { // clear the screen and draw
												// "Save the Screens"
		GL2 gl2 = dr.getGL().getGL2();
		GLU glu = new GLU();
		GLUT glut = new GLUT();

		gl2.glMatrixMode(GL2.GL_PROJECTION);
		gl2.glLoadIdentity();

		glu.gluPerspective(60.0, 1.0, 100.0, 800.0);

		gl2.glMatrixMode(GL2.GL_MODELVIEW);
		gl2.glLoadIdentity();
		glu.gluLookAt(500.0 * Math.sin(xpos / 10.0), 20.0, 500.0 * Math.cos(xpos / 10.0), 0.0, 0.0, 0.0, 0.0, 1.0, 0.0);

		gl2.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

		gl2.glEnable(GL2.GL_CULL_FACE);
		gl2.glColor3f(0.0f, 0.0f, 1.0f);
		gl2.glBegin(GL2.GL_POLYGON);
		gl2.glVertex3d(0.0, 0.0, 0.0);
		gl2.glVertex3d(0.0, 200.0, 0.0);
		gl2.glVertex3d(150.0, 200.0, 0.0);
		gl2.glVertex3d(150.0, 0.0, 0.0);
		gl2.glEnd();

		gl2.glDisable(GL2.GL_CULL_FACE);
		gl2.glColor3f(1.0f, 0.0f, 0.0f);
		gl2.glBegin(GL2.GL_POLYGON);
		gl2.glVertex3d(100.0, 50.0, -10.0);
		gl2.glVertex3d(100.0, 250.0, -10.0);
		gl2.glVertex3d(250.0, 250.0, -10.0);
		gl2.glVertex3d(250.0, 50.0, -10.0);
		gl2.glEnd();

		gl2.glFlush();
		// dr.swapBuffers();
		xpos += xvel;
		if (xpos > dim.getWidth())
			xpos = 0.0f;
	}

	public void dispose(GLAutoDrawable glautodrawable) {
	}

	public void reshape(GLAutoDrawable dr, int x, int y, int width, int height) {
	}
}
