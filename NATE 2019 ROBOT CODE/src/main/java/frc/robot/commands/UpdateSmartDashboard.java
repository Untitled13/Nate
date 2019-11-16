package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class UpdateSmartDashboard extends Command {
  public UpdateSmartDashboard() {
    requires(Robot.Smartdashboard);

  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.Smartdashboard.SmartDashboardInitialize();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Robot.Smartdashboard.updateSmartdashboard();
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