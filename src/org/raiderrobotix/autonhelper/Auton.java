package org.raiderrobotix.autonhelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import org.raiderrobotix.frc2017.Constants;
import org.raiderrobotix.frc2017.Drivebase;

import edu.wpi.first.wpilibj.Timer;

public final class Auton extends ArrayList<Instruction> {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	public Auton(File shortHandFile) throws IOException, ClassNotFoundException {
		ObjectInputStream in = new ObjectInputStream(new FileInputStream(
				Constants.FTP_AUTON_FILE_PATH));
		for (Instruction i : (ArrayList<Instruction>) in.readObject()) {
			this.add(i);
		}
		in.close();
	}

	public Auton(ArrayList<Instruction> autonList) {
		for (Instruction i : autonList) {
			this.add(i);
		}
	}

	public void auton() { // TODO: add functions of robot
		try {
			Drivebase drives = Drivebase.getInstance();
			Timer timer = new Timer();
			Instruction i = this.get(0);
			switch (Integer.parseInt(i.getNext())) {
			case Mechanism.DRIVES:
				String fn = i.getNext();
				double value = Double.parseDouble(i.getNext());
				double speed = Double.parseDouble(i.getNext());
				switch (Integer.parseInt(fn)) {
				case Mechanism.Drives.STRAIGHT:
					if (drives.driveStraight(value, speed)) {
						timer.start();
						timer.reset();
						this.remove(0);
					}
					break;
				case Mechanism.Drives.TURN:
					if (drives.turnToAngle(value, speed)) {
						timer.start();
						timer.reset();
						this.remove(0);
					}
					break;
				}
				break;
			case Mechanism.WAIT:
				if (timer.get() >= Double.parseDouble(i.getNext())) {
					timer.reset();
					this.remove(0);
				}
				break;
			}
		} catch (NumberFormatException e) {
			System.out.println("Number Format Exception");
			this.remove(0);
		}
	}

	public void writeCode() {
		// TODO: implement
		// TODO: Decide return type and parameters
	}

}
