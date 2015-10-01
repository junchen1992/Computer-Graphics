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
import com.jogamp.opengl.util.gl2.GLUT;

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

	float xpos;
	float xvel;

	/** rquad */
	private float rquad = 0.0f;

	/** cube width */
	private float cubeWidth = 0.9f;

	public RubixCube() {
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
		new RubixCube();
	}

	@SuppressWarnings("unused")
	@Override
	public void init(GLAutoDrawable dr) {
		GL2 gl2 = dr.getGL().getGL2();
		GLU glu = new GLU();
		GLUT glut = new GLUT();

		gl2.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

		gl2.glEnable(GL2.GL_DEPTH_TEST);

		gl2.glMatrixMode(GL2.GL_PROJECTION);
		gl2.glLoadIdentity();

		glu.gluPerspective(60.0, 1.0, 100.0, 1000.0);

		gl2.glMatrixMode(GL2.GL_MODELVIEW);
		gl2.glLoadIdentity();

		glu.gluLookAt(100.0, 100.0, 500.0, 100.0, 100.0, 25.0, 0.0, 1.0, 0.0);

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

		gl.glLoadIdentity();
		gl.glTranslatef(0f, 0f, -5.0f);
		gl.glRotatef(rquad, 0.0f, 1.0f, 0.0f);

		gl.glColor3f(0.0f, 0.0f, 1.0f);
		drawRubixCube(gl, -cubeWidth, cubeWidth, -cubeWidth, 1);
		gl.glColor3f(0.0f, 1.0f, 1.0f);
		drawRubixCube(gl, -cubeWidth, -cubeWidth, -cubeWidth, 1);
		gl.glColor3f(0.0f, 1.0f, 0.0f);
		drawRubixCube(gl, cubeWidth, -cubeWidth, -cubeWidth, 0);
		gl.glColor3f(1.0f, 0.0f, 0.0f);
		drawRubixCube(gl, -cubeWidth, -cubeWidth, -cubeWidth, 0);
		gl.glColor3f(1.0f, 0.0f, 1.0f);
		drawRubixCube(gl, -cubeWidth, -cubeWidth, cubeWidth, 2);
		gl.glColor3f(1f, 1f, 0f);
		drawRubixCube(gl, -cubeWidth, -cubeWidth, -cubeWidth, 2);

		gl.glFlush();
		rquad -= 2.0f;
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
			gl.glVertex3f(x0, y0 + 2 * (offset + boarderWidth), z0 + 2 * cubeWidth);
			gl.glVertex3f(x0, y0 + 2 * offset + boarderWidth, z0 + 2 * cubeWidth);

			gl.glVertex3f(x0, y0, z0 + offset);
			gl.glVertex3f(x0, y0, z0 + offset + boarderWidth);
			gl.glVertex3f(x0, y0 + 2 * cubeWidth, z0 + offset + boarderWidth);
			gl.glVertex3f(x0, y0 + 2 * cubeWidth, z0 + offset);

			gl.glVertex3f(x0, y0, z0 + 2 * offset + boarderWidth);
			gl.glVertex3f(x0, y0, z0 + 2 * (offset + boarderWidth));
			gl.glVertex3f(x0, y0 + 2 * cubeWidth, z0 + 2 * (offset + boarderWidth));
			gl.glVertex3f(x0, y0 + 2 * cubeWidth, z0 + 2 * offset + boarderWidth);
		} else if (axis == 1) {
			gl.glVertex3f(x0 + offset, y0, z0);
			gl.glVertex3f(x0 + offset + boarderWidth, y0, z0);
			gl.glVertex3f(x0 + offset + boarderWidth, y0, z0 + 2 * cubeWidth);
			gl.glVertex3f(x0 + offset, y0, z0 + 2 * cubeWidth);

			gl.glVertex3f(x0 + 2 * offset + boarderWidth, y0, z0);
			gl.glVertex3f(x0 + 2 * (offset + boarderWidth), y0, z0);
			gl.glVertex3f(x0 + 2 * (offset + boarderWidth), y0, z0 + 2 * cubeWidth);
			gl.glVertex3f(x0 + 2 * offset + boarderWidth, y0, z0 + 2 * cubeWidth);

			gl.glVertex3f(x0, y0, z0 + offset);
			gl.glVertex3f(x0, y0, z0 + offset + boarderWidth);
			gl.glVertex3f(x0 + 2 * cubeWidth, y0, z0 + offset + boarderWidth);
			gl.glVertex3f(x0 + 2 * cubeWidth, y0, z0 + offset);

			gl.glVertex3f(x0, y0, z0 + 2 * offset + boarderWidth);
			gl.glVertex3f(x0, y0, z0 + 2 * (offset + boarderWidth));
			gl.glVertex3f(x0 + 2 * cubeWidth, y0, z0 + 2 * (offset + boarderWidth));
			gl.glVertex3f(x0 + 2 * cubeWidth, y0, z0 + 2 * offset + boarderWidth);
		} else {
			gl.glVertex3f(x0 + offset, y0, z0);
			gl.glVertex3f(x0 + offset + boarderWidth, y0, z0);
			gl.glVertex3f(x0 + offset + boarderWidth, y0 + 2 * cubeWidth, z0);
			gl.glVertex3f(x0 + offset, y0 + 2 * cubeWidth, z0);

			gl.glVertex3f(x0 + 2 * offset + boarderWidth, y0, z0);
			gl.glVertex3f(x0 + 2 * (offset + boarderWidth), y0, z0);
			gl.glVertex3f(x0 + 2 * (offset + boarderWidth), y0 + 2 * cubeWidth, z0);
			gl.glVertex3f(x0 + 2 * offset + boarderWidth, y0 + 2 * cubeWidth, z0);

			gl.glVertex3f(x0, y0 + offset, z0);
			gl.glVertex3f(x0, y0 + offset + boarderWidth, z0);
			gl.glVertex3f(x0 + 2 * cubeWidth, y0 + offset + boarderWidth, z0);
			gl.glVertex3f(x0 + 2 * cubeWidth, y0 + offset, z0);

			gl.glVertex3f(x0, y0 + 2 * offset + boarderWidth, z0);
			gl.glVertex3f(x0, y0 + 2 * (offset + boarderWidth), z0);
			gl.glVertex3f(x0 + 2 * cubeWidth, y0 + 2 * (offset + boarderWidth), z0);
			gl.glVertex3f(x0 + 2 * cubeWidth, y0 + 2 * offset + boarderWidth, z0);
		}

		gl.glEnd();

	}

}
