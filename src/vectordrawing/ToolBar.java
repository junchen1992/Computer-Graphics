package vectordrawing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Observer;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 * ToolBar - this is a widget for a list of radio buttons.
 * 
 * @author Eric McCreath
 * 
 */

public class ToolBar extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private ButtonGroup toolgroup;
	private ArrayList<JRadioButton> buttons;
	private ArrayList<Observer> observers;
	private Hashtable<ButtonModel, Object> reference;

	/** map */
	private HashMap<String, JLabel> map;

	public ToolBar(int axis) {
		this.setLayout(new BoxLayout(this, axis));
		toolgroup = new ButtonGroup();
		buttons = new ArrayList<JRadioButton>();
		observers = new ArrayList<Observer>();
		reference = new Hashtable<ButtonModel, Object>();

		/***/
		map = new HashMap<String, JLabel>();
	}

	public void addbutton(String text, Object command) {
		JRadioButton res = new JRadioButton(text);
		res.addActionListener(this);
		reference.put(res.getModel(), command);
		toolgroup.add(res);
		buttons.add(res);
		this.add(res);
		if (buttons.size() == 1)
			res.setSelected(true);
	}

	/**
	 * add to the toolbar a JLabel.
	 * 
	 * @param item
	 */
	public void addLabel(String name, String def, String connectedButton) {
		JLabel label = new JLabel(name + ": " + def);
		map.put(connectedButton, label);
		this.add(label);
	}

	/**
	 * add to the toolbar a JButton.
	 * 
	 * @param name
	 * @param command
	 */
	public void addJButton(String name, Object command) {
		JButton button = new JButton(name);
		button.setActionCommand((String) command);
		button.addActionListener(this);
		this.add(button);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getActionCommand().equalsIgnoreCase("INCTHICKNESS")) {
			JLabel label = map.get("THICKNESS");
			double thickness = Double.parseDouble(label.getText().split(":")[1]
					.trim()) + 1.0;
			if (thickness <= 0) {
				thickness = 1;
			}
			if (thickness >= 20) {
				thickness = 20;
			}
			label.setText(label.getText().split(":")[0] + ": " + thickness);
		} else if (arg0.getActionCommand().equalsIgnoreCase("DECTHICKNESS")) {
			JLabel label = map.get("THICKNESS");
			double thickness = Double.parseDouble(label.getText().split(":")[1]
					.trim()) - 1.0;
			if (thickness <= 0) {
				thickness = 1;
			}
			if (thickness >= 20) {
				thickness = 20;
			}
			label.setText(label.getText().split(":")[0] + ": " + thickness);
		} else if (arg0.getActionCommand().equalsIgnoreCase("INCTRANSPARENCY")) {
			JLabel label = map.get("TRANSPARENCY");
			int transparency = Integer.parseInt(label.getText().split(":")[1]
					.replace("%", "").trim()) + 10;
			if (transparency < 0) {
				transparency = 0;
			} else if (transparency > 100) {
				transparency = 100;
			}
			label.setText(label.getText().split(":")[0] + ": " + transparency
					+ "%");
		} else if (arg0.getActionCommand().equalsIgnoreCase("DECTRANSPARENCY")) {
			JLabel label = map.get("TRANSPARENCY");
			int transparency = Integer.parseInt(label.getText().split(":")[1]
					.replace("%", "").trim()) - 10;
			if (transparency < 0) {
				transparency = 0;
			} else if (transparency > 100) {
				transparency = 100;
			}
			label.setText(label.getText().split(":")[0] + ": " + transparency
					+ "%");
		}

		for (Observer o : observers)
			o.update(null, null);
	}

	public Object getSelectCommand() {
		return reference.get(toolgroup.getSelection());
	}

	public void addChangeObserver(Observer observer) {
		observers.add(observer);
	}

	public void removeChangeObserver(Observer observer) {
		observers.remove(observer);
	}

	public JRadioButton getButton(int i) {
		return buttons.get(i);
	}

	/**
	 * 
	 * @return the currently chosen thickness.
	 */
	public double getThickness() {
		double thickness = Double.parseDouble(map.get("THICKNESS").getText()
				.split(":")[1].trim());
		return thickness;
	}

	/**
	 * 
	 * @return the currently chosen transparency.
	 */
	public int getTransparency() {
		int transparency = Integer.parseInt(map.get("TRANSPARENCY").getText()
				.split(":")[1].replace("%", "").trim());
		return transparency;
	}
}