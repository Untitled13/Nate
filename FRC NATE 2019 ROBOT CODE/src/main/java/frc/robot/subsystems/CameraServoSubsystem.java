package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.commands.CameraServoCommand;

public class CameraServoSubsystem extends Subsystem {
  
  public boolean tunable = false;

  
  
  public CameraServoSubsystem(boolean tunable){
    this.tunable = tunable;
    
  }

  //camera face front
  public void cameraServoFront(boolean frontButton){
      if (frontButton) {
        Robot.oi.cameraServo.setAngle(RobotMap.cameraFaceFrontAngle);
      }
    }

  //camera face back
  public void cameraServoBack(boolean backButton) {
    if (backButton) {
    Robot.oi.cameraServo.setAngle(RobotMap.cameraFaceBackAngle);
    }
  }


  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new CameraServoCommand());
  }
}