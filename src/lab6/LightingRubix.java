package lab6;

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

public class LightingRubix implements GLEventListener, MouseMotionListener {
	JFrame jf;
	GLCanvas canvas;
	GLProfile profile;
	GLCapabilities caps;
	Dimension dim = new Dimension(800, 600);
	FPSAnimator animator;
	Texture front, back, left, right;
	float xcamrot = 0.0f;
	float lightdis = 1.0f;
	float time; // in seconds
	float cycletime = 10.0f;
	static int framerate = 60;
	float lightpos[] = { 50.0f, 100.0f, 200.0f, 1.0f };
	float lightpos1[] = { 200.0f, 0.0f, 200.0f, 1.0f };

	public LightingRubix() {
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
		new LightingRubix();
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
		glu.gluPerspective(80.0, 1.0, 1.0, 3000.0);
		gl2.glMatrixMode(GL2.GL_MODELVIEW);
		gl2.glLoadIdentity();
		glu.gluLookAt(0.0, 0.0, 200.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0);

		gl2.glEnable(GL2.GL_NORMALIZE);
		try {
			front = TextureIO.newTexture(new File("rubix.jpg"), true);
			front.enable(gl2);
			front.bind(gl2);
		} catch (GLException | IOException e) {
			e.printStackTrace();
		}
	}

	public void drawFace(String faceType, float x, float y, float z, GL2 gl2, Texture img, float len) {
		float topleftx = x;
		float toplefty = y;
		float topleftz = z;
		float toprightx = 0;
		float toprighty = 0;
		float toprightz = 0;
		float downleftx = 0;
		float downlefty = 0;
		float downleftz = 0;
		float downrightx = 0;
		float downrighty = 0;
		float downrightz = 0;
		int nx = 0;
		int ny = 0;
		int nz = 0;
		if (faceType == "front") {
			toprightx = x + len;
			toprighty = y;
			toprightz = z;
			downleftx = x;
			downlefty = y - len;
			downleftz = z;
			downrightx = x + len;
			downrighty = y - len;
			downrightz = z;
			nz = 1;
		}
		if (faceType == "back") {
			toprightx = x + len;
			toprighty = y;
			toprightz = z;
			downleftx = x;
			downlefty = y - len;
			downleftz = z;
			downrightx = x + len;
			downrighty = y - len;
			downrightz = z;
			nz = -1;
		}
		if (faceType == "left") {
			toprightx = x;
			toprighty = y;
			toprightz = z + len;
			downleftx = x;
			downlefty = y - len;
			downleftz = z;
			downrightx = x;
			downrighty = y - len;
			downrightz = z + len;
			nx = -1;
		}
		if (faceType == "right") {
			toprightx = x;
			toprighty = y;
			toprightz = z + len;
			downleftx = x;
			downlefty = y - len;
			downleftz = z;
			downrightx = x;
			downrighty = y - len;
			downrightz = z + len;
			nx = 1;
		}
		if (faceType == "top") {
			toprightx = x + len;
			toprighty = y;
			toprightz = z;
			downleftx = x;
			downlefty = y;
			downleftz = z - len;
			downrightx = x + len;
			downrighty = y;
			downrightz = z - len;
			ny = 1;
		}
		if (faceType == "bottle") {
			toprightx = x + len;
			toprighty = y;
			toprightz = z;
			downleftx = x;
			downlefty = y;
			downleftz = z - len;
			downrightx = x + len;
			downrighty = y;
			downrightz = z - len;
			ny = -1;
		}
		img.enable(gl2);
		img.bind(gl2);

		gl2.glBegin(GL2.GL_QUADS);
		gl2.glNormal3f(nx, ny, nz);
		gl2.glColor3f(0.0f, 1.0f, 0.0f); // set the color of the quad
		gl2.glTexCoord2f(0.0f, 1.0f);
		gl2.glVertex3f(topleftx, toplefty, topleftz); // Top Left
		gl2.glTexCoord2f(1.0f, 1.0f);
		gl2.glVertex3f(toprightx, toprighty, toprightz); // Top Right
		gl2.glTexCoord2f(1.0f, 0.0f);
		gl2.glVertex3f(downrightx, downrighty, downrightz);
		// Bottom Right
		gl2.glTexCoord2f(0.0f, 0.0f);
		gl2.glVertex3f(downleftx, downlefty, downleftz);
		gl2.glEnd();

		// img.disable(gl2);

		gl2.glFlush();
	}

	public void display(GLAutoDrawable dr) { // clear the screen and draw
												// "Save the Screens"
		GL2 gl2 = dr.getGL().getGL2();
		GLU glu = new GLU();
		GLUT glut = new GLUT();

		gl2.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

		gl2.glEnable(GL2.GL_LIGHTING);
		gl2.glPushMatrix();
		gl2.glRotated(xcamrot, 1.0, 0.0, 0.0);

		// set up light 1

		// gl2.glLightf(GL2.GL_LIGHT1, GL2.GL_CONSTANT_ATTENUATION, 0.0f);
		// gl2.glLightf(GL2.GL_LIGHT1, GL2.GL_LINEAR_ATTENUATION, 0.0f);
		// gl2.glLightf(GL2.GL_LIGHT1, GL2.GL_QUADRATIC_ATTENUATION, 0.001f);
		float ac[] = { 3.0f, 3.0f, 3.0f, 1.0f };

		gl2.glEnable(GL2.GL_LIGHT1);

		float dc[] = { 3.0f, 3.0f, 3.0f, 1.0f };
		float sc[] = { 3.0f, 3.0f, 3.0f, 1.0f };
		gl2.glLightfv(GL2.GL_LIGHT1, GL2.GL_POSITION, lightpos1, 0);
		gl2.glLightfv(GL2.GL_LIGHT1, GL2.GL_AMBIENT, ac, 0);
		gl2.glLightfv(GL2.GL_LIGHT1, GL2.GL_DIFFUSE, dc, 0);
		gl2.glLightfv(GL2.GL_LIGHT1, GL2.GL_SPECULAR, sc, 0);
		// gl2.glEnable(GL2.GL_NORMALIZE);
		gl2.glPushMatrix();
		gl2.glRotated(time * 20.0f, 0.0, 1.0, 0.0);
		// gl2.glColor3f(0.1f, 0.0f, 1.0f);
		float df[] = { 0.0f, 0.2f, 1.0f, 0.0f };
		gl2.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_AMBIENT_AND_DIFFUSE, df, 0);
		float sf[] = { 1.0f, 1.0f, 1.0f, 0.0f };
		gl2.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_SPECULAR, sf, 0);
		gl2.glMaterialf(GL2.GL_FRONT_AND_BACK, GL2.GL_SHININESS, 120.0f);
		float len = 100;
		drawFace("front", -50f, 50f, 50f, gl2, front, len);
		drawFace("back", -50f, 50f, 50f - len, gl2, front, len);
		drawFace("left", -50f, 50f, 50f - len, gl2, front, len);
		drawFace("right", -50f + len, 50f, 50f - len, gl2, front, len);
		drawFace("top", -50f, 50f, 50f, gl2, front, len);
		drawFace("bottle", -50f, 50f - len, 50f, gl2, front, len);
		gl2.glPopMatrix();

		// gl2.glPushMatrix(); // draw the light source's position using a
		// yellow
		// sphere
		// gl2.glDisable(GL2.GL_LIGHTING);
		// gl2.glColor3d(0.95, 0.9, 0.0);
		// gl2.glTranslated(lightpos[0], lightpos[1], lightpos[2]);
		// glut.glutSolidSphere(3.0, 100, 100);
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
