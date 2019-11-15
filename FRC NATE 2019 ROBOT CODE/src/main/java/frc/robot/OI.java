package frc.robot;

import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.commands.NoArmCommand;
import frc.robot.commands.OutreachModeCommand;
import frc.robot.commands.PresentPositionsCommand;
import frc.robot.commands.RobotClimbCommand;
import frc.robot.sensors.ADIS16448_IMU;

public class OI {
  public Joystick joystickLeft = new Joystick(RobotMap.joystickLeftPort);
    public JoystickButton lineFollowButton = new JoystickButton(joystickLeft, RobotMap.buttonLineFollow);
  
  public Joystick joystickRight = new Joystick(RobotMap.joystickRightPort);
    public JoystickButton rightControlButton = new JoystickButton(joystickRight, RobotMap.buttonRightDriveControl);
  
  public Joystick joystickArm = new Joystick(RobotMap.joystickArmPort);
    public JoystickButton wristExtentionlimitBypass = new JoystickButton(joystickArm, RobotMap.wristExtentionlimitBypass);
    public JoystickButton armLimitBypassWristLock = new JoystickButton(joystickArm, RobotMap.armlimitBypassWristLock);
      public Solenoid armBreakSolenoid = new Solenoid(RobotMap.PCM, RobotMap.armBreakSolenoid);

  public Joystick joystickWrist = new Joystick(RobotMap.joystickWristPort);
    public final Encoder wristEncoder = new Encoder(RobotMap.wristEncoderSourceA, RobotMap.wristEncoderSourceB, false, EncodingType.k4X);

  public Joystick joystickExtention = new Joystick(RobotMap.joystickExtentionPort);
    public JoystickButton armExtend = new JoystickButton(joystickWrist, RobotMap.buttonArmExtendPort);
    public JoystickButton armRetract = new JoystickButton(joystickWrist, RobotMap.buttonArmRetractPort);
      public final Encoder extentionEncoder = new Encoder(RobotMap.extentionEncoderSourceA, RobotMap.extentionEncoderSourceB, false, EncodingType.k4X);

  public Joystick joystickVacuumPump = new Joystick(RobotMap.joystickVacuumPumpPort);
    public JoystickButton VacuumPumpOn = new JoystickButton(joystickVacuumPump, RobotMap.buttonVacuumPumpOnPort);
    public JoystickButton VacuumPumpOff = new JoystickButton(joystickVacuumPump, RobotMap.buttonVacuumPumpOffPort);
      public Solenoid vacuumSolenoid = new Solenoid(RobotMap.PCM, RobotMap.vacuumSolenoid);
      
  public Joystick joystickRobotLift = new Joystick(RobotMap.liftRobotPort);
    public JoystickButton robotLift = new JoystickButton(joystickRobotLift, RobotMap.buttonLiftRobotPort);
      public Solenoid robotLiftPistonExtend = new Solenoid(RobotMap.PCM, RobotMap.liftExtendSolenoid);
      public Solenoid robotLiftPistonRetract = new Solenoid(RobotMap.PCM, RobotMap.liftRetractSolenoid);

  public Joystick joystickDeployRamps = new Joystick(RobotMap.deployRampPort);
    public JoystickButton deployRamps = new JoystickButton(joystickDeployRamps, RobotMap.buttonDeployRampPort);
      public Solenoid rampPistonExtend = new Solenoid(RobotMap.PCM, RobotMap.rampExtendSolenoid);
      public Solenoid rampPistonRetract = new Solenoid(RobotMap.PCM, RobotMap.rampRetractSolenoid);

  public Joystick joystickCameraServo = new Joystick(RobotMap.joystickCameraServo);
    public JoystickButton CameraServoFront = new JoystickButton(joystickCameraServo, RobotMap.buttonCameraFaceFront);
    public JoystickButton CameraServoBack = new JoystickButton(joystickCameraServo, RobotMap.buttonCameraFaceBack);
      public Servo cameraServo = new Servo(RobotMap.cameraServoPort);

