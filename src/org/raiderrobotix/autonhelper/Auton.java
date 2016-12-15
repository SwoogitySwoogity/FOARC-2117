package org.raiderrobotix.autonhelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import org.raiderrobotix.frc2017.Drivebase;

import edu.wpi.first.wpilibj.Timer;

public final class Auton extends ArrayList<Instruction> {

	private static final long serialVersionUID = 1L;

	public Auton(File shortHandFile) throws FileNotFoundException {
		Scanner infile = new Scanner(shortHandFile);
		while (infile.hasNext()) {
			String line = infile.nextLine();
			if (!(line.substring(0, 2).equals("$ "))) {
				continue;
			}
			line = line.substring(2);
			Instruction i = new Instruction();
			while (line.indexOf(" ") >= 0) {
				i.add(line.substring(0, line.indexOf(" ")));
				line = line.substring(line.indexOf(" ") + 1);
			}
			if (line.length() > 0) {
				i.add(line);
			}
			this.add(i);
		}
		infile.close();
	}

	public Auton(ArrayList<Instruction> autonList) {
		for (Instruction i : autonList) {
			this.add(i);
		}
	}

	public void auton() { // TODO: add functions of robot
		Drivebase drives = Drivebase.getInstance();
		Timer timer = new Timer();
		Instruction i = this.get(0);
		switch(Integer.parseInt(i.getNext())) {
		case Mechanism.DRIVES:
			String fn = i.getNext();
			double value = Double.parseDouble(i.getNext());
			double speed = Double.parseDouble(i.getNext());
			switch(Integer.parseInt(fn)) {
			case Mechanism.Drives.STRAIGHT:
				if(drives.driveStraight(value, speed)) {
					timer.start();
					timer.reset();
					this.remove(0);
				}
				break;
			case Mechanism.Drives.TURN:
				if(drives.turnToAngle(value, speed)) {
					timer.start();
					timer.reset();
					this.remove(0);
				}
				break;
			}
			break;
		case Mechanism.WAIT:
			if(timer.get() >= Double.parseDouble(i.getNext())) {
				timer.reset();
				this.remove(0);
			}
			break;
		}
	}

	public void writeCode() {
		// TODO: implement
		// TODO: Decide return type and parameters
	}
	
}
