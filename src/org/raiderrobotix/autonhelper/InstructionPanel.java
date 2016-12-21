package org.raiderrobotix.autonhelper;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public final class InstructionPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	// Components of an Instruction Panel
	private JLabel stepLabel = new JLabel("", SwingConstants.CENTER);
	private JButton removeButton = new JButton("X");
	private JButton reorderButtonUp = new JButton("<html>&#9650<html>");
	private JButton reorderButtonDown = new JButton("<html>&#9660<html>");
	private JComboBox<Object> mechanismDropDown;
	private JLabel speedLabel = new JLabel("Speed: ");
	private JTextField speedField = new JTextField();
	private JPanel speedPanel = new JPanel();
	private JLabel valueLabel = new JLabel("Value: ");
	private JTextField valueField = new JTextField();
	private JPanel valuePanel = new JPanel();
	private JPanel extraDataPanel = new JPanel();
	private JPanel reorderPanel;
	
	// Instruction Panel Resources
	protected int step;
	private InstructionSet instructionSet;
	private HashMap<String, ArrayList<Integer>> mechanismMapping;
	private GridLayout dataPanelLayout = new GridLayout(1, 2, 5, 5);
	private long lastTimeClickedSpeed;

	public InstructionPanel(int initStep) {
		// Assign Variables
		step = initStep;
		mapMechanisms();
		lastTimeClickedSpeed = System.currentTimeMillis() - 2000;
		mechanismDropDown = new JComboBox<Object>(
				(mechanismMapping.keySet().toArray()));
		instructionSet = InstructionSet.getInstance();
		
		// Create panels For Extra Auton Data
		speedPanel.setLayout(dataPanelLayout);
		speedPanel.add(speedLabel);
		speedPanel.add(speedField);
		speedField.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {
			}

			public void mouseEntered(MouseEvent e) {
			}

			public void mouseExited(MouseEvent e) {
			}

			public void mousePressed(MouseEvent e) {
			}

			public void mouseReleased(MouseEvent e) {
				if (lastTimeClickedSpeed + 300 >= System.currentTimeMillis()) {
					speedField.setText(Double.toString(Utility.getSpeed()));
					lastTimeClickedSpeed = System.currentTimeMillis() - 2000;
				} else {
					lastTimeClickedSpeed = System.currentTimeMillis();
				}
			}
		});
		valuePanel.setLayout(dataPanelLayout);
		valuePanel.add(valueLabel);
		valuePanel.add(valueField);
		extraDataPanel.setLayout(new GridLayout(2, 1, 5, 5));
		
		// Create Accessory Buttons and Functions for Instruction Panels
		removeButton.setForeground(Color.RED);
		removeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				instructionSet.remove(step - 1);
			}
		});
		final InstructionPanel ip = this;
		reorderButtonUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				instructionSet.add(step - 2, ip);
				instructionSet.remove(step);
			}
		});
		reorderButtonDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				instructionSet.add(step + 1, ip);
				instructionSet.remove(step - 1);
			}
		});
		
		// Create Mechanism Drop-Down menu
		mechanismDropDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				extraDataPanel.removeAll();
				switch (mechanismMapping.get(
						(String) mechanismDropDown.getSelectedItem()).get(0)) {
				case Mechanism.DRIVES:
					extraDataPanel.add(valuePanel);
					extraDataPanel.add(speedPanel);
					break;
				case Mechanism.BRAKES:
					extraDataPanel.add(new JLabel(""));
					extraDataPanel.add(new JLabel(""));
					break;
				case Mechanism.WAIT:
					extraDataPanel.add(valuePanel);
					extraDataPanel.add(new JLabel(""));
					break;
				}
				AutonUI.getInstance().updateUI(false);
			}
		});
		mechanismDropDown.setBackground(Color.WHITE);
		
		// Assign Layout and Components
		this.setLayout(dataPanelLayout);
		JPanel westPanel = new JPanel();
		westPanel.setLayout(new GridLayout(2, 1, 2, 2));
		JPanel northWesternPanel = new JPanel();
		northWesternPanel.setLayout(dataPanelLayout);
		northWesternPanel.add(stepLabel);
		JPanel northWesternSubPanel = new JPanel();
		northWesternSubPanel.setLayout(dataPanelLayout);
		northWesternSubPanel.add(removeButton);
		reorderPanel = new JPanel();
		reorderPanel.setLayout(dataPanelLayout);
		reorderPanel.add(step == 1 ? new JLabel("") : reorderButtonUp);
		reorderPanel
				.add(step == InstructionSet.getInstance().size() ? new JLabel(
						"") : reorderButtonDown);
		northWesternSubPanel.add(reorderPanel);
		northWesternPanel.add(northWesternSubPanel);
		westPanel.add(northWesternPanel);
		westPanel.add(mechanismDropDown);
		this.add(westPanel);
		extraDataPanel.add(valuePanel);
		extraDataPanel.add(new JLabel(""));
		this.add(extraDataPanel);
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
	}

	/**
	 * Assign drop down menu items to the correct mechanism IDs for shorthand code.
	 */
	private void mapMechanisms() {
		mechanismMapping = new HashMap<String, ArrayList<Integer>>();
		
		// Drive Straight
		ArrayList<Integer> n = new ArrayList<Integer>();
		n.add(Mechanism.DRIVES);
		n.add(Mechanism.Drives.STRAIGHT);
		mechanismMapping.put("Drive Straight", n);
		
		// Turn To Angle
		n = new ArrayList<Integer>();
		n.add(Mechanism.DRIVES);
		n.add(Mechanism.Drives.TURN);
		mechanismMapping.put("Drive- Spin", n);
		
		// Brakes On
		n = new ArrayList<Integer>();
		n.add(Mechanism.BRAKES);
		n.add(Mechanism.Drives.BRAKES_ON);
		mechanismMapping.put("Brakes In", n);
		
		// Brakes Off
		n = new ArrayList<Integer>();
		n.add(Mechanism.BRAKES);
		n.add(Mechanism.Drives.BRAKES_OFF);
		mechanismMapping.put("Brakes Out", n);
		
		// Timer- Wait
		n = new ArrayList<Integer>();
		n.add(Mechanism.WAIT);
		mechanismMapping.put("Timer- Wait", n);
	}

	/**
	 * Update instruction panel's appearance for minor changes.
	 */
	public void update() {
		stepLabel.setText("Step " + Integer.toString(step));
		reorderPanel.removeAll();
		reorderPanel.setLayout(dataPanelLayout);
		reorderPanel.add(step == 1 ? new JLabel("") : reorderButtonUp);
		reorderPanel
				.add(step == InstructionSet.getInstance().size() ? new JLabel(
						"") : reorderButtonDown);
	}

	/**
	 * Make shorthand code from user input on UI.
	 * 
	 * @return Instruction information made from what the user enters into the panel.
	 */
	public Instruction getInstruction() {
		Instruction ret = new Instruction();
		for (int i : mechanismMapping.get((String) mechanismDropDown
				.getSelectedItem())) {
			ret.add(Integer.toString(i));
		}
		switch (mechanismMapping.get(
				(String) mechanismDropDown.getSelectedItem()).get(0)) {
		case Mechanism.DRIVES:
			ret.add(valueField.getText());
			ret.add(speedField.getText());
			break;
		case Mechanism.WAIT:
			ret.add(valueField.getText());
			break;
		}
		ret.add("$");
		return ret;
	}

}
