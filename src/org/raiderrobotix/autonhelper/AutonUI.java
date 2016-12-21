package org.raiderrobotix.autonhelper;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public final class AutonUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private static AutonUI _instance;

	private Container pane;
	private InstructionSet is;
	private JButton addButton = new JButton("Add Step");
	private JButton helpButton = new JButton("Constants");
	private JButton ftpButton = new JButton("Send Code to Robot");
	private JButton copyButton = new JButton("Copy Code to Clipboard");

	private AutonUI() {
		super("Auton Helper");
		pane = this.getContentPane();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pane.setLayout(new BorderLayout());
		is = InstructionSet.getInstance();
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				is.add(new InstructionPanel(is.size() + 1));
				updateUI(false);
			}
		});
		helpButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, new JLabel(
						"<html>There are currently no constants</html>",
						SwingConstants.CENTER), "Constants",
						JOptionPane.PLAIN_MESSAGE);
			}
		});
		ftpButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<Instruction> instructions = new ArrayList<Instruction>();
				for (InstructionPanel i : is) {
					instructions.add(i.getInstruction());
				}
				try {
					Utility.sendOverFile(instructions);
				} catch (IOException e1) {
					JOptionPane
							.showMessageDialog(
									null,
									new JLabel(
											"<html>There was an error<br>sending the file.</html>",
											SwingConstants.CENTER), "ERROR",
									JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		copyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String methodName = Utility.getName();
					Clipboard c = Toolkit.getDefaultToolkit()
							.getSystemClipboard();
					StringSelection code = new StringSelection(is
							.getCode(methodName));
					c.setContents(code, code);
					JOptionPane.showMessageDialog(null, new JLabel(
							"Copy Successful!", SwingConstants.CENTER),
							"Message", JOptionPane.PLAIN_MESSAGE);
				} catch (Exception e1) {
					JOptionPane
							.showMessageDialog(
									null,
									new JLabel(
											"<html>There was an error<br>copying the code.</html>",
											SwingConstants.CENTER), "ERROR",
									JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		updateUI(true);
		this.setSize(850, 600);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	public static AutonUI getInstance() {
		if (_instance == null) {
			_instance = new AutonUI();
		}
		return _instance;
	}

	/**
	 * Repaints and revalidates all components and puts them together if this
	 * is the first time.
	 * 
	 * @param init
	 *            Is this the first time the UI is running for the user?
	 */
	protected void updateUI(boolean init) {
		if (init) {
			pane.removeAll();
			JPanel northernButtonPanel = new JPanel();
			northernButtonPanel.setLayout(new GridLayout(1, 4, 10, 10));
			northernButtonPanel.add(addButton);
			northernButtonPanel.add(ftpButton);
			northernButtonPanel.add(copyButton);
			northernButtonPanel.add(helpButton);
			pane.add(northernButtonPanel, BorderLayout.NORTH);
		} else {
			try {
				pane.remove(1);
			} catch (Exception e) {
			}
		}
		pane.add(is.getPanel(), BorderLayout.CENTER);
		this.revalidate();
		this.repaint();
	}

	public static void main(String[] args) {
		AutonUI.getInstance();
	}

}