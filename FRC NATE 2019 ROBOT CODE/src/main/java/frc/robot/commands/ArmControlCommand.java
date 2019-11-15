package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;


public class ArmControlCommand extends Command {

  public ArmControlCommand() {
    requires(Robot.armSubsystem);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {

    Robot.armSubsystem.armEncoderReset();

  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
 
  Robot.armSubsystem.armControl(Robot.oi.joystickArm.getY(), Robot.oi.armLimitBypassWristLock.get());  Robot.armSubsystem.armAngle();
  Robot.armSubsystem.armEncoderUpperReset(Robot.oi.joystickArm.getY(), Robot.oi.armUpperLimitSwitch.get(), Robot.oi.joystickArm.getPOV());
  Robot.armSubsystem.armEncoderLowerReset(Robot.oi.joystickArm.getY(), Robot.oi.armLowerLimitSwitch.get(), Robot.oi.joystickArm.getPOV());
  
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.armSubsystem.stop();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}