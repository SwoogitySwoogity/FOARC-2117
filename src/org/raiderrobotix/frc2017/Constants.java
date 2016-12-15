package org.raiderrobotix.frc2017;

public abstract class Constants { // TODO: get ALL values

	// FTP Information
	public static final String FTP_AUTON_FILE_PATH = "/home/lvuser/auton.dat";
	public static final String FTP_PREFIX = "ftp://roboRIO-25-frc.local";
	
	// Auto-Driving Constants
	public static final double TURN_ANGLE_TOLERANCE = 1.0; // (In Degrees)
	public static final double DRIVE_STRAIGHT_ANGLE_TOLERANCE = 1.0;
	public static final double DRIVE_STRAIGHT_SPEED_SUBTRACTION = 0.15; // TODO:
																		// get
	public static final double DRIVE_STRAIGHT_DISTANCE_TOLERANCE = 1.0; // TODO:
																		// get

	// PWMs (Control) TODO: get
	public static final int LEFT_DRIVES_PWM = 8;
	public static final int RIGHT_DRIVES_PWM = 0;
	public static final int LEFT_BRAKE_PWM = 9;
	public static final int RIGHT_BRAKE_PWM = 1;

	// Brake Positions
	public static final double LEFT_BRAKES_ON = 0.42;
	public static final double LEFT_BRAKES_OFF = 0.57;
	public static final double RIGHT_BRAKES_ON = 0.55;
	public static final double RIGHT_BRAKES_OFF = 0.34;

	// Digital Sensors
	public static final int LEFT_ENCODER_PWM_A = 9; // TODO: Get Values
	public static final int LEFT_ENCODER_PWM_B = 4;
	public static final int RIGHT_ENCODER_PWM_A = 6;
	public static final int RIGHT_ENCODER_PWM_B = 7;

	// Auton Information
	private static final double TIRE_DIAMETER = 0.0; // TODO: Get Values // (In
														// Inches)
	private static final double TIRE_CIRCUMFERENCE = TIRE_DIAMETER * Math.PI;
	private static final double COUNTS_PER_REVOLUTION = 85.75; // TODO: Fix Math
	public static final double INCHES_PER_COUNT = TIRE_CIRCUMFERENCE
			/ COUNTS_PER_REVOLUTION;

	// Joysticks
	public static final int LEFT_JOYSTICK_PORT = 0;
	public static final int RIGHT_JOYSTICK_PORT = 1;
	public static final int OPERATOR_JOYSTICK_PORT = 2;
	public static final int SWITCH_BOX_PORT = 3;
	public static final double JOYSTICK_DEADBAND = 0.2;

	// CAN Addresses TODO: Get Values

	// Inversions TODO: get
	public static final boolean RIGHT_DRIVE_MOTORS_INVERTED = false;
	public static final boolean LEFT_DRIVE_MOTORS_INVERTED = true;
	public static final boolean LEFT_ENCODER_INVERTED = true;
	public static final boolean RIGHT_ENCODER_INVERTED = false;

}
