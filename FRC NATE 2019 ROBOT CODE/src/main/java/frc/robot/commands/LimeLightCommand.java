package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class LimeLightCommand extends Command {
  public LimeLightCommand() {
      requires(Robot.LimeLightSubsystem);
   }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
      Robot.LimeLightSubsystem.LimelightSetup();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Robot.LimeLightSubsystem.Limelight();
    // Robot.driveSubsystem.lineFollow(Robot.LimeLightSubsystem.limelightX , Robot.oi.joystickLeft.getY(), Robot.oi.lineFollowButton.get());
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
