
package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class ManualDrive extends Command {
  public ManualDrive() {
    requires(Robot.driveSubsystem);
  }

  @Override
  protected void initialize() {
  }

  @Override
  protected void execute() {
    if (Robot.oi.left.getTrigger()) {
      Robot.driveSubsystem.lineFollow(Robot.Limelight.limelightX(), Robot.oi.left.getY());
    } else {
      Robot.driveSubsystem.manualDrive(Robot.oi.left.getY(), Robot.oi.right.getY(), Robot.oi.rightControlButton.get(), Robot.oi.lineFollowButton.get(), Robot.oi.right.getY(), -Robot.oi.left.getY());
    }
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

  @Override
  protected void end() {
  }

  @Override
  protected void interrupted() {
  }
}