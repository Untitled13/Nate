package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;
import frc.robot.commands.ManualWristControl;

public class WristSubsystem extends Subsystem {

  public boolean tunable = false;

  private WPI_TalonSRX wristMaster;
  private double wristPercentage; //between 0 & 100
  private double wristDifference; //between -1 & 1
  private double wristOutput; //between -1 & 1
  private double wristMove; //between -1 & 1
  private double wristFinal; //between -1 & 1

  private double wristRawEncoder; //whole number
  // private double wristEncoderUpperLimit; //whole number 
  // private double wristEncoderLowerLimit; //whole number
  private double wristOffset = 0; //whole number
  private boolean wristUpperSet; // boolean
  private boolean wristLowerSet; // boolean

  private boolean wristGoto; //true when arm is moving to preset positions
  
  private double outreachWristSpeed;
  // private static double encoderTraveDistence = 375;

  // private boolean PovUp;
  // private boolean PovDown;

  // private boolean wristEncoderUpperReset;
  // private boolean wristEncoderLowerReset;

  private boolean noWrist = false;
  private boolean outreachMode = false;

  private final Encoder wristEncoder;

  private final DigitalInput wristLowerLimitSwitch;
  private final DigitalInput wristUpperLimitSwitch;

    public WristSubsystem(boolean tunable) {
		this.tunable = tunable;
      //setup armMotor
      wristMaster = new WPI_TalonSRX(RobotMap.wristPort);

      // SETUP ENCODER
      wristEncoder = new Encoder(RobotMap.wristEncoderSourceA, RobotMap.wristEncoderSourceB, false, EncodingType.k4X);

      // SETUP LIMITSWITCHES
      wristLowerLimitSwitch = new DigitalInput(RobotMap.armWristLowerLimitSwitch);
      wristUpperLimitSwitch = new DigitalInput(RobotMap.armWristUpperLimitSwitch);
    }

    public void setWristOutreachMode(boolean mode) {
      outreachMode = mode;
      if (outreachMode) {
        outreachWristSpeed = RobotMap.OutreachWristSpeed;
      } else {
        outreachWristSpeed = 1;
      }
    }

    public void setNoWrist(boolean mode) {
      noWrist = mode;
    }

    public double getWristPercentage() {
      return wristPercentage;
    }

    //Manualy move arm
    public void wristControl(double wristSpeed, boolean wristLimitBypass) {
        if (!wristGoto) {
            wristMove = ((-wristSpeed) / RobotMap.wristSpeed) * outreachWristSpeed;

            //Arm smoothing
            if (wristMove > wristFinal) {
                wristFinal = wristFinal + ((wristMove - wristFinal) * RobotMap.wristSmooth);
            } else if(wristMove < wristFinal) {
                wristFinal = wristFinal - ((wristFinal - wristMove) * RobotMap.wristSmooth);
            }
            
            //Send to Motor
            wristOutput = wristFinal;
            
            //Start driveMoter
            driveMoter(wristLimitBypass);
        }
    }
    
    //sets arm output based on "armOutput" from sends
    public void driveMoter(boolean wristLimitBypass) {
      
    //   //upper soft limit
    //   if (wristPercentage >= .8 && wristOutput >= 0 && !armLimitBypass) {
    //       wristOutput = (wristOutput / (wristPercentage * 4)); 
    //   }
      
    //   //upper hard limit
    //   if (wristOutput > 0 && wristPercentage >= 1 && !armLimitBypass) {
    //     wristOutput = 0;
    //   }

    //   //lower soft limit
    //   if (wristPercentage <= .2 && wristOutput <= 0 && !armLimitBypass) {
    //     wristOutput = (wristOutput / (Math.abs(1 - wristPercentage) * 7)); 
    //   }

    // //   lower hard limit
    //   if (wristOutput < 0 && wristPercentage <= 0 && !armLimitBypass) {
    //     wristOutput = 0;
    //   }

    System.out.print("wrist upper encoder = " + !wristUpperLimitSwitch.get());
    System.out.print("wrist lower encoder = " + !wristLowerLimitSwitch.get());
    System.out.print("wrist offset = " + wristOffset);
    // hard upper
    if (!wristUpperLimitSwitch.get() && wristOutput < 0) {
        wristOutput = 0;
    }

    // hard lower
    if (!wristLowerLimitSwitch.get() && wristOutput > 0) {
        wristOutput = 0;
    }

      //send "armOutput" to motors
      if (!noWrist) {
        wristMaster.set(wristOutput);
      } else {
        wristMaster.set(0);
      }
      
    }

    //sets default wrist upper encoder values
    public void wristEncoderUpperReset() {
      boolean upperLimitSwitch = !wristUpperLimitSwitch.get();
        if (upperLimitSwitch && !wristUpperSet) {
            System.out.println("wristEncoderUpperReset");
            wristOffset = -wristRawEncoder + SmartDashboard.getNumber("wristEncoderTravelDistence", 0);
            wristUpperSet = true;
        } else {
            wristUpperSet = false;
        }
        
    }

    //sets default wrist lower encoder values
    public void wristEncoderLowerReset() {
      boolean lowerLimitSwitch = !wristLowerLimitSwitch.get();
        if (lowerLimitSwitch && !wristLowerSet) {
            System.out.println("wristEncoderLowerReset");
            wristOffset = -wristRawEncoder;
            wristLowerSet = true;
        } else {
            wristLowerSet = false;
        }
        
    }

    //get wrist percentage
  public void wristPosition() {
    wristRawEncoder = wristEncoder.get();
    System.out.println("wristRawEncoder = " + wristRawEncoder);
    wristPercentage = ((wristRawEncoder + wristOffset) / SmartDashboard.getNumber("wristEncoderTravelDistence", 0));
    System.out.println("wristPercentage = " + wristPercentage);
    //   extentionPercentage = ((extentionRawEncoder - extentionEncoderLowerLimit) / (110132 ));
  }

    //sets the arm's angle for preset positions
    public void wristSetAngle(double wristPosition) {
      if (Math.abs(wristPosition - wristPercentage) > .02) {
        wristGoto = true;
        //safty, incase the arm is set to o past 100%
        if (wristPosition > 1) {
          wristPosition = 1; 
        } else if(wristPosition < 0){
            wristPosition = 0; 
        }

        //finds the diffrence between where the arm is set to go, and where it is
        wristDifference = -((wristPosition - wristPercentage) * 3 );

        //does not allow the arm to move faseer than .6
        if (wristDifference >= .4) {
          wristDifference = .4;
        } else if (wristDifference <=-.4) {
          wristDifference = -.4;
        }

        //does not allow the arm to move slower than .1, so it can make it to its end point
        if (wristDifference <= .2 && wristDifference > 0) {
          wristDifference = .2;
        } else if (wristDifference >=-.2 && wristDifference < 0) {
          wristDifference = -.2;
        }

        //sends the arm's power to the moter
        wristOutput = wristDifference;
        driveMoter(false);

      //called when the arm is at set position
      } else if (Math.abs(wristPosition - wristPercentage) < .02) {
        wristOutput = 0;
        wristGoto = false;
        driveMoter(false);
        }
    }

    //stops motor
    public void stop() {
      wristMaster.set(0);
    }

  	@Override
	  public void initDefaultCommand() {
      setDefaultCommand(new ManualWristControl());
    
    }
  
  }
