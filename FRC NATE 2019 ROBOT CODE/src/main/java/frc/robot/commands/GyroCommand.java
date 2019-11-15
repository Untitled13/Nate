package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class GyroCommand extends Command {
  public GyroCommand() {
    requires(Robot.gyroSubsystem);
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {

    Robot.gyroSubsystem.angleX(Robot.oi.gyro.getAngleX());
    Robot.gyroSubsystem.angleY(Robot.oi.gyro.getAngleY());
    Robot.gyroSubsystem.angleZ(Robot.oi.gyro.getAngleZ());
    Robot.gyroSubsystem.accelerationX(Robot.oi.gyro.getAccelX());
    Robot.gyroSubsystem.accelerationY(Robot.oi.gyro.getAccelY());
    Robot.gyroSubsystem.accelerationZ(Robot.oi.gyro.getAccelZ());
  
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}