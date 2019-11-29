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

  // private boolean robotLifted = false;

  public RobotClimbSubsystem() {

    // SETUP SOLENOIDS
    robotLiftPistonExtend = new Solenoid(RobotMap.PCM, RobotMap.liftExtendSolenoid);
    robotLiftPistonRetract = new Solenoid(RobotMap.PCM, RobotMap.liftRetractSolenoid);
    robotLiftPistonExtend.set(false);
    robotLiftPistonRetract.set(true);

    rampPistonExtend = new Solenoid(RobotMap.PCM, RobotMap.rampExtendSolenoid);
    rampPistonRetract = new Solenoid(RobotMap.PCM, RobotMap.rampRetractSolenoid);
    rampPistonExtend.set(false);
    rampPistonRetract.set(true);
  }

  //lift robot
  public void liftRobot() {
    boolean robotLifted = robotLiftPistonExtend.get();
    robotLiftPistonExtend.set(robotLifted);
    robotLiftPistonRetract.set(!robotLifted);
  }  
  
  //deploy ramps
  public void rampDeploy() {
    rampPistonExtend.set(true);
    rampPistonRetract.set(false); 
  
    Timer.delay(.5);

    rampPistonExtend.set(false);
    rampPistonRetract.set(true);
  }

  @Override
  public void initDefaultCommand() {

  }
}