  public Joystick joystickOutreachMode = new Joystick(RobotMap.joystickOutreachMode);
    public JoystickButton outreachMode = new JoystickButton(joystickOutreachMode, RobotMap.buttonOutreachMode);

  public Joystick joystickNoArm = new Joystick(RobotMap.joystickNoArm);
    public JoystickButton noArm = new JoystickButton(joystickNoArm, RobotMap.buttonNoArm);

  public Joystick joystickEncoderReset = new Joystick(RobotMap.joystickEncoderReset);
    public JoystickButton upL = new JoystickButton(joystickEncoderReset, 14);

  public Joystick joystickPresetPositions = new Joystick(RobotMap.joystickPresetPositionsPort);
    public JoystickButton pickUpHatch = new JoystickButton (joystickPresetPositions, RobotMap.PickUpHatchPort);
    public JoystickButton pickUpBall = new JoystickButton(joystickPresetPositions, RobotMap.pickUpBallPort);
    public JoystickButton pickUpBallOffFloor = new JoystickButton(joystickPresetPositions, RobotMap.pickUpBallOffFloorPort);
    public JoystickButton placeHatch = new JoystickButton(joystickPresetPositions, RobotMap.placeHatchPort);
    public JoystickButton placeBallInRocket = new JoystickButton(joystickPresetPositions, RobotMap.placeBallInRocketPort);
    public JoystickButton placeBallInCargoShip = new JoystickButton(joystickPresetPositions, RobotMap.placeBallInCargoShipPort);
    public JoystickButton armVerticle = new JoystickButton(joystickPresetPositions, RobotMap.armVerticlePort);

  public final DigitalInput armLowerLimitSwitch = new DigitalInput(RobotMap.armLowerLimitSwitchPort);
  public final DigitalInput armUpperLimitSwitch = new DigitalInput(RobotMap.armUpperLimitSwitchPort);
  public final DigitalInput wristLowerLimitSwitch = new DigitalInput(RobotMap.armWristLowerLimitSwitch);
  public final DigitalInput wristUpperLimitSwitch = new DigitalInput(RobotMap.armWristUpperLimitSwitch);
  public final DigitalInput extentionLowerLimitSwitch = new DigitalInput(RobotMap.armExtentionLowerLimitSwitch);
  public final DigitalInput extentionUpperLimitSwitch = new DigitalInput(RobotMap.armExtentionUpperLimitSwitch);

  public final ADIS16448_IMU gyro = new ADIS16448_IMU();

  public OI() {
    
    Command pickUpHatch = new PresentPositionsCommand(1);
    this.pickUpHatch.whenPressed(pickUpHatch);
    Command pickUpBall = new PresentPositionsCommand(2);
    this.pickUpBall.whenPressed(pickUpBall);
    Command pickUpBallOffFloor = new PresentPositionsCommand(3);
    this.pickUpBallOffFloor.whenPressed(pickUpBallOffFloor);
    Command placeHatch = new PresentPositionsCommand(4);
    this.placeHatch.whenPressed(placeHatch);
    Command placeBallInRocket = new PresentPositionsCommand(5);
    this.placeBallInRocket.whenPressed(placeBallInRocket);
    Command placeBallInCargoShip = new PresentPositionsCommand(6);
    this.placeBallInCargoShip.whenPressed(placeBallInCargoShip);
    Command armVerticle = new PresentPositionsCommand(7);
    this.armVerticle.whenPressed(armVerticle); 

    Command robotLift = new RobotClimbCommand(true);
    this.robotLift.toggleWhenPressed(robotLift);

    Command outreachMode = new OutreachModeCommand(true);
    this.outreachMode.toggleWhenPressed(outreachMode);

    Command noArm = new NoArmCommand(true);
    this.noArm.toggleWhenPressed(noArm);

}
 }