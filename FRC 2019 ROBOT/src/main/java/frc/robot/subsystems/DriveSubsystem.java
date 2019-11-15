package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;
import frc.robot.commands.DriveCommand;


public class DriveSubsystem extends Subsystem {

	public boolean tunable = false;

	public WPI_TalonSRX leftMaster, leftSlave, rightMaster, rightSlave;

	public DifferentialDrive drive;

	public double leftRawEncoder;
	public double rightRawEncoder;

	public double leftSpeed;
	public double rightSpeed;

	public double leftFinal;
	public double rightFinal;

	public double leftOutput;
	public double rightOutput;

	public double outreachDriveSpeed;

	public double lineOffSet;

	public boolean lineFollow;

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
		leftMaster.configNeutralDeadband(RobotMap.driveNeutralDeadband, RobotMap.driveTimeoutMs);
		leftMaster.setInverted(true);
		leftSlave.setInverted(true);
		leftMaster.setSensorPhase(false);
		leftMaster.setNeutralMode(NeutralMode.Brake);
		
		// FOLLOW
		leftSlave.follow(leftMaster);
		
		// RIGHT MASTER
		rightMaster.configNeutralDeadband(RobotMap.driveNeutralDeadband, RobotMap.driveTimeoutMs);
		rightMaster.setInverted(false);
		rightSlave.setInverted(false);
		rightMaster.setSensorPhase(false);	
		rightMaster.setNeutralMode(NeutralMode.Brake);

		// FOLLOW
		rightSlave.follow(rightMaster);
	}

	//DRIVE MOTORS
	public void manualDrive(double leftPower, double rightPower, boolean rightControl, boolean leftControl, double leftTurn, double rightTurn) {
		if (!lineFollow) {
			//left drive smooth
			if (leftPower > leftSpeed) {
				leftSpeed = leftSpeed + ((leftPower - leftSpeed) * SmartDashboard.getNumber("driveSmooth", .5));
			} else if(leftPower < leftSpeed) {
				leftSpeed = leftSpeed - ((leftSpeed - leftPower) * SmartDashboard.getNumber("driveSmooth", .5));
			}

			//right drive smooth
			if (rightPower > rightSpeed) {
				rightSpeed = rightSpeed + ((rightPower - rightSpeed) * SmartDashboard.getNumber("driveSmooth", .5));
			} else if(rightPower < rightSpeed) {
				rightSpeed = rightSpeed - ((rightSpeed - rightPower) * SmartDashboard.getNumber("driveSmooth", .5));
			}

			//drive speed
			leftFinal = leftSpeed * RobotMap.driveSpeed * outreachDriveSpeed;
			rightFinal = rightSpeed * RobotMap.driveSpeed * outreachDriveSpeed;


			// right control
			if (rightControl) {
				if (rightTurn > 0) {
					leftOutput = rightFinal;
					rightOutput = (rightFinal + (rightTurn * RobotMap.rightControlLeftEffectiveness));
				} else {
					leftOutput = (rightFinal - (rightTurn * RobotMap.rightControlLeftEffectiveness));
					rightOutput = rightFinal;
				}

				if (leftControl) {
					leftOutput = -1;
					rightOutput = -1;
				}
				
			} else {
				leftOutput = leftFinal;
				rightOutput = rightFinal;
			}
		}
	}

	//GET ENCODOR OUTPUT
	public void driveRawEncoderOuput() {

		leftRawEncoder = leftMaster.getSelectedSensorPosition();
		rightRawEncoder = rightMaster.getSelectedSensorPosition();

	}

	public void lineFollow(double XValue, double joystick, boolean buttonActivate) {

		lineOffSet = (XValue / 15 * .25);
		
		// if (targetArea >= 8 || targetArea == 0) {
		// 	// KEEP LINE OFFSET THE SAME
		// } else if (targetArea < 2) {
		// 	if (XValue >= 0) {
		// 		lineOffSet = lineOffSet - .1;
		// 	} else if (XValue <= 0) {
		// 		lineOffSet = lineOffSet + .1;
		// 	}
		// }

		if (buttonActivate) {
			lineFollow = true;
			leftOutput = joystick - lineOffSet;
			rightOutput = joystick + lineOffSet;
		} else {
			lineFollow = false;
		}
		driveMotors();

	}

	public void driveMotors() {
		drive.tankDrive(leftOutput, rightOutput);
	}

	public void stop() {
		drive.tankDrive(0, 0);		
	}
	
	@Override
	public void initDefaultCommand() {
    	setDefaultCommand(new DriveCommand());
	}
}