package lab6;

import java.awt.Dimension;

import javax.swing.JFrame;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.FPSAnimator;

/**
 * Lab task 6: (1) Create a Rubix cube that rotates on an axis. You must include
 * some form of specular lighting. Given that Rubix cubes are shiny, you should
 * see a bright spots or regions at particular angles.
 * 
 * @author Jason
 * 
 */
public class RubixCube implements GLEventListener {

	JFrame jf;
	GLCanvas canvas;
	GLProfile profile;
	GLCapabilities caps;
	Dimension dim = new Dimension(800, 600);
	FPSAnimator animator;

	/** cube width */
	private float cubeWidth = 0.9f;

	/** settings */
	float time; // in seconds
	float cycletime = 10.0f;
	static int framerate = 60;
	float lightpos[] = { 50.0f, 100.0f, 200.0f, 1.0f }; // 4th param = 0
														// indicates a point
														// light.

	public RubixCube() {
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
		animator.start();
	}

	public static void main(String[] args) {
		new RubixCube();
	}

	@Override
	public void init(GLAutoDrawable dr) {
		GL2 gl2 = dr.getGL().getGL2();
		GLU glu = new GLU();
		gl2.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		gl2.glEnable(GL2.GL_DEPTH_TEST);
		gl2.glEnable(GL2.GL_LIGHTING); // +
		gl2.glMatrixMode(GL2.GL_PROJECTION);
		gl2.glLoadIdentity();
		glu.gluPerspective(80.0, 1.0, 50.0, 3000.0);
		// gl2.glOrtho(0.0, dim.getWidth(), 0.0, dim.getHeight(), 0, 300f);
		gl2.glMatrixMode(GL2.GL_MODELVIEW);
		gl2.glLoadIdentity();
		glu.gluLookAt(0.0, 80.0, 500.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0);
		gl2.glEnable(GL2.GL_NORMALIZE);
	}

	@Override
	public void dispose(GLAutoDrawable dr) {
	}

	@Override
	public void reshape(GLAutoDrawable dr, int x, int y, int width, int height) {
		final GL2 gl = dr.getGL().getGL2();
		GLU glu = new GLU();
		if (height <= 0)
			height = 1;
		final float h = (float) width / (float) height;
		gl.glViewport(0, 0, width, height);
		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLoadIdentity();
		glu.gluPerspective(45.0f, h, 1.0, 20.0);
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity();
	}

	public void display(GLAutoDrawable dr) {
		GL2 gl = dr.getGL().getGL2();

		gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);

		gl.glEnable(GL2.GL_LIGHTING);
		gl.glPushMatrix();
		gl.glRotated(0.0, 1.0, 0.0, 0.0);

