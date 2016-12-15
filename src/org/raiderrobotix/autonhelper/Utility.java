package org.raiderrobotix.autonhelper;

import java.awt.GridLayout;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.raiderrobotix.frc2017.Constants;

public abstract class Utility {

	/**
	 * Get a file from the user.
	 * 
	 * @return File that was chosen
	 */
	public static File getFile() {
		try {
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
		}
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Choose a File");
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		int a = JFileChooser.APPROVE_OPTION - 1;
		while (a != JFileChooser.APPROVE_OPTION
				|| fileChooser.getSelectedFile() == null)
			a = fileChooser.showDialog(null, "Use");
		return fileChooser.getSelectedFile();
	}

	/**
	 * Get a name for the new autonomous mode.
	 * 
	 * @return A string- name of the new auton.
	 */
	public static String getName() {
		String a = "";
		boolean ran = false;
		while (a.length() == 0 || a.indexOf(" ") >= 0) {
			if (ran)
				JOptionPane
						.showMessageDialog(
								null,
								"Please write a name with at least one character that contains no spaces, please.");
			a = JOptionPane.showInputDialog(null, "Enter a name", "Enter name",
					JOptionPane.PLAIN_MESSAGE);
			ran = true;
		}
		return a;
	}

	/**
	 * Get a speed value from the user.
	 * 
	 * @return A double value that will work with wpilibj motor setting.
	 */
	public static double getSpeed() {
		final JSlider slider = new JSlider(0, 100, 50);
		slider.setMajorTickSpacing(10);
		slider.setMinorTickSpacing(1);
		final JLabel value = new JLabel("50%");
		value.setHorizontalAlignment(SwingConstants.CENTER);
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				value.setText(Integer.toString(slider.getValue()) + "%");
			}
		});
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2, 1, 0, 8));
		panel.add(slider);
		panel.add(value);
		JOptionPane.showMessageDialog(null, panel, "Choose Motor Speed",
				JOptionPane.PLAIN_MESSAGE);
		return ((double) slider.getValue()) / 100.0;
	}

	/**
	 * Get a double value from the user.
	 * 
	 * @param title
	 *            The title for the input pane.
	 * 
	 * @return A string value of a double entered by the user.
	 */
	public static String getValue(String message) {
		String a = "";
		a = JOptionPane.showInputDialog(null, message, "Enter a value",
				JOptionPane.PLAIN_MESSAGE);
		if (a.indexOf("Constants.") >= 0 || a.indexOf("0.") >= 0)
			return a;
		else
			JOptionPane
					.showMessageDialog(null,
							"Make sure you are entering a 'Constants' number or decimal starting with '0.'");
		while (a.length() == 0)
			try {
				a = Double.toString(Double.parseDouble(JOptionPane
						.showInputDialog(null, message, "Enter a value",
								JOptionPane.PLAIN_MESSAGE)));
			} catch (NumberFormatException e) {
				a = "";
			}
		return Double.toString(Double.parseDouble(a));
	}

	public static void sendOverFile(ArrayList<Instruction> auton)
			throws IOException {
		URL url = new URL(Constants.FTP_PREFIX + Constants.FTP_AUTON_FILE_PATH);
		URLConnection conn = url.openConnection();
		ObjectOutputStream out = new ObjectOutputStream(conn.getOutputStream());
		out.writeObject(auton);
		out.close();
	}

}
