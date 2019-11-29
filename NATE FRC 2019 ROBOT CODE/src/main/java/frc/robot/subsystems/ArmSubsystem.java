package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.commands.ManualArmControl;

public class ArmSubsystem extends Subsystem {
  public boolean tunable = false;

  private WPI_TalonSRX armMaster;

  private double armEncoderUpperLimit = -5; //whole number 
  private double armEncoderLowerLimit = 1720; //whole number

  private Solenoid armBrakeSolenoid;

  private DigitalInput armLowerLimitSwitch;
  private DigitalInput armUpperLimitSwitch;

  private NetworkTableEntry armP;
  private NetworkTableEntry armI;
  private NetworkTableEntry armD;

	public ArmSubsystem(boolean tunable) {
		this.tunable = tunable;

    //setup armMotor
    armMaster = new WPI_TalonSRX(RobotMap.armPort);

    // SETUP BRAKE SOLENOID
    armBrakeSolenoid = new Solenoid(RobotMap.PCM, RobotMap.armBreakSolenoid);

    // SETUP LIMITSWITCHES
    armLowerLimitSwitch = new DigitalInput(RobotMap.armLowerLimitSwitchPort);
    armUpperLimitSwitch = new DigitalInput(RobotMap.armUpperLimitSwitchPort);

    // SETUP PID CONTROL IN SHUFFLEBOARD
    armP = Robot.ShuffleBoard.Tune.add("armPID-P", 0).getEntry();
    armI = Robot.ShuffleBoard.Tune.add("armPID-I", 0).getEntry();
    armD = Robot.ShuffleBoard.Tune.add("armPID-D", 0).getEntry();
  }

  public double getArmRawEncoder() {
    return armMaster.getSelectedSensorPosition();
  }

  public double getP() {
    return armP.getDouble(0);
  }

  public double getI() {
    return armI.getDouble(0);
  }

  public double getD() {
    return armD.getDouble(0);
  }

  public double getArmOutput() {
    return armMaster.get();
  }

  //Manualy move arm
  public void armControl(double armSpeed, boolean armLimitBypass) {
    double armMove = ((-armSpeed) / RobotMap.armSpeed);

    if (Robot.oi.isOutreachMode) {
      armMove = armMove * RobotMap.OutreachArmSpeed;
    }

    //Arm smoothing
    double armFinal = getArmOutput() + ((armMove - getArmOutput()) * RobotMap.armSmooth);

    //Start driveMorer, and armBrake
    driveMoter(armFinal, armLimitBypass);
  }
  
  //sets arm output based on "armOutput" from sends
  public void driveMoter(double armOutput, boolean armLimitBypass) {
    
    //upper soft limit
    if (getArmPercentage() >= .8 && armOutput >= 0 && !armLimitBypass) {
        armOutput = (armOutput / (getArmPercentage() * 4)); 
    }
    
    //upper hard limit
    if (armOutput > 0 && getArmPercentage() >= 1 && !armLimitBypass) {
      armOutput = 0;
    }

    //lower soft limit
    if (getArmPercentage() <= .2 && armOutput <= 0 && !armLimitBypass) {
      armOutput = (armOutput / (Math.abs(1 - getArmPercentage()) * 7)); 
    }

    //lower hard limit
    if (armOutput < 0 && getArmPercentage() <= 0 && !armLimitBypass) {
      armOutput = 0;
    }

    //lower limit, to stop wires from being pinched, and the suction cup does not get jamed
    if (Robot.extentionSubsystem.getExtentionPosition() > .1 && Robot.extentionSubsystem.getExtentionPosition() < .9 && getArmPercentage() < .2 && armOutput <= .1) {
      armOutput = 0;
      setBrake(true);
    }

    armBrake(armOutput);

    //send "armOutput" to motors
    if (!Robot.oi.isNoArm) {
      armMaster.set(armOutput);
    } else {
      armMaster.set(0);
    }
  }

  //decides when the arm break should be applyed
  public void armBrake(double armOutput) {
    if (Math.abs (armOutput) < .05 ) {
      setBrake(true);
    } else {
      setBrake(false);
    } 
  }    

  //sends "setbrake" to arm
  public void setBrake(boolean state) {
    armBrakeSolenoid.set(state);
  }

  //resets arm encoder upper value when limit switch is pressed
  public void armEncoderUpperReset(double joystick, double POV){
    boolean upperLimit = armUpperLimitSwitch.get();
    boolean povUp;
    if (POV == 315 || POV == 0 || POV == 45) {
      povUp = true;
    } else {
      povUp = false;
    }

    if (joystick >.1 && !upperLimit && povUp) {
      armEncoderUpperLimit = getArmRawEncoder();
      armEncoderLowerLimit = getArmRawEncoder() + 1725;
    }

  }

  //resets arm encoder lower value when limit switch is pressed
  public void armEncoderLowerReset(double joystick, double POV){
    boolean lowerLimit = armLowerLimitSwitch.get();
    boolean povDown;
    if (POV == 135 || POV == 180 || POV == 225) {
      povDown = true;
    } else {
      povDown = false;
    }

    if (joystick < -.1 && !lowerLimit && povDown) {
      armEncoderLowerLimit = getArmRawEncoder();
    }
  
  }

  //gets the arm percentage (0-1) value from encoder
  public double getArmPercentage() {
    double armPercentage = ((armEncoderLowerLimit - getArmRawEncoder()) * (1/(armEncoderLowerLimit - armEncoderUpperLimit)));
    return armPercentage;
  }

  //sets the arm's angle for preset positions
  public boolean armSetAngle(double armPosition) {
    if (Math.abs(armPosition - getArmPercentage()) > .02) {
      
      //safty, incase the arm is set to o past 100%
      if (armPosition > 100) {
        armPosition = 100; 
      } else if(armPosition < 0){
      
      }

      //finds the diffrence between where the arm is set to go, and where it is
      double armDifference = ((armPosition - getArmPercentage() + .02) * 3 );
      
      //stops the arm from hitting the motor when it moves back
      if (getArmPercentage() >= .6 && armPosition >= 90) {

        if (armDifference >= 0) {
          armDifference = (armDifference / (getArmPercentage() * 2) + .05);
        }
      }

      //does not allow the arm to move faseer than .6
      if (armDifference >= .6) {
        armDifference = .6;
      } else if (armDifference <=-.6) {
        armDifference = -.6;
      }

      //does not allow the arm to move slower than .1, so it can make it to its end point
      if (armDifference <= .2 && armDifference > 0) {
        armDifference = .2;
      } else if (armDifference >=-.2 && armDifference < 0) {
        armDifference = -.2;
      }

      //sends the arm's power to the moter
      driveMoter(armDifference, false);
      return true;

    //called when the arm is at set position
    } else {
      driveMoter(0, false);
      return false;
    }
  }

  //stops motor
  public void stop() {
    armMaster.set(0);
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new ManualArmControl());
  
  }
}