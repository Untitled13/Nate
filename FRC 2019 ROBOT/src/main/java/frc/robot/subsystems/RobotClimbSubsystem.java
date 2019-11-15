package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;
import frc.robot.commands.RobotClimbCommand;

public class RobotClimbSubsystem extends Subsystem {
  //lift robot
  public void liftRobot(Boolean lift) {
    Robot.oi.robotLiftPistonExtend.set(!lift);
    Robot.oi.robotLiftPistonRetract.set(lift);
  
  }  
  
  //deploy ramps
  public void rampDeploy(boolean deployButton) {
    if (deployButton) {
      Robot.oi.rampPistonExtend.set(false);
      Robot.oi.rampPistonRetract.set(true); 
    
      try {
        Thread.sleep(500);
    } catch (Exception e) {}

      Robot.oi.rampPistonExtend.set(true);
      Robot.oi.rampPistonRetract.set(false);
    }
  }

 
  public void stop() {
    
  }


  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new RobotClimbCommand(false));
    }
}