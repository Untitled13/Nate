package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.DigitalInput;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.commands.ManualArmControl;

public class ArmSubsystem extends Subsystem {
  public boolean tunable = false;
  
  private WPI_TalonSRX armMaster;
  
  private double armPercentage; //between 0 & 100
  private double armDifference; //between -1 & 1
  private double armOutput; //between -1 & 1
  private double armMove; //between -1 & 1
  private double armFinal; //between -1 & 1

  private double armRawEncoder; //whole number
  private double armEncoderUpperLimit; //whole number 
  private double armEncoderLowerLimit; //whole number

  private boolean armGoto; //true when arm is moving to preset positions
  
  private double outreachArmSpeed;

  private boolean PovUp;
  private boolean PovDown;

  private boolean noArm = false;
  private boolean outreachMode = false;

  private Solenoid armBrakeSolenoid;

  private final DigitalInput armLowerLimitSwitch;
  private final DigitalInput armUpperLimitSwitch;

	public ArmSubsystem(boolean tunable) {
		this.tunable = tunable;

    //setup armMotor
    armMaster = new WPI_TalonSRX(RobotMap.armPort);

    // SETUP BRAKE SOLENOID
    armBrakeSolenoid = new Solenoid(RobotMap.PCM, RobotMap.armBreakSolenoid);

    // SETUP LIMITSWITCHES
    armLowerLimitSwitch = new DigitalInput(RobotMap.armLowerLimitSwitchPort);
    armUpperLimitSwitch = new DigitalInput(RobotMap.armUpperLimitSwitchPort);
  }

    public void setArmOutreachMode(boolean mode) {
      outreachMode = mode;
      if (outreachMode) {
        outreachArmSpeed = RobotMap.OutreachArmSpeed;
      } else {
        outreachArmSpeed = 1;
      }
    }

    public void setNoArm(boolean mode) {
      noArm = mode;
    }

    public void setArmGoto(boolean value) {
      armGoto = value;
    }

    public double getArmPercentage() {
      return armPercentage;
    }

    public boolean getNoArm() {
      return noArm;
    }

    public boolean getOutreachMode() {
      return outreachMode;
    }

    public boolean getArmGoto() {
      return armGoto;
    }

    //Manualy move arm
    public void armControl(double armSpeed, boolean armLimitBypass) {
      armMove = ((-armSpeed) / RobotMap.armSpeed) * outreachArmSpeed;

      //Arm smoothing
      if (armMove > armFinal) {
        armFinal = armFinal + ((armMove - armFinal) * RobotMap.armSmooth);
      } else if(armMove < armFinal) {
        armFinal = armFinal - ((armFinal - armMove) * RobotMap.armSmooth);
      }
      
      //Send to Motor
      armOutput = armFinal;
     
      //Start driveMorer, and armBrake
      driveMoter(armLimitBypass);
      armBrake();
    }
    
    //sets arm output based on "armOutput" from sends
    public void driveMoter(boolean armLimitBypass) {
      
      //upper soft limit
      if (armPercentage >= .8 && armOutput >= 0 && !armLimitBypass) {
          armOutput = (armOutput / (armPercentage * 4)); 
      }
      
      //upper hard limit
      if (armOutput > 0 && armPercentage >= 1 && !armLimitBypass) {
        armOutput = 0;
      }

      //lower soft limit
      if (armPercentage <= .2 && armOutput <= 0 && !armLimitBypass) {
        armOutput = (armOutput / (Math.abs(1 - armPercentage) * 7)); 
      }

      //lower hard limit
      if (armOutput < 0 && armPercentage <= 0 && !armLimitBypass) {
        armOutput = 0;
      }

      //lower limit, to stop wires from being pinched, and the suction cup does not get jamed
      if (Robot.extentionSubsystem.getExtentionPercentage() > .1 && Robot.extentionSubsystem.getExtentionPercentage() < .9 && armPercentage < .2 && armOutput <= .1) {
        armOutput = 0;
        setBrake(true);
      }

      //send "armOutput" to motors
      if (!noArm) {
        armMaster.set(armOutput);
      } else {
        armMaster.set(0);
      }
      
    }

    //decides when the arm break should be applyed
    public void armBrake() {

        if (Math.abs (armOutput) < .07 ) {
          brakeArm();
        } else {
          unbrakeArm(); 
        } 
    }    
      
    //brakes the arm
    public void brakeArm() {
      setBrake(true);
    }

    //unbrakes the arm
    public void unbrakeArm() {
      setBrake(false);
    }

    //sends "setbrake" to arm
    public void setBrake(boolean state) {
      armBrakeSolenoid.set(state);
    }
    
    //sets default arm encoder values
    public void armEncoderReset() {
      armEncoderUpperLimit = -5;
      armEncoderLowerLimit = 1720;
    }

    //resets arm encoder upper value when limit switch is pressed
    public void armEncoderUpperReset(double joystick, double POV){
      boolean upperLimit = armUpperLimitSwitch.get();
      if (POV == 315 || POV == 0 || POV == 45) {
        PovUp = true;
      } else {
        PovUp = false;
      }

      if (joystick >.1 && !upperLimit && PovUp) {
        armEncoderUpperLimit = armRawEncoder;
        armEncoderLowerLimit = armRawEncoder + 1725;
      }

    }

    //resets arm encoder lower value when limit switch is pressed
    public void armEncoderLowerReset(double joystick, double POV){
      boolean lowerLimit = armLowerLimitSwitch.get();
      if (POV == 135 || POV == 180 || POV == 225) {
        PovDown = true;
      }

      if (joystick < -.1 && !lowerLimit && PovDown) {
        armEncoderLowerLimit = armRawEncoder;
      }
    
    }

    //gets the arm percentage (0-1) value from encoder
    public void armAngle() {
      armRawEncoder = armMaster.getSelectedSensorPosition();
      armPercentage = ((armEncoderLowerLimit - armRawEncoder) * (1/(armEncoderLowerLimit - armEncoderUpperLimit)));
    }

    //sets the arm's angle for preset positions
    public void armSetAngle(double armPosition) {
      unbrakeArm();
      if (Math.abs(armPosition - armPercentage) > .02) {
        
        //safty, incase the arm is set to o past 100%
        if (armPosition > 100) {
          armPosition = 100; 
        } else if(armPosition < 0){
       
        }

        //finds the diffrence between where the arm is set to go, and where it is
        armDifference = ((armPosition - armPercentage + .02) * 3 );
        
        //stops the arm from hitting the motor when it moves back
        if (armPercentage >= .6 && armPosition >= 90) {

          if (armDifference >= 0) {
            armDifference = (armDifference / (armPercentage * 2) + .05);
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
        armOutput = armDifference;
        driveMoter(false);

      //called when the arm is at set position
      } else if (Math.abs(armPosition - armPercentage) < .02) {
        armOutput = 0;
        armGoto = false;
        driveMoter(false);
        brakeArm();
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