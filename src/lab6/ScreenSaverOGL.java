package lab6;

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

public class ScreenSaverOGL implements GLEventListener {

	JFrame jf;
	GLCanvas canvas;
	GLProfile profile;
	GLCapabilities caps;
	Dimension dim = new Dimension(800, 600);
	FPSAnimator animator;

	float xpos;
	float xvel;

	// settings of the rotation:
	float xcamrot = 0.0f;
	float time; // in seconds
	float cycletime = 10.0f;
	static int framerate = 60;

	// position of the light source:
	float lightpos[] = { 50.0f, 100.0f, 200.0f, 1.0f };

	public ScreenSaverOGL() {
		jf = new JFrame();
		profile = GLProfile.getDefault();
		caps = new GLCapabilities(profile);

		// double buffer:
		caps.setDoubleBuffered(true);

		canvas = new GLCanvas(caps);
		canvas.addGLEventListener(this);
		canvas.requestFocusInWindow();
		jf.getContentPane().add(canvas);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setVisible(true);
		jf.setPreferredSize(dim);
		jf.pack();

		// set up animator:
		// animator = new FPSAnimator(canvas, 20);
		animator = new FPSAnimator(canvas, framerate);
		time = 0.0f;
		// xpos = 100.0f;
		// xvel = 1.0f;
		animator.start();
	}

	public static void main(String[] args) {
		new ScreenSaverOGL();
	}

	public void init(GLAutoDrawable dr) { // set up openGL for 2D drawing
		GL2 gl2 = dr.getGL().getGL2();
		GLU glu = new GLU();
		gl2.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

		//
		gl2.glEnable(GL2.GL_DEPTH_TEST);
		gl2.glEnable(GL2.GL_LIGHTING);

		gl2.glMatrixMode(GL2.GL_PROJECTION);
		gl2.glLoadIdentity();

		//
		// glu.gluOrtho2D(0.0, dim.getWidth(), 0.0, dim.getHeight());
		glu.gluPerspective(80.0, 1.0, 50.0, 3000.0);

		gl2.glMatrixMode(GL2.GL_MODELVIEW);
		gl2.glLoadIdentity();

		//
		glu.gluLookAt(0.0, 80.0, 500.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0);
		gl2.glEnable(GL2.GL_NORMALIZE);
	}

	public void display(GLAutoDrawable dr) { // clear the screen and draw "Save
												// the Screens"
		GL2 gl2 = dr.getGL().getGL2();
		GLUT glut = new GLUT();

		gl2.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

		gl2.glEnable(GL2.GL_LIGHTING);
		gl2.glPushMatrix();
		gl2.glRotated(xcamrot, 1.0, 0.0, 0.0);

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
