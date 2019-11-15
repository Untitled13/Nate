package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class RobotClimbCommand extends Command {
  boolean robotLift = false;
  public RobotClimbCommand(boolean lift) {

    requires(Robot.RobotClimbSubsystem);

      robotLift = lift;
   
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    // if (!Robot.OutreachModeSubsystem.OutreachMode) {
      Robot.RobotClimbSubsystem.liftRobot(robotLift);    
      Robot.RobotClimbSubsystem.rampDeploy(Robot.oi.deployRamps.get());
    // } else {
      // robotLift = false;
    // }
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