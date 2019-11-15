package frc.robot.subsystems;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import frc.robot.RobotMap;


public class Camera {

  public Camera() {
    UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
    camera.setResolution(RobotMap.cameraXResolution, RobotMap.cameraYResolution);
  }
  
}