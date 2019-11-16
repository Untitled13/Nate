package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.RobotMap;

public class RobotClimbSubsystem extends Subsystem {

  private Solenoid robotLiftPistonExtend;
  private Solenoid robotLiftPistonRetract;

  private Solenoid rampPistonExtend;
  private Solenoid rampPistonRetract;

  //lift robot
  public void liftRobot(Boolean lift) {
    robotLiftPistonExtend.set(!lift);
    robotLiftPistonRetract.set(lift);
  
    // SETUP SOLENOIDS
    robotLiftPistonExtend = new Solenoid(RobotMap.PCM, RobotMap.liftExtendSolenoid);
    robotLiftPistonRetract = new Solenoid(RobotMap.PCM, RobotMap.liftRetractSolenoid);

    rampPistonExtend = new Solenoid(RobotMap.PCM, RobotMap.rampExtendSolenoid);
    rampPistonRetract = new Solenoid(RobotMap.PCM, RobotMap.rampRetractSolenoid);
  }  
  
  //deploy ramps
  public void rampDeploy() {
    rampPistonExtend.set(false);
    rampPistonRetract.set(true); 
  
    Timer.delay(.5);

    rampPistonExtend.set(true);
    rampPistonRetract.set(false);
  }

  @Override
  public void initDefaultCommand() {

  }
}