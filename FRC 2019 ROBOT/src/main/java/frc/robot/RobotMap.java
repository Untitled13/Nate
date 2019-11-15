package frc.robot;

public class RobotMap {
  
  //DRIVE
    public static double rightControlLeftEffectiveness = .3; //between 0 & 1, higher = more turning
    public static final int leftMasterPort = 3; //CAN port number
    public static final int leftSlavePort = 0; //CAN port number
    public static final int rightMasterPort = 2; //CAN port number
    public static final int rightSlavePort = 1; //CAN port number
    public static int joystickLeftPort = 1; //USB port number
      public static int buttonLineFollow = 1; //BUTTON number
    public static int joystickRightPort = 2; //USB port number
      public static int buttonRightDriveControl = 1; //BUTTON number
    public static final int driveTimeoutMs = 30; //IDK
    public static final double driveNeutralDeadband = 0.1; //IDK
    public static final double driveSmooth = .5; //between 0 & 1, higher = less smoothing
    public static final double driveSpeed = 1; //between 0 & 1; higher=faster

  //ARM:
    public static int armSpeed = 2; //between 1 & ~, higher = slower 
    public static double armSmooth = .4;  //between 0 & 1, lower = more smoothing
    public static final int armUpperLimitSwitchPort = 3; //DIO port number
    public static final int armLowerLimitSwitchPort = 4; //DIO port number
    public static final int armPort = 7; //CAN port number
    public static int joystickArmPort = 0; //USB port number
    public static int armlimitBypassWristLock = 6; //BUTTON number
    public static int armBreakSolenoid = 4; //PCM port number

  //EXTENTENTION
    public static double ExtentionSpeed = .6; //between 0 & 1, higher = faster
    public static double extentionSmooth = .25; //between 0 & 1, lower = more smoothing
    public static final int armExtentionUpperLimitSwitch = 7; //DIO port number
    public static final int armExtentionLowerLimitSwitch =0; //DIO port number
    public static final int extentionEncoderSourceA = 8; //DIO port signal A number
    public static final int extentionEncoderSourceB = 9; //DIO port signal B number
    public static final int armExtentionPort = 8; //CAN port number
    public static int joystickExtentionPort = 0; //USB port number
      public static int buttonArmExtendPort = 5; //BUTTON number
      public static int buttonArmRetractPort = 3; //BUTTON number

    public static int wristExtentionlimitBypass = 4; //BUTTON number "joystickArmPort"
  //WRIST
    public static int wristSpeed = 1; //between 1 & ~, higher = slower 
    public static double wristSmooth = .2;  //between 0 & 1, lower = more smoothing
    public static final int armWristUpperLimitSwitch = 1; //DIO port number
    public static final int armWristLowerLimitSwitch = 2; //DIO port number
    public static final int wristEncoderSourceA = 5; //DIO port signal A number
    public static final int wristEncoderSourceB = 6; //DIO port signal B number
    public static final int wristPort = 6; //CAN port number
    public static int joystickWristPort = 0; //USB port number

  //VACUUM
    public static double vacuumPumpSpeed = 1; //between 0 & 1, higher = more  
    public static int joystickVacuumPumpPort = 0; //USB port number
      public static int buttonVacuumPumpOnPort = 1; //BUTTON number
      public static int buttonVacuumPumpOffPort = 2; //BUTTON number
    public static final int vacuumPumpMasterPort = 0; //CAN port number
    public static final int vacuumPumpSlavePort = 1;  //CAN port number
    public static int vacuumSolenoid = 6; //PCM port number

  //LIFT
    public static int liftRobotPort = 1; //USB port number
      public static int buttonLiftRobotPort = 4; //BUTTON number
    public static int liftExtendSolenoid = 0; //PCM port number
    public static int liftRetractSolenoid = 1; //PCM port number

  //RAMPS
    public static int deployRampPort = 1; //USB port number
     public static int buttonDeployRampPort = 2; //BUTTONnumber
    public static int rampExtendSolenoid = 2; //PCM port number
    public static int rampRetractSolenoid = 3; //PCM port number