		// set up the light:
		float ac[] = { 0.2f, 0.2f, 0.2f, 1.0f };
		gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_AMBIENT, ac, 0);
		gl.glEnable(GL2.GL_LIGHT1);
		float dc[] = { 3.0f, 3.0f, 3.0f, 1.0f };
		float sc[] = { 3.0f, 3.0f, 3.0f, 1.0f };
		gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_POSITION, lightpos, 0);
		gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_DIFFUSE, dc, 0);
		gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_SPECULAR, sc, 0);

		// set up and draw the rubix cube:
		gl.glPushMatrix();
		gl.glLoadIdentity();
		gl.glTranslatef(0f, 0f, -5.0f);
		gl.glRotatef(time * 20.0f, 0.3f, 1.0f, 0.0f);

		// set up the material:
		float df[] = { 0.0f, 0.2f, 1.0f, 0.0f };
		float sf[] = { 1.0f, 1.0f, 1.0f, 0.0f };
		gl.glEnable(GL2.GL_COLOR_MATERIAL);
		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_AMBIENT_AND_DIFFUSE, df,
				0);
		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_SPECULAR, sf, 0);
		gl.glMaterialf(GL2.GL_FRONT_AND_BACK, GL2.GL_SHININESS, 120.0f);

		// draw the rubix cube:
		gl.glColor3f(0.0f, 0.0f, 1.0f);
		drawRubixCube(gl, -cubeWidth, cubeWidth, -cubeWidth, 1);

		gl.glColor3f(0.0f, 1.0f, 1.0f);
		drawRubixCube(gl, -cubeWidth, -cubeWidth, -cubeWidth, 1);
		gl.glColor3f(0.0f, 1.0f, 0.0f);

		gl.glDisable(GL2.GL_LIGHTING);

		drawRubixCube(gl, cubeWidth, -cubeWidth, -cubeWidth, 0);
		gl.glColor3f(1.0f, 0.0f, 0.0f);
		drawRubixCube(gl, -cubeWidth, -cubeWidth, -cubeWidth, 0);
		gl.glColor3f(1.0f, 0.0f, 1.0f);
		drawRubixCube(gl, -cubeWidth, -cubeWidth, cubeWidth, 2);
		gl.glColor3f(1f, 1f, 0f);
		drawRubixCube(gl, -cubeWidth, -cubeWidth, -cubeWidth, 2);

		// gl.glDisable(GL2.GL_LIGHTING);

		gl.glPopMatrix();

		gl.glPopMatrix();

		gl.glFlush();

		time += 1.0f / framerate;
		if (time > cycletime)
			time = 0.0f;
	}

	/**
	 * Draw a face of the rubix cube.
	 * 
	 * @param gl
	 * @param x0
	 * @param y0
	 * @param z0
	 * @param axis
	 */
	public void drawRubixCube(GL2 gl, float x0, float y0, float z0, int axis) {
		float boarderWidth = 0.15f;
		float offset = 0.5f;

		// draw 9 slices:
		gl.glBegin(GL2.GL_QUADS);

		if (axis == 0) {
			for (float y = y0; y < 0.7; y += (offset + boarderWidth)) {
				for (float z = z0; z < 0.7; z += (offset + boarderWidth)) {
					gl.glVertex3f(x0, y, z);
					gl.glVertex3f(x0, y + offset, z);
					gl.glVertex3f(x0, y + offset, z + offset);
					gl.glVertex3f(x0, y, z + offset);
				}
			}
		} else if (axis == 1) {
			for (float x = x0; x < 0.7; x += (offset + boarderWidth)) {
				for (float z = z0; z < 0.7; z += (offset + boarderWidth)) {
					gl.glVertex3f(x, y0, z);
					gl.glVertex3f(x + offset, y0, z);
					gl.glVertex3f(x + offset, y0, z + offset);
					gl.glVertex3f(x, y0, z + offset);
				}
			}
		} else {
			for (float x = x0; x < 0.7; x += (offset + boarderWidth)) {
				for (float y = y0; y < 0.7; y += (offset + boarderWidth)) {
					gl.glVertex3f(x, y, z0);
					gl.glVertex3f(x + offset, y, z0);
					gl.glVertex3f(x + offset, y + offset, z0);
					gl.glVertex3f(x, y + offset, z0);
				}
			}
		}

		// draw boarders:
		gl.glColor3f(0.0f, 0.0f, 0.0f);

		if (axis == 0) {
			gl.glVertex3f(x0, y0 + offset, z0);
			gl.glVertex3f(x0, y0 + offset + boarderWidth, z0);
			gl.glVertex3f(x0, y0 + offset + boarderWidth, z0 + 2 * cubeWidth);
			gl.glVertex3f(x0, y0 + offset, z0 + 2 * cubeWidth);

			gl.glVertex3f(x0, y0 + 2 * offset + boarderWidth, z0);
			gl.glVertex3f(x0, y0 + 2 * (offset + boarderWidth), z0);
			gl.glVertex3f(x0, y0 + 2 * (offset + boarderWidth), z0 + 2
					* cubeWidth);
			gl.glVertex3f(x0, y0 + 2 * offset + boarderWidth, z0 + 2
					* cubeWidth);

			gl.glVertex3f(x0, y0, z0 + offset);
			gl.glVertex3f(x0, y0, z0 + offset + boarderWidth);
			gl.glVertex3f(x0, y0 + 2 * cubeWidth, z0 + offset + boarderWidth);
			gl.glVertex3f(x0, y0 + 2 * cubeWidth, z0 + offset);

			gl.glVertex3f(x0, y0, z0 + 2 * offset + boarderWidth);
			gl.glVertex3f(x0, y0, z0 + 2 * (offset + boarderWidth));
			gl.glVertex3f(x0, y0 + 2 * cubeWidth, z0 + 2
					* (offset + boarderWidth));
			gl.glVertex3f(x0, y0 + 2 * cubeWidth, z0 + 2 * offset
					+ boarderWidth);
		} else if (axis == 1) {
			gl.glVertex3f(x0 + offset, y0, z0);
			gl.glVertex3f(x0 + offset + boarderWidth, y0, z0);
			gl.glVertex3f(x0 + offset + boarderWidth, y0, z0 + 2 * cubeWidth);
			gl.glVertex3f(x0 + offset, y0, z0 + 2 * cubeWidth);

			gl.glVertex3f(x0 + 2 * offset + boarderWidth, y0, z0);
			gl.glVertex3f(x0 + 2 * (offset + boarderWidth), y0, z0);
			gl.glVertex3f(x0 + 2 * (offset + boarderWidth), y0, z0 + 2
					* cubeWidth);
			gl.glVertex3f(x0 + 2 * offset + boarderWidth, y0, z0 + 2
					* cubeWidth);

			gl.glVertex3f(x0, y0, z0 + offset);
			gl.glVertex3f(x0, y0, z0 + offset + boarderWidth);
			gl.glVertex3f(x0 + 2 * cubeWidth, y0, z0 + offset + boarderWidth);
			gl.glVertex3f(x0 + 2 * cubeWidth, y0, z0 + offset);

			gl.glVertex3f(x0, y0, z0 + 2 * offset + boarderWidth);
			gl.glVertex3f(x0, y0, z0 + 2 * (offset + boarderWidth));
			gl.glVertex3f(x0 + 2 * cubeWidth, y0, z0 + 2
					* (offset + boarderWidth));
			gl.glVertex3f(x0 + 2 * cubeWidth, y0, z0 + 2 * offset
					+ boarderWidth);
		} else {
			gl.glVertex3f(x0 + offset, y0, z0);
			gl.glVertex3f(x0 + offset + boarderWidth, y0, z0);
			gl.glVertex3f(x0 + offset + boarderWidth, y0 + 2 * cubeWidth, z0);
			gl.glVertex3f(x0 + offset, y0 + 2 * cubeWidth, z0);

			gl.glVertex3f(x0 + 2 * offset + boarderWidth, y0, z0);
			gl.glVertex3f(x0 + 2 * (offset + boarderWidth), y0, z0);
			gl.glVertex3f(x0 + 2 * (offset + boarderWidth), y0 + 2 * cubeWidth,
					z0);
			gl.glVertex3f(x0 + 2 * offset + boarderWidth, y0 + 2 * cubeWidth,
					z0);

			gl.glVertex3f(x0, y0 + offset, z0);
			gl.glVertex3f(x0, y0 + offset + boarderWidth, z0);
			gl.glVertex3f(x0 + 2 * cubeWidth, y0 + offset + boarderWidth, z0);
			gl.glVertex3f(x0 + 2 * cubeWidth, y0 + offset, z0);

			gl.glVertex3f(x0, y0 + 2 * offset + boarderWidth, z0);
			gl.glVertex3f(x0, y0 + 2 * (offset + boarderWidth), z0);
			gl.glVertex3f(x0 + 2 * cubeWidth, y0 + 2 * (offset + boarderWidth),
					z0);
			gl.glVertex3f(x0 + 2 * cubeWidth, y0 + 2 * offset + boarderWidth,
					z0);
		}

		gl.glEnd();

	}

}
