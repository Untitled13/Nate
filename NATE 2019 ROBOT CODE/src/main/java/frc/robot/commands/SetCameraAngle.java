package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class SetCameraAngle extends Command {
  int angle;
  public SetCameraAngle(int angle) {
    requires(Robot.cameraServoSubsystem);
    this.angle = angle;
    
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.cameraServoSubsystem.setCameraAngle(RobotMap.cameraFaceFrontAngle);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Robot.cameraServoSubsystem.setCameraAngle(angle);
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