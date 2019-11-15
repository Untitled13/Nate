package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class VacuumPumpCommand extends Command {
  public VacuumPumpCommand() {
    requires(Robot.VacuumPumpSubsystem);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {

    Robot.VacuumPumpSubsystem.vacuumPump(Robot.oi.VacuumPumpOn.get(), Robot.oi.VacuumPumpOff.get());

  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {

    Robot.VacuumPumpSubsystem.stop();
    Robot.oi.vacuumSolenoid.set(false);
    
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}