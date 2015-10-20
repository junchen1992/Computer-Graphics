package tut;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.Animator;

public class HelloWorldDemo extends JFrame implements GLEventListener {

	private static final long serialVersionUID = 1L;

	public HelloWorldDemo() {
		super("Basic JOGL Demo");

		setLayout(new BorderLayout());

		setSize(600, 600);
		setLocation(40, 40);

		setVisible(true);

		setupJOGL();
	}

	private void setupJOGL() {
		GLProfile profile = GLProfile.getDefault();
		GLCapabilities caps = new GLCapabilities(profile);
		caps.setDoubleBuffered(true);
		caps.setHardwareAccelerated(true);

		GLCanvas canvas = new GLCanvas(caps);
		canvas.addGLEventListener(this);
		add(canvas, BorderLayout.CENTER);
		Animator anim = new Animator(canvas);
		anim.start();

	}

	public static void main(String[] args) {
		HelloWorldDemo demo = new HelloWorldDemo();
		demo.setVisible(true);
	}

	@Override
	public void init(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		gl.glClearColor(0, 0, 0, 0);
		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glOrtho(0, 1, 0, 1, -1, 1);

	}

	@Override
	public void dispose(GLAutoDrawable drawable) {
		// TODO Auto-generated method stub

	}

	@Override
	public void display(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();

		gl.glClear(GL2.GL_COLOR_BUFFER_BIT);
		gl.glBegin(GL2.GL_TRIANGLES);

		gl.glColor3f(1, 0, 0);
		gl.glVertex3f(0.25f, 0.25f, 0);

		gl.glColor3f(0, 1, 0);
		gl.glVertex3f(0.5f, 0.25f, 0);

		gl.glColor3f(0, 0, 1);
		gl.glVertex3f(0.25f, 0.5f, 0);

		gl.glEnd();
		gl.glFlush();

	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		// TODO Auto-generated method stub

	}

}