package lab3;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/*
 * DrawIt - 
 * Eric McCreath 2009
 */

public class DrawIt implements Runnable {

	static final Dimension dim = new Dimension(800, 600);

	JFrame jf;
	DrawArea da;
	JMenuBar bar;
	JMenu jmfile;
	JMenuItem jmiquit, jmiexport;
	ToolBar colorToolbar;
	ToolBar optionToolbar;

	public DrawIt() {
		SwingUtilities.invokeLater(this);
	}

	public void run() {
		jf = new JFrame();
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		da = new DrawArea(dim, this);
		// da.setFocusable(true);
		jf.getContentPane().add(da, BorderLayout.CENTER);

		// create a toolbar
		colorToolbar = new ToolBar(BoxLayout.Y_AXIS);
		colorToolbar.addbutton("Red", Color.RED);
		colorToolbar.addbutton("Blue", Color.BLUE);
		colorToolbar.addbutton("Green", Color.GREEN);
		colorToolbar.addbutton("Eraser", Color.WHITE);

		colorToolbar.addLabel("Thickness", "1.0", "THICKNESS");
		colorToolbar.addJButton("+", "INCTHICKNESS");
		colorToolbar.addJButton("-", "DECTHICKNESS");
		colorToolbar.addLabel("Transparency", "100%", "TRANSPARENCY");
		colorToolbar.addJButton("+", "INCTRANSPARENCY");
		colorToolbar.addJButton("-", "DECTRANSPARENCY");

		// create another toolbar:
		optionToolbar = new ToolBar(BoxLayout.Y_AXIS);
		// a placeholder does nothing:
		optionToolbar.addbutton("Line", "Line");
		optionToolbar.addbutton("Smudge", "Smudge");
		optionToolbar.addbutton("Spray", "SPRAY");
		optionToolbar.addbutton("Flood Fill", "FLOODFILL");

		JPanel jp = new JPanel();
		jp.setLayout(new BoxLayout(jp, BoxLayout.Y_AXIS));
		jp.add(colorToolbar);
		jp.add(optionToolbar);

		// jf.getContentPane().add(colorToolbar, BorderLayout.LINE_END);
		jf.getContentPane().add(jp, BorderLayout.LINE_END);

		// create some menus
		bar = new JMenuBar();
		jmfile = new JMenu("File");
		jmiexport = new JMenuItem("Export");
		jmfile.add(jmiexport);
		jmiexport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				da.export(new File("export.png"));
			}
		});

		jmiquit = new JMenuItem("Quit");
		jmfile.add(jmiquit);
		jmiquit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		bar.add(jmfile);
		jf.setJMenuBar(bar);

		jf.pack();
		jf.setVisible(true);
	}

	public static void main(String[] args) {
		@SuppressWarnings("unused")
		DrawIt sc = new DrawIt();
	}
}
