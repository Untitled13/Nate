package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class CameraServoCommand extends Command {
  public CameraServoCommand() {
    requires(Robot.cameraServoSubsystem);
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.cameraServoSubsystem.cameraServoFront(Robot.oi.CameraServoFront.get());
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    
    Robot.cameraServoSubsystem.cameraServoFront(Robot.oi.CameraServoFront.get());
    Robot.cameraServoSubsystem.cameraServoBack(Robot.oi.CameraServoBack.get());

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