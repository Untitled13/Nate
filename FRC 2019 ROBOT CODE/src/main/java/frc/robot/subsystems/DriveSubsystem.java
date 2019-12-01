package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.commands.ManualDrive;


public class DriveSubsystem extends Subsystem {
	public boolean tunable = false;

	private WPI_TalonSRX leftMaster, leftSlave, rightMaster, rightSlave;

	private DifferentialDrive drive;

	public DriveSubsystem(boolean tunable) {
		this.tunable = tunable;
		//setup motors
		leftMaster = new WPI_TalonSRX(RobotMap.leftMasterPort);
		leftSlave = new WPI_TalonSRX(RobotMap.leftSlavePort);
		rightMaster = new WPI_TalonSRX(RobotMap.rightMasterPort);
		rightSlave = new WPI_TalonSRX(RobotMap.rightSlavePort);

		drive = new DifferentialDrive(leftMaster, rightMaster);

		// RESET TALONS
		leftMaster.configFactoryDefault();
		leftSlave.configFactoryDefault();
		rightMaster.configFactoryDefault();
		rightSlave.configFactoryDefault();
		
		// LEFT MASTER
		leftMaster.setInverted(true);
		leftSlave.setInverted(true);
		leftMaster.setSensorPhase(false);
		leftMaster.setNeutralMode(NeutralMode.Brake);
		
		// FOLLOW
		leftSlave.follow(leftMaster);
		
		// RIGHT MASTER
		rightMaster.setInverted(false);
		rightSlave.setInverted(false);
		rightMaster.setSensorPhase(false);	
		rightMaster.setNeutralMode(NeutralMode.Brake);

		// FOLLOW
		rightSlave.follow(rightMaster);
	}

	public double getLeftOutput() {
		return leftMaster.get();
	}

	public double getRightOutput() {
		return rightMaster.get();
	}

	//DRIVE MOTORS
	public void manualDrive(double leftPower, double rightPower, boolean rightControl, boolean leftControl, double leftTurn, double rightTurn) {
		// left drive smooth
			double leftSpeed = getLeftOutput() + ((leftPower - getLeftOutput()) * Robot.ShuffleBoard.driveSmooth.getDouble(RobotMap.defaultDriveSmooth));
			

		//right drive smooth
			double rightSpeed = getRightOutput() + ((rightPower - getRightOutput()) * Robot.ShuffleBoard.driveSmooth.getDouble(RobotMap.defaultDriveSmooth));

		//drive speed
			double leftFinal = leftSpeed * Robot.ShuffleBoard.driveSpeed.getDouble(RobotMap.defaultDriveSpeed);
			double rightFinal = rightSpeed * Robot.ShuffleBoard.driveSpeed.getDouble(RobotMap.defaultDriveSpeed);

			if (Robot.oi.isOutreachMode) {
				leftFinal = leftFinal * Robot.ShuffleBoard.outreachModeDriveSpeed.getDouble(RobotMap.defaultOutreachDriveSpeed);
				rightFinal = rightFinal * Robot.ShuffleBoard.outreachModeDriveSpeed.getDouble(RobotMap.defaultOutreachDriveSpeed);;
			}

		// right control
			double leftOutput;
			double rightOutput;
			if (rightControl) {
				// MAKE WEELS ALLWAYS SLOW, AND NOT ACCELERATE
				if (rightTurn > 0) {
					leftOutput = rightFinal; 
					rightOutput = (rightFinal + (rightTurn * Robot.ShuffleBoard.driveRightControlLeftJoyEffectiveness.getDouble(RobotMap.defaultRightControlLeftEffectiveness)));
				} else {
					leftOutput = (rightFinal - (rightTurn * Robot.ShuffleBoard.driveRightControlLeftJoyEffectiveness.getDouble(RobotMap.defaultRightControlLeftEffectiveness)));
					rightOutput = rightFinal;
				}
				
			} else {
				leftOutput = leftFinal;
				rightOutput = rightFinal;
			}
			driveMotors(leftOutput, rightOutput);
	}

	//GET ENCODOR OUTPUT
	public double getLeftDriveEncoder() {
		return leftMaster.getSelectedSensorPosition();
	}

	public double getRightDriveEncoder() {
		return rightMaster.getSelectedSensorPosition();
	}

	public void lineFollow(double LimelightXValue, double joystickY) {

		double lineOffSet = (LimelightXValue / 15 * .25);
		
		// if (targetArea >= 8 || targetArea == 0) {
		// 	// KEEP LINE OFFSET THE SAME
		// } else if (targetArea < 2) {
		// 	if (XValue >= 0) {
		// 		lineOffSet = lineOffSet - .1;
		// 	} else if (XValue <= 0) {
		// 		lineOffSet = lineOffSet + .1;
		// 	}
		// }

		double leftOutput = joystickY - lineOffSet;
		double rightOutput = joystickY + lineOffSet;
		driveMotors(leftOutput, rightOutput);

	}

	public void driveMotors(double left, double right) {
		drive.tankDrive(left, right);
	}

	public void stop() {
		drive.tankDrive(0, 0);		
	}
	
	@Override
	public void initDefaultCommand() {
    	setDefaultCommand(new ManualDrive());
	}
}