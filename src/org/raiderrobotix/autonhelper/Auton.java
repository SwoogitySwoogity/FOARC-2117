package org.raiderrobotix.autonhelper;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import org.raiderrobotix.frc2017.Constants;
import org.raiderrobotix.frc2017.Drivebase;

public final class Auton extends ArrayList<Instruction> {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	public Auton() throws IOException, ClassNotFoundException {
		ObjectInputStream in = new ObjectInputStream(new FileInputStream(
				Constants.AUTON_FILE_PATH));
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

	public double auton(double time) { // TODO: add functions of robot
		try {
			Drivebase drives = Drivebase.getInstance();
			Instruction i = this.get(0);
			switch (Integer.parseInt(i.getNext())) {
			case Mechanism.DRIVES:
				if (drives.brakesAreOn()) {
					drives.brakesOff();
				}
				String fn = i.getNext();
				double value = Double.parseDouble(i.getNext());
				double speed = Double.parseDouble(i.getNext());
				switch (Integer.parseInt(fn)) {
				case Mechanism.Drives.STRAIGHT:
					if (drives.driveStraight(value, speed)) {
						time = 0.0;
						this.remove(0);
					}
					break;
				case Mechanism.Drives.TURN:
					if (drives.turnToAngle(value, speed)) {
						time = 0.0;
						this.remove(0);
					}
					break;
				}
				break;
			case Mechanism.WAIT:
				if (time >= Double.parseDouble(i.getNext())) {
					time = 0.0;
					this.remove(0);
				}
				break;
			case Mechanism.BRAKES:
				drives.setSpeed(0.0);
				if (Integer.parseInt(i.getNext()) == Mechanism.Drives.BRAKES_ON) {
					drives.brakesOn();
				} else {
					drives.brakesOff();
				}
				time = 0.0;
				this.remove(0);
			}
		} catch (NumberFormatException e) {
			System.out.println("Number Format Exception");
			this.remove(0);
		}
		return time;
	}

	public void writeCode() {
		// TODO: implement
		// TODO: Decide return type and parameters
	}

}
