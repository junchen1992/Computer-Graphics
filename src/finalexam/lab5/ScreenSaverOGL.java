package finalexam.lab5;

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

	double angle = 0.0;

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

		gl2.glEnable(GL2.GL_DEPTH_TEST);

		gl2.glMatrixMode(GL2.GL_PROJECTION);
		gl2.glLoadIdentity();

		glu.gluPerspective(60.0, 1.0, 100.0, 1000.0);

		gl2.glMatrixMode(GL2.GL_MODELVIEW);
		gl2.glLoadIdentity();

		glu.gluLookAt(100.0, 100.0, 500.0, 100.0, 100.0, 25.0, 0.0, 1.0, 0.0);
	}

	// public void init(GLAutoDrawable dr) { // set up openGL for 2D drawing
	// GL2 gl2 = dr.getGL().getGL2();
	// GLU glu = new GLU();
	//
	// gl2.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
	//
	// // *
	// // gl2.glClearDepth(1.0f);
	// gl2.glEnable(GL2.GL_DEPTH_TEST);
	// // gl2.glDepthFunc(GL2.GL_LEQUAL);
	// // gl2.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST);
	//
	// gl2.glMatrixMode(GL2.GL_PROJECTION);
	// gl2.glLoadIdentity();
	//
	// // glu.gluOrtho2D(0.0, dim.getWidth(), 0.0, dim.getHeight());
	// glu.gluPerspective(60.0, 1.0, 100.0, 1000.0);
	//
	// gl2.glMatrixMode(GL2.GL_MODELVIEW);
	// gl2.glLoadIdentity();
	//
	// // *
	// // glu.gluLookAt(100.0, 100.0, 500.0, 100.0, 100.0, 25.0, 0.0, 1.0,
	// // 0.0);
	//
	// glu.gluLookAt(0.0, 0.0, 200.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0);
	//
	// // load an image:
	// // try {
	// // cgtexture = TextureIO.newTexture(new File(imageSrc), true);
	// // cgtexture.enable(gl2);
	// // cgtexture.bind(gl2);
	// // } catch (GLException | IOException e) {
	// // e.printStackTrace();
	// // }
	//
	// }

	public void display(GLAutoDrawable dr) { // clear the screen and draw
												// "Save the Screens"
		GL2 gl2 = dr.getGL().getGL2();
		GLUT glut = new GLUT();

		// gl2.glClear(GL.GL_COLOR_BUFFER_BIT);
		gl2.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

		// polygon:
		gl2.glPushMatrix();

		// gl2.glTranslated(150, 0, 0);
		gl2.glRotated(xpos, 0, 1, 0);
		// gl2.glTranslated(-150, 0, 0);

		gl2.glBegin(GL2.GL_POLYGON);
		gl2.glVertex3d(0.0, 0.0, 0.0);
		gl2.glVertex3d(300.0, 0.0, 0.0);
		gl2.glVertex3d(300.0, 200.0, 0.0);
		gl2.glVertex3d(0.0, 200.0, 0.0);
		gl2.glEnd();

		gl2.glPopMatrix();

		// gl2.glColor3f(1.0f, 0.0f, 0.0f);
		// gl2.glRasterPos2f(xpos, 300.0f);
		// glut.glutBitmapString(GLUT.BITMAP_HELVETICA_18, "Save the Screens");
		gl2.glFlush();

		xpos += xvel;
		if (xpos > dim.getWidth())
			xpos = 0.0f;

		angle += 1;
		if (angle > 360)
			angle = 0;
	}

	public void drawPoly() {

	}

	public void dispose(GLAutoDrawable glautodrawable) {
	}

	public void reshape(GLAutoDrawable dr, int x, int y, int width, int height) {
	}
}