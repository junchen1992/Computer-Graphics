package lab5;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
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

public class ScreenSaverOGL implements GLEventListener, MouseMotionListener {

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
	private float cubeWidth = 0.3f;

	/***/
	// private final static String imageSrc =
	// "/Users/Jason/git/Computer-Graphics/index.png";
	private final static String imageSrc = "/Users/Jason/git/Computer-Graphics/lab5_pic1.png";
	Texture cgtexture;

	private float xcamrot;

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
		animator = new FPSAnimator(canvas, 50);
		xpos = 100.0f;
		xvel = 1.0f;
		animator.start();
	}

	public static void main(String[] args) {
		new ScreenSaverOGL();
	}

	@SuppressWarnings("unused")
	@Override
	public void init(GLAutoDrawable dr) { // set up openGL for 2D drawing
		GL2 gl2 = dr.getGL().getGL2();
		GLU glu = new GLU();
		GLUT glut = new GLUT();

		gl2.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

		// *
		// gl2.glClearDepth(1.0f);
		gl2.glEnable(GL2.GL_DEPTH_TEST);
		// gl2.glDepthFunc(GL2.GL_LEQUAL);
		// gl2.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST);

		gl2.glMatrixMode(GL2.GL_PROJECTION);
		gl2.glLoadIdentity();

		// glu.gluOrtho2D(0.0, dim.getWidth(), 0.0, dim.getHeight());
		glu.gluPerspective(60.0, 1.0, 100.0, 1000.0);

		gl2.glMatrixMode(GL2.GL_MODELVIEW);
		gl2.glLoadIdentity();

		// *
		// glu.gluLookAt(100.0, 100.0, 500.0, 100.0, 100.0, 25.0, 0.0, 1.0,
		// 0.0);

		glu.gluLookAt(0.0, 0.0, 200.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0);

		// load an image:
		try {
			cgtexture = TextureIO.newTexture(new File(imageSrc), true);
			cgtexture.enable(gl2);
			cgtexture.bind(gl2);
		} catch (GLException | IOException e) {
			e.printStackTrace();
		}

	}

	@SuppressWarnings("unused")
	public void display1(GLAutoDrawable dr) {
		// clear the screen and draw "Save the Screens"
		GL2 gl2 = dr.getGL().getGL2();
		GLU glu = new GLU();
		GLUT glut = new GLUT();

		gl2.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

		gl2.glPushMatrix();
		gl2.glRotated(xpos, 0.0, 1.0, 0.0);
		gl2.glColor3f(0.3f, 0.5f, 0.7f);

		gl2.glBegin(GL2.GL_POLYGON);
		gl2.glVertex3d(0.0, 0.0, 0.0);
		gl2.glVertex3d(300.0, 0.0, 0.0);
		gl2.glVertex3d(300.0, 200.0, 0.0);
		gl2.glVertex3d(0.0, 200.0, 0.0);
		gl2.glEnd();

		// method 1 to duplicate the polygon 4 times:
		duplicate(gl2, -50, -50, -50);
		duplicate(gl2, -25, -25, -25);
		duplicate(gl2, 50, 50, 50);
		duplicate(gl2, 100, 100, 100);

		gl2.glPopMatrix();
		gl2.glFlush();

		xpos += xvel;
		if (xpos > dim.getWidth())
			xpos = 0.0f;
	}

	/**
	 * Duplicate a polygon.
	 * 
	 * @param gl2
	 * @param x
	 * @param y
	 * @param z
	 */
	public void duplicate(GL2 gl2, double x, double y, double z) {
		gl2.glTranslated(x, y, z);
		// change the color of the duplicated polygons:
		gl2.glColor3f(0.0f, 1.0f, 1.0f);
		gl2.glBegin(GL2.GL_POLYGON);
		gl2.glVertex3d(0.0, 0.0, 0.0);
		gl2.glVertex3d(300.0, 0.0, 0.0);
		gl2.glVertex3d(300.0, 200.0, 0.0);
		gl2.glVertex3d(0.0, 200.0, 0.0);
		gl2.glEnd();
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

	/**
	 * Display method for step 2.
	 * 
	 * @param dr
	 */
	public void display(GLAutoDrawable dr) {
		GL2 gl = dr.getGL().getGL2();
		GLU glu = new GLU();

		gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);

		cgtexture.enable(gl);
		cgtexture.bind(gl);

		// gl.glPushMatrix();

		gl.glLoadIdentity();

		glu.gluLookAt(lightdis * 100, xcamrot * 10, 200.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0);

		gl.glTranslatef(0f, 0f, -5.0f);
		gl.glRotatef(rquad, 1.0f, 1.0f, 1.0f);
		// Start Drawing The Cube
		gl.glBegin(GL2.GL_QUADS);
		// red color
		// gl.glColor3f(1f, 0f, 0f);
		// gl.glVertex3f(1.0f, 1.0f, -1.0f);
		// gl.glVertex3f(-1.0f, 1.0f, -1.0f);
		// gl.glVertex3f(-1.0f, 1.0f, 1.0f);
		// gl.glVertex3f(1.0f, 1.0f, 1.0f);

		// green color
		gl.glColor3f(1.0f, 1.0f, 1.0f);
		// gl.glTexCoord3f(1.0f, 0.0f, 1.0f);
		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(cubeWidth, -cubeWidth, cubeWidth);
		// gl.glTexCoord3f(1.0f, 0.0f, 0.0f);
		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(-cubeWidth, -cubeWidth, cubeWidth);
		// gl.glTexCoord3f(0.0f, 0.0f, 0.0f);
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(-cubeWidth, -cubeWidth, -cubeWidth);
		// gl.glTexCoord3f(0.0f, 0.0f, 1.0f);
		gl.glTexCoord2f(0.0f, 1.0f);
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

		// gl.glPopMatrix();

		cgtexture.disable(gl);

		// duplicate:
		// duplicate2(gl, -2.0f, 0.0f, 0.0f);
		// duplicate2(gl, -1.0f, 0.0f, 0.0f);
		// duplicate2(gl, 1.0f, 0.0f, 0.0f);
		// duplicate2(gl, 2.0f, 0.0f, 0.0f);

		gl.glFlush();
		rquad -= 1.0f;
	}

	public void duplicate2(GL2 gl, float x, float y, float z) {
		gl.glPushMatrix();

		// gl.glLoadIdentity();
		gl.glTranslatef(x, y, z);

		// Rotate The Cube On X, Y & Z
		// giving different colors to different sides
		gl.glRotatef(rquad, 1.0f, 1.0f, 1.0f);

		cgtexture.enable(gl);
		cgtexture.bind(gl);

		// Start Drawing The Cube
		gl.glBegin(GL2.GL_QUADS);

		// red color
		// gl.glColor3f(1f, 0f, 0f);
		// gl.glVertex3f(1.0f, 1.0f, -1.0f);
		// gl.glVertex3f(-1.0f, 1.0f, -1.0f);
		// gl.glVertex3f(-1.0f, 1.0f, 1.0f);
		// gl.glVertex3f(1.0f, 1.0f, 1.0f);

		// green color
		gl.glColor3f(1.0f, 1.0f, 1.0f);
		// gl.glTexCoord3f(1.0f, 0.0f, 1.0f);
		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(cubeWidth, -cubeWidth, cubeWidth);
		// gl.glTexCoord3f(1.0f, 0.0f, 0.0f);
		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(-cubeWidth, -cubeWidth, cubeWidth);
		// gl.glTexCoord3f(0.0f, 0.0f, 0.0f);
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(-cubeWidth, -cubeWidth, -cubeWidth);
		// gl.glTexCoord3f(0.0f, 0.0f, 1.0f);
		gl.glTexCoord2f(0.0f, 1.0f);
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

		cgtexture.disable(gl);

		gl.glFlush();
	}

	private Float xcamrotLast;
	private Float lightdisLast;
	private float lightdis = 1.0f;

	@Override
	public void mouseDragged(MouseEvent me) {
		// TODO Auto-generated method stub
		if (xcamrotLast != null)
			xcamrot += ((((float) me.getY()) / canvas.getHeight()) - xcamrotLast) * 360.0f;
		xcamrotLast = (((float) me.getY()) / canvas.getHeight());

		if (lightdisLast != null)
			lightdis += ((((float) me.getX()) / canvas.getWidth()) - lightdisLast) * 10.0f;
		lightdisLast = (((float) me.getX()) / canvas.getWidth());

		canvas.display();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
