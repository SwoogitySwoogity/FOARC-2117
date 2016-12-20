package org.raiderrobotix.autonhelper;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class InstructionSet extends ArrayList<InstructionPanel> {

	private static final long serialVersionUID = 1L;
	private static InstructionSet _instance;

	private InstructionSet() {
	}

	public static InstructionSet getInstance() {
		if (_instance == null) {
			_instance = new InstructionSet();
		}
		return _instance;
	}

	public JPanel getPanel() {
		JPanel nonScroll = new JPanel();
		nonScroll.setLayout(new GridLayout(this.size(), 1, 0, 15));
		for (InstructionPanel i : this) {
			i.update();
			nonScroll.add(i);
			i.setPreferredSize(new Dimension(790, 56));
		}
		JScrollPane scroll = new JScrollPane(nonScroll,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		if (this.size() > 7) {
			scroll.setPreferredSize(new Dimension(829, 510));
		}
		scroll.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		JPanel ret = new JPanel();
		ret.add(scroll);
		return ret;
	}

	public InstructionPanel remove(int index) {
		InstructionPanel r = super.remove(index);
		for (int i = 0; i < this.size(); i++) {
			this.get(i).step = i + 1;
		}
		AutonUI.getInstance().setComponentsInPane(false);
		return r;
	}

	public boolean add(InstructionPanel e) {
		super.add(e);
		e.update();
		return true;
	}

}
