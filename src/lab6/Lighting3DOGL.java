package lab6;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

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

public class Lighting3DOGL implements GLEventListener, MouseMotionListener {

	JFrame jf;
	GLCanvas canvas;
	GLProfile profile;
	GLCapabilities caps;
	Dimension dim = new Dimension(800, 600);
	FPSAnimator animator;

	float xcamrot = 0.0f;
	float lightdis = 1.0f;
	float time; // in seconds
	float cycletime = 10.0f;
	static int framerate = 60;
	float lightpos[] = { 50.0f, 100.0f, 200.0f, 1.0f };

	public Lighting3DOGL() {
		jf = new JFrame();
		profile = GLProfile.getDefault();
		caps = new GLCapabilities(profile);
		caps.setDoubleBuffered(true);
		canvas = new GLCanvas(caps);
		canvas.addGLEventListener(this);
		canvas.requestFocusInWindow();
		jf.getContentPane().add(canvas);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setVisible(true);
		jf.setPreferredSize(dim);
		jf.pack();
		animator = new FPSAnimator(canvas, framerate);
		time = 0.0f;
		canvas.addMouseMotionListener(this);
		animator.start();
	}

	public static void main(String[] args) {
		new Lighting3DOGL();
	}

	public void init(GLAutoDrawable dr) { // set up openGL for 2D drawing
		GL2 gl2 = dr.getGL().getGL2();
		GLU glu = new GLU();
		GLUT glut = new GLUT();
		gl2.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		gl2.glEnable(GL2.GL_DEPTH_TEST);

		gl2.glEnable(GL2.GL_LIGHTING);

		gl2.glMatrixMode(GL2.GL_PROJECTION);
		gl2.glLoadIdentity();
		glu.gluPerspective(80.0, 1.0, 50.0, 3000.0);
		gl2.glMatrixMode(GL2.GL_MODELVIEW);
		gl2.glLoadIdentity();
		glu.gluLookAt(0.0, 80.0, 500.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0);

		gl2.glEnable(GL2.GL_NORMALIZE);

	}

	public void display(GLAutoDrawable dr) {
		GL2 gl2 = dr.getGL().getGL2();
		GLUT glut = new GLUT();

		gl2.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

		gl2.glEnable(GL2.GL_LIGHTING);
		gl2.glPushMatrix();
		gl2.glRotated(xcamrot, 1.0, 0.0, 0.0);

		// set up light 1

		// gl2.glLightf(GL2.GL_LIGHT1, GL2.GL_CONSTANT_ATTENUATION, 0.0f);
		// gl2.glLightf(GL2.GL_LIGHT1, GL2.GL_LINEAR_ATTENUATION, 0.0f);
		// gl2.glLightf(GL2.GL_LIGHT1, GL2.GL_QUADRATIC_ATTENUATION, 0.001f);
		float ac[] = { 0.2f, 0.2f, 0.2f, 1.0f };
		gl2.glLightfv(GL2.GL_LIGHT1, GL2.GL_AMBIENT, ac, 0);
		gl2.glEnable(GL2.GL_LIGHT1);
		float dc[] = { 3.0f, 3.0f, 3.0f, 1.0f };
		float sc[] = { 3.0f, 3.0f, 3.0f, 1.0f };
		gl2.glLightfv(GL2.GL_LIGHT1, GL2.GL_POSITION, lightpos, 0);
		gl2.glLightfv(GL2.GL_LIGHT1, GL2.GL_DIFFUSE, dc, 0);
		gl2.glLightfv(GL2.GL_LIGHT1, GL2.GL_SPECULAR, sc, 0);

		gl2.glPushMatrix(); // draw the spinning tea pot with a particular
							// material
		gl2.glRotated(time * 20.0f, 0.1, 1.0, 0.0);
		gl2.glColor3f(0.1f, 0.0f, 1.0f);
		float df[] = { 0.0f, 0.2f, 1.0f, 0.0f };
		gl2.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_AMBIENT_AND_DIFFUSE, df, 0);
		float sf[] = { 1.0f, 1.0f, 1.0f, 0.0f };
		gl2.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_SPECULAR, sf, 0);
		gl2.glMaterialf(GL2.GL_FRONT_AND_BACK, GL2.GL_SHININESS, 120.0f);
		glut.glutSolidTeapot(100.0);
		gl2.glPopMatrix();

		gl2.glPopMatrix();

		gl2.glFlush();

		time += 1.0f / framerate;
		if (time > cycletime)
			time = 0.0f;
	}

	public void dispose(GLAutoDrawable glautodrawable) {
	}

	public void reshape(GLAutoDrawable dr, int x, int y, int width, int height) {
	}

	Float xcamrotLast, lightdisLast;

	@Override
	public void mouseDragged(MouseEvent me) {
		if (xcamrotLast != null)
			xcamrot += ((((float) me.getY()) / canvas.getHeight()) - xcamrotLast) * 360.0f;
		xcamrotLast = (((float) me.getY()) / canvas.getHeight());

		if (lightdisLast != null)
			lightdis += ((((float) me.getX()) / canvas.getWidth()) - lightdisLast) * 10.0f;
		lightdisLast = (((float) me.getX()) / canvas.getWidth());

		lightpos[0] = lightdis * 50.0f;
		lightpos[1] = lightdis * 100.0f;
		lightpos[2] = lightdis * 200.0f;

		canvas.display();
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		xcamrotLast = null;
		lightdisLast = null;
	}
}
