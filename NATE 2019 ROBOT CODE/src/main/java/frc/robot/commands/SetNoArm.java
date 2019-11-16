package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class SetNoArm extends Command {
  boolean mode = false;
  public SetNoArm(boolean mode) {
    this.mode = mode;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {

  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Robot.armSubsystem.setNoArm(mode);
    Robot.extentionSubsystem.setNoExtention(mode);
    Robot.WristSubsystem.setNoWrist(mode);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return true;
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
