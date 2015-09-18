package tut;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLJPanel;

public class BasicJoglApp2D extends JPanel implements GLEventListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BasicJoglApp2D() {
		GLJPanel drawable = new GLJPanel();
		drawable.setPreferredSize(new Dimension(600, 600));
		setLayout(new BorderLayout());
		add(drawable, BorderLayout.CENTER);
		drawable.addGLEventListener(this);

	}

	@Override
	public void init(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();

		// sets the clear color to light blue:
		gl.glClearColor(0.8f, 0.8f, 1, 1);

	}

	@Override
	public void dispose(GLAutoDrawable drawable) {
		// TODO Auto-generated method stub

	}

	@Override
	public void display(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();

		// “clears” the drawing area to its background color:
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT);

		// draw a yellow triangle with a black border:
		// yellow triangle:
		// gl.glColor3f(1.0f, 1.0f, 0.0f);
		gl.glBegin(GL2.GL_POLYGON);
		gl.glVertex2f(-0.5f, -0.3f);
		gl.glVertex2f(0.5f, -0.3f);
		gl.glVertex2f(0.0f, 0.6f);
		gl.glEnd();

		// black border:
		gl.glColor3f(0.0f, 1.0f, 0.0f);
		gl.glBegin(GL2.GL_LINE_LOOP);
		gl.glVertex2f(-0.5f, -0.3f);
		gl.glVertex2f(0.5f, -0.3f);
		gl.glVertex2f(0.0f, 0.6f);
		gl.glEnd();
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		// TODO Auto-generated method stub

	}

	public static void main(String[] args) {
		JFrame window = new JFrame("Basic JOGL App 2D");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setContentPane(new BasicJoglApp2D());
		window.pack();
		window.setVisible(true);
	}

}
