package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;

public class SetOutreachMode extends Command {
  boolean mode;
  public SetOutreachMode() {
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    mode = Robot.oi.isOutreachMode;
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Robot.oi.isOutreachMode = !Robot.oi.isOutreachMode;
    SmartDashboard.putBoolean("OutreachMode", !mode);
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
