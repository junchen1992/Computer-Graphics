package lab8;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.Timer;

public class RayTracer implements ActionListener {

	/**
	 * RayTracer Eric McCreath 2009
	 */

	final static Dimension dim = new Dimension(800, 800);

	JFrame jframe;
	JComponent canvas;
	int numframes = 50;
	final BufferedImage frames[];
	int currentframe = 0;

	Timer timer;

	@SuppressWarnings("serial")
	public RayTracer() {
		jframe = new JFrame("RayTracer");
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frames = new BufferedImage[numframes];
		for (int i = 0; i < frames.length; i++) {
			frames[i] = new BufferedImage(dim.width, dim.height, BufferedImage.TYPE_INT_RGB);
		}

		canvas = new JComponent() {
			public void paint(Graphics g) {
				if (frames[currentframe] != null)
					g.drawImage(frames[currentframe], 0, 0, null);
			}
		};
		canvas.setPreferredSize(dim);
		jframe.getContentPane().add(canvas);
		jframe.pack();
		jframe.setVisible(true);

		timer = new Timer(100, this);
	}

	public static void main(String[] args) {
		RayTracer rt = new RayTracer();
		View view = new View(new P3D(4.0, 4.0, -20.0), new P3D(-2.0, -2.0, 10.0), new P3D(10.0, 0.0, 2.0),
				new P3D(0.0, -10.0, 2.0));
		double stage = 0.0;

		// generate frames

		for (int i = 0; i < rt.frames.length; i++) {
			Scene scene = makeScene(stage);
			rt.frames[i] = rt.raytrace(scene, view);
			stage += 1.0 / rt.numframes;
			rt.currentframe = i;
			rt.canvas.repaint();
		}

		// run animation
		rt.timer.start();
	}

	public void animate() {
		currentframe++;
		if (currentframe >= frames.length)
			currentframe = 0;
		Graphics g = canvas.getGraphics();
		canvas.paint(g);
	}

	private BufferedImage raytrace(Scene scene, View view) {
		System.out.println("Start Ray Tracing....");
		BufferedImage res = new BufferedImage(dim.width, dim.height, BufferedImage.TYPE_INT_RGB);
		P3D startdir = view.direction.add(view.across.scale(-0.5).add(view.down.scale(-0.5)));
		for (int y = 0; y < dim.height; y++) {
			for (int x = 0; x < dim.width; x++) {

				Ray r = new Ray(view.camera, startdir.add(view.across.scale(((double) x) / dim.getWidth()))
						.add(view.down.scale(((double) y) / dim.getHeight())).normalize());
				Color c = scene.raytrace(r);
				res.setRGB(x, y, c.getRGB());
			}
		}
		return res;
	}

	private static Scene makeScene(double step) {
		Scene res = new Scene();

		// body
		res.add(new Sphere(new P3D(0.0, 1.4, 0.0), 1.9, Color.blue));
		res.add(new Sphere(new P3D(0.0, 1.0, 0.0), 2.0, Color.blue));
		res.add(new Sphere(new P3D(0.0, -1.0, 0.0), 2.0, Color.red));
		res.add(new Sphere(new P3D(0.0, -1.3, 0.0), 2.0, Color.red));

		// legs
		for (int i = 0; i < 10; i++) {
			res.add(new Sphere(new P3D(-0.5, -2.6 - (i * 0.3), 0.0), 1.0 - (i * 0.05),
					(i % 2 == 0 ? Color.red : Color.green)));
			res.add(new Sphere(new P3D(0.5, -2.6 - (i * 0.3), 0.0), 1.0 - (i * 0.05),
					(i % 2 == 0 ? Color.red : Color.green)));
		}

		// head
		res.add(new Sphere(new P3D(0.0, 4.3, 0.0), 1.0, Color.white));
		res.add(new Sphere(new P3D(0.35, 4.35, -0.9), 0.1, Color.black));
		res.add(new Sphere(new P3D(-0.35, 4.35, -0.9), 0.1, Color.black));

		// balls

		res.add(new Sphere(ballpos(step), 0.3, Color.orange));
		res.add(new Sphere(ballpos(step + 1.0 / 5.0), 0.3, Color.green));
		res.add(new Sphere(ballpos(step + 2.0 / 5.0), 0.3, Color.red));
		res.add(new Sphere(ballpos(step + 3.0 / 5.0), 0.3, Color.yellow));
		res.add(new Sphere(ballpos(step + 4.0 / 5.0), 0.3, Color.white));
		return res;
	}

	private static P3D ballpos(double step) {

		double bhmax = 5.0;
		double bhmin = 0.3;
		double bwmax = 2.0;
		double bwmin = 1.0;

		double phase1 = 0.45;

		while (step > 1.0)
			step -= 1.0;
		if (step < phase1) {
			double phase = step / phase1;
			double x = bwmin - (bwmax + bwmin) * phase;
			double par = (phase - 0.5) * 2.0;
			double y = bhmin + bhmax * (1.0 - par * par);
			return new P3D(x, y, -2.5);
		} else if (step < 0.5) {
			double phase = (step - phase1) / (0.5 - phase1);
			double x = -bwmax + (bwmax - bwmin) * phase;
			double y = bhmin;
			return new P3D(x, y, -2.5);
		} else if (step < 0.5 + phase1) {
			double phase = (step - 0.5) / phase1;
			double x = -bwmin + (bwmax + bwmin) * phase;
			double par = (phase - 0.5) * 2.0;
			double y = bhmin + bhmax * (1.0 - par * par);
			return new P3D(x, y, -2.5);
		} else {
			double phase = (step - 0.5 - phase1) / (0.5 - phase1);
			double x = bwmax - (bwmax - bwmin) * phase;
			double y = bhmin;
			return new P3D(x, y, -2.5);

		}
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == timer) {
			animate();
		}
	}

}
