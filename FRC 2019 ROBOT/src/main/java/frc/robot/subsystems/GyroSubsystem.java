package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.commands.GyroCommand;

/**
 * Add your docs here.
 */
public class GyroSubsystem extends Subsystem {
  public double angleX;
  public double angleY;
  public double angleZ;
  public double accelerationX;
  public double accelerationY;
  public double accelerationZ;
  
  //get angle "angle X"
  public void angleX(double angleX){
    this.angleX = angleX;
    // System.out.println("angleX" + angleX);
  }

  //get angle "angle Y"
  public void angleY(double angleY){
    this.angleY = angleY;
    // System.out.println("angleY" + angleY);
  }

  //get angle "angle Z"
  public void angleZ(double angleZ){
    this.angleZ = angleZ;
    // System.out.println("angleZ" + angleZ);
  }
  //get acceleration "accelerationX"
  public void accelerationX(double accelerationX) {
    this.accelerationX = accelerationX;
    // System.out.println("accelerationX" + accelerationX);
  }
  //get acceleration "accelerationY"
  public void accelerationY(double accelerationY) {
    this.accelerationY = accelerationY;
    // System.out.println("accelerationY" + accelerationY);
  }
  //get acceleration "accelerationZ"
  public void accelerationZ(double accelerationZ) {
    this.accelerationZ = accelerationZ;
    // System.out.println("accelerationZ" + accelerationZ);
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new GyroCommand());
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}