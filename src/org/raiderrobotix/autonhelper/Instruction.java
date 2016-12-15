package org.raiderrobotix.autonhelper;

import java.util.ArrayList;

public final class Instruction extends ArrayList<String> {

	private static final long serialVersionUID = 1L;

	private int _counter = -1;

	public Instruction() {
	}

	public Instruction(String s) {
		this.add(s);
	}

	public Instruction(ArrayList<String> list) {
		for (String i : list) {
			this.add(i);
		}
	}

	public String getNext() {
		_counter++;
		return this.get(_counter % (this.size()));
	}

}