  //CAMERA
    public static int cameraFaceFrontAngle = 165; //between 0 & 180
    public static int cameraFaceBackAngle = 5; //between 0 & 180
    public static int cameraXResolution = 2000; //X resolution
    public static int cameraYResolution = 480; //Y resolution
    public static int cameraServoPort = 9; //PWM port number
    public static int joystickCameraServo = 2; //USB port number
      public static int buttonCameraFaceFront = 3; //BUTTON number
      public static int buttonCameraFaceBack = 2; //BUTTON number

  // OTHER
    public static int PCM = 37; //PCM ID "37"

  //Outreach Mode
    public static double OutreachArmSpeed = .8; //between 0 & 1, higher = more based of value above
    public static double OutreachExtentionSpeed = .8; //between 0 & 1, higher = more based of value above
    public static double OutreachwristSpeed = .8; //between 0 & 1, higher = more based of value above
    public static double OutreachDriveSpeed = .8; //between 0 & 1, higher = more based of value above
    public static int joystickOutreachMode = 2; //USB port number
      public static int buttonOutreachMode = 9; //BUTTON number

  //Outreach Mode
    public static int joystickNoArm = 2; //USB port number
    public static int buttonNoArm = 8; //BUTTON number
  
  //Encoder Reset
    public static int joystickEncoderReset = 0;
      public static int upL = 1;
      public static int upC =2;
      public static int upR = 3;
      public static int downL = 4;
      public static int downC = 5;
      public static int downR = 6;
  // PRESET POSITIONS 
      public static int presetPositionsTimeout= 5; //Whole Number (SECONDS)
    // PORTS
      public static int joystickPresetPositionsPort = 0; //USB port number
      public static final int PickUpHatchPort = 7; //BUTTON number
      public static final int pickUpBallPort = 9; //BUTTON number
      public static final int pickUpBallOffFloorPort = 12; //BUTTON number
      public static final int placeHatchPort = 8; //BUTTON number
      public static final int placeBallInRocketPort = 10; //BUTTON number
      public static final int placeBallInCargoShipPort = 0; //BUTTON number
      public static final int armVerticlePort = 11; //BUTTON number

    // ARM PRESET POSITIONS
      public static double armPickUpHatchPosition = 100; //between 0 & 100, higher = higher up
      public static double armPickUpBallPosition = 100; //between 0 & 100, higher = higher up
      public static double armPickUpBallOffFloorPosition = 18; //between 0 & 100, higher = higher up
      public static double armPlaceHatchPosition = 30; //between 0 & 100, higher = higher up
      public static double armPlaceBallInRocketPosition = 20; //between 0 & 100, higher = higher up
      public static double armPlaceBallInCargoShipPosition = 35; //between 0 & 100, higher = higher up
      public static double armVerticlePosition = 65; //between 0 & 100, higher = higher up

    // EXTENTION PRESET POSITIONS
      public static double extentionPickUpHatchPosition = 90; //between 0 & 100, higher = farther out
      public static double extentionPickUpBallPosition = 90; //between 0 & 100, higher = farther out
      public static double extentionPickUpBallOffFloorPosition = 50; //between 0 & 100, higher = farther out
      public static double extentionPlaceHatchPosition = 20; //between 0 & 100, higher = farther out
      public static double extentionPlaceBallInRocketPosition = 70; //between 0 & 100, higher = farther out
      public static double extentionPlaceBallInCargoShipPosition = 30; //between 0 & 100, higher = farther out
      public static double extentionVerticlePosition = 50; //between 0 & 100, higher = farther out

    // WRIST PRESET POSITIONS
      public static double wristPickUpHatchPosition = 100; //between 0 & 100, higher = higher up
      public static double wristPickUpBallPosition = 100; //between 0 & 100, higher = higher up
      public static double wristPickUpBallOffFloorPosition = 18; //between 0 & 100, higher = higher up
      public static double wristPlaceHatchPosition = 30; //between 0 & 100, higher = higher up
      public static double wristPlaceBallInRocketPosition = 20; //between 0 & 100, higher = higher up
      public static double wristPlaceBallInCargoShipPosition = 35; //between 0 & 100, higher = higher up
      public static double wristVerticlePosition = 65; //between 0 & 100, higher = higher up

    }