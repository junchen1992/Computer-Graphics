package lab7;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

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
import com.jogamp.opengl.util.texture.Texture;

public class Step2 implements GLEventListener, MouseListener {

	JFrame jf;
	GLCanvas canvas;
	GLProfile profile;
	GLCapabilities caps;
	Dimension dim = new Dimension(800, 600);
	FPSAnimator animator;
	Texture fronttexture, backtexture, sidetexture;
	float xcamrot = 0.0f;
	float lightdis = 1.0f;
	float xpos;
	float xvel;
	float ypos;
	float yvel;
	float lypos;
	float lyvel;
	float x;
	float y;

	/** cube width */
	private float cubeWidth = 50.0f;

	public Step2() {
		jf = new JFrame();
		profile = GLProfile.getDefault();
		caps = new GLCapabilities(profile);
		canvas = new GLCanvas(caps);
		canvas.addGLEventListener(this);
		canvas.requestFocusInWindow();
		canvas.addMouseListener(this);
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
		new Step2();
	}

	public void init(GLAutoDrawable dr) {
		GL2 gl2 = dr.getGL().getGL2();
		GLU glu = new GLU();
		gl2.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		gl2.glMatrixMode(GL2.GL_PROJECTION);
		gl2.glLoadIdentity();

		glu.gluPerspective(60.0, 1.0, 100.0, 800.0);

		gl2.glMatrixMode(GL2.GL_MODELVIEW);
		gl2.glLoadIdentity();
		glu.gluLookAt(500.0 * Math.sin(100 / 10.0), 20.0, 500.0 * Math.cos(100 / 10.0), 0.0, 0.0, 0.0, 0.0, 1.0, 0.0);

	}

	public void display(GLAutoDrawable dr) {

		GL2 gl2 = dr.getGL().getGL2();

		gl2.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

		drawPhone(gl2, -50, 0.0, 0.0, 150.0);

		xpos += speed;
		speed = speed / 1.2;
		if (xpos > dim.getWidth())
			xpos = 0.0f;

	}

	public void drawPhone(GL2 gl, double x1, double y1, double x2, double y2) {

		gl.glPushMatrix();
		gl.glEnable(GL2.GL_DEPTH_TEST);
		gl.glTranslated((float) (x1 + (x2 - x1) / 2), 0.0f, 0.0f);
		gl.glRotated(xpos, 0.0, 1.0, 0.0);
		gl.glTranslated(-(float) (x1 + (x2 - x1) / 2), 0.0f, 0.0f);

		gl.glBegin(GL2.GL_QUADS);

		// green color
		gl.glColor3f(1.0f, 1.0f, 1.0f);
		gl.glVertex3f(cubeWidth, -cubeWidth, cubeWidth);
		gl.glVertex3f(-cubeWidth, -cubeWidth, cubeWidth);
		gl.glVertex3f(-cubeWidth, -cubeWidth, -cubeWidth);
		gl.glVertex3f(cubeWidth, -cubeWidth, -cubeWidth);

		// blue color
		gl.glColor3f(0f, 0f, 1f);
		gl.glVertex3f(cubeWidth, cubeWidth, cubeWidth);
		gl.glVertex3f(-cubeWidth, cubeWidth, cubeWidth);
		gl.glVertex3f(-cubeWidth, -cubeWidth, cubeWidth);
		gl.glVertex3f(cubeWidth, -cubeWidth, cubeWidth);

		// yellow (red + green)
		gl.glColor3f(1f, 1f, 0f);
		gl.glVertex3f(cubeWidth, -cubeWidth, -cubeWidth);
		gl.glVertex3f(-cubeWidth, -cubeWidth, -cubeWidth);
		gl.glVertex3f(-cubeWidth, cubeWidth, -cubeWidth);
		gl.glVertex3f(cubeWidth, cubeWidth, -cubeWidth);

		// purple (red + green)
		gl.glColor3f(1f, 0f, 1f);
		gl.glVertex3f(-cubeWidth, cubeWidth, cubeWidth);
		gl.glVertex3f(-cubeWidth, cubeWidth, -cubeWidth);
		gl.glVertex3f(-cubeWidth, -cubeWidth, -cubeWidth);
		gl.glVertex3f(-cubeWidth, -cubeWidth, cubeWidth);

		// sky blue (blue +green)
		gl.glColor3f(0f, 1f, 1f);
		gl.glVertex3f(cubeWidth, cubeWidth, -cubeWidth);
		gl.glVertex3f(cubeWidth, cubeWidth, cubeWidth);
		gl.glVertex3f(cubeWidth, -cubeWidth, cubeWidth);
		gl.glVertex3f(cubeWidth, -cubeWidth, -cubeWidth);

		// Done Drawing The Quad
		gl.glEnd();

		gl.glPopMatrix();

	}

	public void dispose(GLAutoDrawable glautodrawable) {
	}

	public void reshape(GLAutoDrawable dr, int x, int y, int width, int height) {
	}

	int x1, x2;
	long time1, time2;
	double speed = 0.0;

	@Override
	public void mousePressed(MouseEvent e) {
		x1 = e.getX();
		time1 = System.currentTimeMillis();

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		x2 = e.getX();
		time2 = System.currentTimeMillis();
		long time_dif = (time2 - time1);

		speed = ((x2 - x1) * 10) / time_dif;

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}