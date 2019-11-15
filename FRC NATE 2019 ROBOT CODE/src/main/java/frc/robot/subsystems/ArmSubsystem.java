package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.commands.ArmControlCommand;

public class ArmSubsystem extends Subsystem {

	public boolean tunable = false;

  public WPI_TalonSRX armMaster;
  
  
  public double armPercentage; //between 0 & 100
  public double armDifference; //between -1 & 1
  public double armOutput; //between -1 & 1
  public double armMove; //between -1 & 1
  public double armFinal; //between -1 & 1

  public double armRawEncoder; //whole number
  public double armEncoderUpperLimit; //whole number 
  public double armEncoderLowerLimit; //whole number

  public boolean armGoto; //true when arm is moving to preset positions
  
  public double outreachArmSpeed;

  public boolean PovUp;
  public boolean PovDown;

  public boolean armEncoderUpperReset;
  public boolean armEncoderLowerReset;

	public ArmSubsystem(boolean tunable) {
		this.tunable = tunable;

    //setup armMotor
    armMaster = new WPI_TalonSRX(RobotMap.armPort);

  }
    
    //Manualy move arm
    public void armControl(double armSpeed, boolean armLimitBypass) {
      armMove = ((-armSpeed) / RobotMap.armSpeed) * outreachArmSpeed * Robot.NoArmSubsystem.speed;

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
      if (Robot.extentionSubsystem.extentionPercentage > .1 && Robot.extentionSubsystem.extentionPercentage < .9 && armPercentage < .2 && armOutput <= .1) {
        armOutput = 0;
        setBrake(true);
      }

      //send "armOutput" to motors
      armMaster.set(armOutput);
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
      Robot.oi.armBreakSolenoid.set(state);
    }
    
    //sets default arm encoder values
    public void armEncoderReset() {
      armEncoderUpperLimit = -5;
      armEncoderLowerLimit = 1720;
    }

    //resets arm encoder upper value when limit switch is pressed
    public void armEncoderUpperReset(double joystick, Boolean UpperLimit, double POV){
      if (POV == 315 || POV == 0 || POV == 45) {
        PovUp = true;
      } else {
        PovUp = false;
      }

      if (joystick >.1 && !UpperLimit && PovUp) {
        armEncoderUpperLimit = armRawEncoder;
        armEncoderLowerLimit = armRawEncoder + 1725;
      }

    }

    //resets arm encoder lower value when limit switch is pressed
    public void armEncoderLowerReset(double joystick, Boolean LowerLimit, double POV){
      if (POV == 135 || POV == 180 || POV == 225) {
        PovDown = true;
      }

      if (joystick < -.1 && !LowerLimit && PovDown) {
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
      setDefaultCommand(new ArmControlCommand());
    
    }
  
  }