package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.DigitalInput;
import frc.robot.RobotMap;
import frc.robot.commands.ManualExtentionControl;

public class ExtentionSubsystem extends Subsystem {

	public boolean tunable = false;

  private WPI_TalonSRX extentionMaster;

  private double extentionPercentage; //between 0 & 1
  private double extentionDifference; //between -1 & 1
  private double extentionMove; //between -1 & 1
  private double extentionOutput; //between -1 & 1
  private boolean extentionGoto; //true when extention is going to preset position
  private double extentionSpeed; //between -1 & 1
  private double extentionFinal; //between -1 & 1

  private double extentionEncoderUpperLimit; //whole number greater than 0
  private double extentionEncoderLowerLimit; //should be 0
  private double extentionRawEncoder; //whole number

  private double outreachExtentionSpeed;

  private boolean noExtention = false;
  private boolean outreachMode = false;

  private final Encoder extentionEncoder;

  private final DigitalInput extentionLowerLimitSwitch;
  private final DigitalInput extentionUpperLimitSwitch;

	public ExtentionSubsystem(boolean tunable) {
    this.tunable = tunable;
    
    //setup motor
    extentionMaster = new WPI_TalonSRX(RobotMap.armExtentionPort);
    
    // SETUP ENCODER
    extentionEncoder = new Encoder(RobotMap.extentionEncoderSourceA, RobotMap.extentionEncoderSourceB, false, EncodingType.k4X);

    // SETUP LIMITSWITCHES
    extentionLowerLimitSwitch = new DigitalInput(RobotMap.armExtentionLowerLimitSwitch);
    extentionUpperLimitSwitch = new DigitalInput(RobotMap.armExtentionUpperLimitSwitch);

  }

  public void setExtentionOutreachMode(boolean mode) {
    outreachMode = mode;
    if (outreachMode) {
      outreachExtentionSpeed = RobotMap.OutreachExtentionSpeed;
    } else {
      outreachExtentionSpeed = 1;
    }
    
  }

  public void setNoExtention(boolean mode) {
    noExtention = mode;
  }

  public void setExtentionGoto(boolean value) {
    extentionGoto = value;
  }

  public boolean getExtentionGoto() {
    return extentionGoto;
  }

  public double getExtentionPercentage() {
    return extentionPercentage;
  }

  //extention manual drive
  public void extendControl(Boolean extend, boolean retract) {

    if (!extentionGoto) {
      //set "extentionSpeed" to extend
      if (extend) {
        extentionSpeed = RobotMap.ExtentionSpeed + .1;
      } else if (retract) {
        extentionSpeed = -RobotMap.ExtentionSpeed - .1;
      } else {
        extentionSpeed = 0;
      }

      //set extentionMove to extentionSpeed, plus calibration
      extentionMove = ((extentionSpeed) / 2) * outreachExtentionSpeed;

      //extention smoothing
      if (extentionMove > extentionFinal) {
        extentionFinal = extentionFinal + ((extentionMove - extentionFinal)* RobotMap.extentionSmooth);
      } else if(extentionMove < extentionFinal) {
        extentionFinal = extentionFinal - ((extentionFinal - extentionMove)* RobotMap.extentionSmooth);
      }

      //send extentionFinal to ouput
      extentionOutput = extentionFinal;
    }
  }

  // send extentionOutput to motor
  public void driveMoter(boolean extentionLimitBypass) {
    
    //sets soft upper limit
    if (extentionPercentage >=.95 && extentionOutput >= 0 && !extentionLimitBypass) {
      extentionOutput = (extentionOutput * (Math.abs(extentionPercentage - 1))); 
    }
    
    //sets soft lower limit
    if (extentionPercentage <=.07 && extentionOutput <= 0 && !extentionLimitBypass) {
      extentionOutput = (extentionOutput / Math.abs(extentionPercentage - 10)); 
    }

    //set extention max speed
    if (extentionOutput >= .6) {
      extentionOutput = .6;
    }

    // sends extentionOutput to motor
    if (!noExtention) {
      extentionMaster.set(extentionOutput);
    } else {
      extentionMaster.set(0);
    }
    
  }



  //reset extention encoder upper & lower limits
  public void extentionEncoderReset() {

    extentionEncoder.reset();
    extentionEncoderUpperLimit = 110132;
    extentionEncoderLowerLimit = 0;
    

  }

  //reset lower limit
  public void extentionEncoderLowerReset(){
    boolean lowerLimit = extentionLowerLimitSwitch.get();
    if (!lowerLimit) {
      extentionEncoderLowerLimit = extentionRawEncoder - 3000;  
    }

  }

  //get extention percentage
  public void extentionPosition() {
    extentionRawEncoder = extentionEncoder.get();
      extentionPercentage = ((extentionRawEncoder - extentionEncoderLowerLimit) / (110132 ));
  }

  public void extentionSetPosition(double extentionPosition) {
    
    //if extention is not at set position
    if (Math.abs(extentionPosition - extentionPercentage) > .05) {
      
      //safty, so the extention can not be over, or under extended
      if (extentionPosition > 1) {
        extentionPosition = 1; 
      } else if(extentionPosition < 0){
      
      }

      //finds the diffrence between where the extention shound be, and where it is
      extentionDifference = ((extentionPosition - extentionPercentage));
      
      //sets the extention max speed
      if (extentionDifference >= .7) {
        extentionPercentage = .7;
      } else if (extentionDifference <=-.7) {
        extentionDifference = -.7;
      }

      //sets the extention slowest speed
      if (extentionDifference <= .2 && extentionDifference > 0) {
        extentionDifference = .2;
      } else if (extentionDifference >=-.2 && extentionDifference < 0) {
        extentionDifference = -.2;
      }


      extentionOutput = extentionDifference;
      driveMoter(false);

    //if extention is at set position
    } else if (Math.abs(extentionPosition - extentionPercentage) < .05) {

      extentionOutput = 0;
      extentionGoto = false;
      driveMoter(false);

    }

  }


  public void stop() {
    extentionMaster.set(0);
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new ManualExtentionControl());
  }
  
}