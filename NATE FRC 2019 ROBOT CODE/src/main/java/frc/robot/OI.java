package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.commands.DeployRamps;
import frc.robot.commands.LiftRobot;
import frc.robot.commands.PresentPositions;
import frc.robot.commands.SetCameraAngle;
import frc.robot.commands.SetNoArm;
import frc.robot.commands.SetOutreachMode;
import frc.robot.sensors.ADIS16448_IMU;

public class OI {
  public boolean isOutreachMode = false;
  public boolean isNoArm = false;
  public boolean followLine = false;

  public Joystick manipulator = new Joystick(0);
  public Joystick left = new Joystick(1);
  public Joystick right = new Joystick(2);

  // LEFT DRIVE
    public JoystickButton lineFollowButton = new JoystickButton(left, RobotMap.buttonLineFollow);
  
  // RIGHT DRIVE
    public JoystickButton rightControlButton = new JoystickButton(right, RobotMap.buttonRightDriveControl);
  
  // ARM
    public JoystickButton wristExtentionlimitBypass = new JoystickButton(manipulator, RobotMap.wristExtentionlimitBypass);
    public JoystickButton armLimitBypassWristLock = new JoystickButton(manipulator, RobotMap.armlimitBypassWristLock);

  // EXTENTION
    public JoystickButton armExtend = new JoystickButton(manipulator, RobotMap.buttonArmExtendPort);
    public JoystickButton armRetract = new JoystickButton(manipulator, RobotMap.buttonArmRetractPort);

  // VACUUM PUMPS
    public JoystickButton VacuumPumpOn = new JoystickButton(manipulator, RobotMap.buttonVacuumPumpOnPort);
    public JoystickButton VacuumPumpOff = new JoystickButton(manipulator, RobotMap.buttonVacuumPumpOffPort);
      
  // LIFT ROBOT
    public JoystickButton robotLift = new JoystickButton(left, RobotMap.buttonLiftRobotPort);

  // DEPLOY RAMPS
    public JoystickButton deployRamps = new JoystickButton(left, RobotMap.buttonDeployRampPort);

  // CAMERA SERVO
    public JoystickButton CameraServoFront = new JoystickButton(right, RobotMap.buttonCameraFaceFront);
    public JoystickButton CameraServoBack = new JoystickButton(right, RobotMap.buttonCameraFaceBack);

  // OUTREACH MODE
    public JoystickButton outreachMode = new JoystickButton(right, RobotMap.buttonOutreachMode);

  // NO ARM
    public JoystickButton noArm = new JoystickButton(right, RobotMap.buttonNoArm);

  // ENCODER RESET
    // public JoystickButton upL = new JoystickButton(joystickEncoderReset, 14);

  // PRESET POSITIONS
    public JoystickButton pickUpHatch = new JoystickButton (manipulator, RobotMap.PickUpHatchPort);
    public JoystickButton pickUpBall = new JoystickButton(manipulator, RobotMap.pickUpBallPort);
    public JoystickButton pickUpBallOffFloor = new JoystickButton(manipulator, RobotMap.pickUpBallOffFloorPort);
    public JoystickButton placeHatch = new JoystickButton(manipulator, RobotMap.placeHatchPort);
    public JoystickButton placeBallInRocket = new JoystickButton(manipulator, RobotMap.placeBallInRocketPort);
    public JoystickButton placeBallInCargoShip = new JoystickButton(manipulator, RobotMap.placeBallInCargoShipPort);
    public JoystickButton armVerticle = new JoystickButton(manipulator, RobotMap.armVerticlePort);

  public final ADIS16448_IMU gyro = new ADIS16448_IMU();

  public OI() {
    
    Command pickUpHatch = new PresentPositions(1);
    this.pickUpHatch.whenPressed(pickUpHatch);
    Command pickUpBall = new PresentPositions(2);
    this.pickUpBall.whenPressed(pickUpBall);
    Command pickUpBallOffFloor = new PresentPositions(3);
    this.pickUpBallOffFloor.whenPressed(pickUpBallOffFloor);
    Command placeHatch = new PresentPositions(4);
    this.placeHatch.whenPressed(placeHatch);
    Command placeBallInRocket = new PresentPositions(5);
    this.placeBallInRocket.whenPressed(placeBallInRocket);
    Command placeBallInCargoShip = new PresentPositions(6);
    this.placeBallInCargoShip.whenPressed(placeBallInCargoShip);
    Command armVerticle = new PresentPositions(7);
    this.armVerticle.whenPressed(armVerticle); 

    Command robotLift = new LiftRobot();
    this.robotLift.whenPressed(robotLift);

    Command deployRamps = new DeployRamps();
    this.deployRamps.whenPressed(deployRamps);

    Command outreachMode = new SetOutreachMode();
    this.outreachMode.whenPressed(outreachMode);

    Command noArm = new SetNoArm();
    this.noArm.whenPressed(noArm);

    Command CameraServoFront = new SetCameraAngle(RobotMap.cameraFaceFrontAngle);
    this.CameraServoFront.whenPressed(CameraServoFront);

    Command CameraServoBack = new SetCameraAngle(RobotMap.cameraFaceBackAngle);
    this.CameraServoBack.whenPressed(CameraServoBack);

  }
}