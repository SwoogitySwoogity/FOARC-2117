package org.raiderrobotix.frc2017;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;

public class AutonController {

	private static AutonController m_instance;

	private int m_step;
	private final Drivebase m_drives;
	private final Timer m_timer;
	private final Joystick m_switchBox;

	private AutonController() {
		m_drives = Drivebase.getInstance();
		m_switchBox = new Joystick(Constants.SWITCH_BOX_PORT);
		m_timer = new Timer();
		m_step = 0;
	}

	public static AutonController getInstance() {
		if (m_instance == null) {
			m_instance = new AutonController();
		}
		return m_instance;
	}

	public void resetStep() {
		m_step = 0;
	}
	
	public int getAutonChosen() {
		int n = 0;
		if (m_switchBox.getRawButton(5)) {
			n += 1;
		}
		if (m_switchBox.getRawButton(12)) {
			n += 2;
		}
		if (m_switchBox.getRawButton(7)) {
			n += 3;
		}
		if (m_switchBox.getRawButton(11)) {
			n += 4;
		}
		if (m_switchBox.getRawButton(6)) {
			n += 5;
		}
		if (m_switchBox.getRawButton(8)) {
			n += 6;
		}
		return n;
	}
	
	public void useFTPFile() {
		
	}
}
