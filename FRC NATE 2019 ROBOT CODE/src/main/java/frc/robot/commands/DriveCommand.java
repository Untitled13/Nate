
package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class DriveCommand extends Command {
  public DriveCommand() {
    requires(Robot.driveSubsystem);
  }

  @Override
  protected void initialize() {
  }

  @Override
  protected void execute() {
   
      Robot.driveSubsystem.manualDrive(Robot.oi.joystickLeft.getY(), Robot.oi.joystickRight.getY(), Robot.oi.rightControlButton.get(), Robot.oi.lineFollowButton.get(), Robot.oi.joystickRight.getY(), -Robot.oi.joystickLeft.getY());
      Robot.driveSubsystem.driveRawEncoderOuput();
      Robot.driveSubsystem.driveMotors();

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