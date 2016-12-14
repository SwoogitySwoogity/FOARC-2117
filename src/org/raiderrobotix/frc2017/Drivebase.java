package org.raiderrobotix.frc2017;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SPI.Port;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.VictorSP;

public class Drivebase {

	private static Drivebase m_instance;
	
	private final VictorSP m_leftDrives;
	private final VictorSP m_rightDrives;
	private final Servo m_rightBrake;
	private final Servo m_leftBrake;
	private final Encoder m_leftEncoder;
	private final Encoder m_rightEncoder;
	private boolean m_brakesOn;
	private int m_driveStep;
	private AHRS m_navX;
	private double m_headingYaw;
	
	private Drivebase() {
		m_navX = new AHRS(Port.kMXP);
		m_headingYaw = 0.0;
		
		m_leftDrives = new VictorSP(Constants.LEFT_DRIVES_PWM);
		m_rightDrives = new VictorSP(Constants.RIGHT_DRIVES_PWM);
		
		m_leftBrake = new Servo(Constants.LEFT_BRAKE_PWM);
		m_rightBrake = new Servo(Constants.RIGHT_BRAKE_PWM);
		
		m_leftEncoder = new Encoder(Constants.LEFT_ENCODER_PWM_A, Constants.LEFT_ENCODER_PWM_B, Constants.LEFT_ENCODER_INVERTED);
		m_rightEncoder = new Encoder(Constants.RIGHT_ENCODER_PWM_A, Constants.RIGHT_ENCODER_PWM_B, Constants.RIGHT_ENCODER_INVERTED);
		
		m_leftEncoder.setDistancePerPulse(Constants.INCHES_PER_COUNT);
		m_rightEncoder.setDistancePerPulse(Constants.INCHES_PER_COUNT);
		
		m_driveStep = 0;
	}
	
	public static Drivebase getInstance() {
		if (m_instance == null) {
			m_instance = new Drivebase();
		}
		return m_instance;
	}
	
	public void resetStep() {
		m_driveStep = 0;
	}
	
	public void setSpeed(double speed) {
		setSpeed(speed, speed);
	}
	
	public void setSpeed(double leftSpeed, double rightSpeed) {
		m_leftDrives.set(leftSpeed);
		m_rightDrives.set(rightSpeed);
	}
	
	public void brakesOn() {
		m_brakesOn = true;
		m_leftBrake.set(Constants.LEFT_BRAKES_ON);
		m_rightBrake.set(Constants.RIGHT_BRAKES_ON);
	}
	
	public void brakesOff() {
		m_brakesOn = false;
		m_leftBrake.set(Constants.LEFT_BRAKES_OFF);
		m_rightBrake.set(Constants.RIGHT_BRAKES_OFF);
	}
	
	public boolean brakesAreOn() {
		return m_brakesOn;
	}
	
	public double getLeftEncoderDistance() {
		return m_leftEncoder.getDistance();
	}
	
	public double getRightEncoderDistance() {
		return m_rightEncoder.getDistance();
	}
	
	public void resetEncoders() {
		m_leftEncoder.reset();
		m_rightEncoder.reset();
	}
	
	public boolean turnToAngle(double angle, double speed) {
		// TODO: Implement Eventually
	}
	
	public boolean driveStraight(double distance, double speed) {
		// TODO: Implement Eventually
	}
	
	public double getGyroAngle() {
		return m_navX.getAngle() - m_headingYaw;
	}
	
	public void resetNavX() {
		m_headingYaw = m_navX.getAngle();
	}
}
