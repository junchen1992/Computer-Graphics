package lab3;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Observer;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
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

	public ToolBar(int axis) {
		this.setLayout(new BoxLayout(this, axis));
		toolgroup = new ButtonGroup();
		buttons = new ArrayList<JRadioButton>();
		observers = new ArrayList<Observer>();
		reference = new Hashtable<ButtonModel, Object>();
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

	@Override
	public void actionPerformed(ActionEvent arg0) {
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
}