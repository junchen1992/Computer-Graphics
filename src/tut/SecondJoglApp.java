package tut;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;

/**
 * This is a basic JOGL app. Feel free to reuse this code or modify it.
 * 这是个基础的JOGL程序，你可以随意重用该代码或者修改它。
 */
public class SecondJoglApp extends JFrame {

	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		final SecondJoglApp app = new SecondJoglApp();

		// show what we've done
		// 看一下我们做了什么
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				app.setVisible(true);
			}
		});
	}

	public SecondJoglApp() {
		// set the JFrame title
		// 设置JFrame标题
		super("Second JOGL Application");

		// kill the process when the JFrame is closed
		// 当JFrame关闭的时候，结束进程
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// only three JOGL lines of code ... and here they are
		// 只有三行JOGL代码 ... 如下
		GLProfile profile = GLProfile.getDefault();
		GLCapabilities glcaps = new GLCapabilities(profile);
		GLCanvas glcanvas = new GLCanvas(glcaps);
		// glcanvas.addGLEventListener();

		// add the GLCanvas just like we would any Component
		// 像其它组件一样把GLCanvas加入
		getContentPane().add(glcanvas, BorderLayout.CENTER);
		setSize(500, 300);

		// center the JFrame on the screen
		// 使JFrame显示在屏幕中央
		centerWindow(this);
	}

	public void centerWindow(Component frame) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = frame.getSize();

		if (frameSize.width > screenSize.width)
			frameSize.width = screenSize.width;
		if (frameSize.height > screenSize.height)
			frameSize.height = screenSize.height;
		frame.setLocation((screenSize.width - frameSize.width) >> 1, (screenSize.height - frameSize.height) >> 1);
	}
}
