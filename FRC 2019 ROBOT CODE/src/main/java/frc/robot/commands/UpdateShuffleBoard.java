package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;

public class UpdateShuffleBoard extends Command {
  int count = 0;
  public UpdateShuffleBoard() {
    SmartDashboard.putNumber("a", count);
    count++;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    // Robot.ShuffleBoard.update = false;
    SmartDashboard.putNumber("b", count);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    // Robot.ShuffleBoard.pdpVoltage.setDouble(Robot.PDP.PDPVoltage());
    // Robot.ShuffleBoard.pdpTotalAmp.setDouble(Robot.PDP.PDPTotalAmp());
    // Robot.ShuffleBoard.pdpArmAmp.setDouble(Robot.PDP.PDPArmAmp());
    // Robot.ShuffleBoard.pdpExtentionAmp.setDouble(Robot.PDP.PDPExtentionAmp());
    // Robot.ShuffleBoard.pdpWristAmp.setDouble(Robot.PDP.PDPWristAmp());
    // Robot.ShuffleBoard.pdpLeftDriveAmp.setDouble(Robot.PDP.PDPLeftDriveAmp());
    // Robot.ShuffleBoard.pdpRightDriveAmp.setDouble(Robot.PDP.PDPRightDriveAmp());
    // Robot.ShuffleBoard.pdpTemperature.setDouble(Robot.PDP.PDPTemperature());
    SmartDashboard.putNumber("key", count);
    count++;
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    // return Robot.ShuffleBoard.update;
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
