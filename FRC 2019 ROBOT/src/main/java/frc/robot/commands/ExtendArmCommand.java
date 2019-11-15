package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class ExtendArmCommand extends Command {
  public ExtendArmCommand() {
    requires(Robot.extentionSubsystem);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {

    Robot.extentionSubsystem.extentionEncoderReset();

  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {

    Robot.extentionSubsystem.extendControl(Robot.oi.armExtend.get(), Robot.oi.armRetract.get());
    Robot.extentionSubsystem.extentionPosition(Robot.oi.extentionEncoder.get());
    Robot.extentionSubsystem.driveMoter(Robot.oi.wristExtentionlimitBypass.get());
    Robot.extentionSubsystem.armEncoderLowerReset(Robot.oi.extentionLowerLimitSwitch.get());

  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.extentionSubsystem.stop();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}