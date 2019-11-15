package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.commands.WristControlCommand;

public class WristSubsystem extends Subsystem {

public boolean tunable = false;

  public WPI_TalonSRX wristMaster;
  
  
  public double wristPercentage; //between 0 & 100
  public double wristDifference; //between -1 & 1
  public double wristOutput; //between -1 & 1
  public double wristMove; //between -1 & 1
  public double wristFinal; //between -1 & 1

  public double wristRawEncoder; //whole number
  public double wristEncoderUpperLimit; //whole number 
  public double wristEncoderLowerLimit; //whole number
  public double wristOffset = 0; //whole number
  public boolean wristUpperSet; // boolean
  public boolean wristLowerSet; // boolean

  public boolean wristGoto; //true when arm is moving to preset positions
  
  public double outreachWristSpeed;
  public static double encoderTraveDistence = 375;

  public boolean PovUp;
  public boolean PovDown;

  public boolean wristEncoderUpperReset;
  public boolean wristEncoderLowerReset;

    public WristSubsystem(boolean tunable) {
		this.tunable = tunable;

        //setup armMotor
        wristMaster = new WPI_TalonSRX(RobotMap.wristPort);

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

    System.out.print("wrist upper encoder = " + !Robot.oi.wristUpperLimitSwitch.get());
    System.out.print("wrist lower encoder = " + !Robot.oi.wristLowerLimitSwitch.get());
    System.out.print("wrist offset = " + wristOffset);
    // hard upper
    if (!Robot.oi.wristUpperLimitSwitch.get() && wristOutput < 0) {
        wristOutput = 0;
    }

    // hard lower
    if (!Robot.oi.wristLowerLimitSwitch.get() && wristOutput > 0) {
        wristOutput = 0;
    }

      //send "armOutput" to motors
      wristMaster.set(wristOutput);
    }

    //sets default wrist upper encoder values
    public void wristEncoderUpperReset(boolean upperLimitSwitch) {
        if (upperLimitSwitch && !wristUpperSet) {
            System.out.println("wristEncoderUpperReset");
            wristOffset = -wristRawEncoder + SmartDashboard.getNumber("wristEncoderTravelDistence", 0);
            wristUpperSet = true;
        } else {
            wristUpperSet = false;
        }
        
    }

    //sets default wrist lower encoder values
    public void wristEncoderLowerReset(boolean lowerLimitSwitch) {
        if (lowerLimitSwitch && !wristLowerSet) {
            System.out.println("wristEncoderLowerReset");
            wristOffset = -wristRawEncoder;
            wristLowerSet = true;
        } else {
            wristLowerSet = false;
        }
        
    }

    //get wrist percentage
  public void wristPosition(double extentionEncoder) {
    wristRawEncoder = extentionEncoder;
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
      setDefaultCommand(new WristControlCommand());
    
    }
  
  }




























// package frc.robot.subsystems;

// import com.ctre.phoenix.motorcontrol.ControlMode;
// import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

// import edu.wpi.first.wpilibj.Joystick;
// import edu.wpi.first.wpilibj.command.Subsystem;
// import edu.wpi.first.wpilibj.drive.DifferentialDrive;
// import frc.robot.Robot;
// import frc.robot.RobotMap;
// import frc.robot.commands.WristControlCommand;


// public class WristSubsystem extends Subsystem {

//     public boolean tunable = false;

//     public WPI_TalonSRX wristMaster;

//     public static double wristControl;

//     public double wristMove;

//     public double wristFinal;

//     public double wristSpeed = .25;

//     public DifferentialDrive drive;
  
//     public Joystick joystickwrist;

//     public double outreachWristSpeed;

//     public WristSubsystem(boolean tunable) {
//         this.tunable = tunable;
//         //setup motors
// 		wristMaster = new WPI_TalonSRX(RobotMap.wristPort);
    
//     }

//   	    // DRIVE WRIST
//         public void wristControl(double wristSpeed, boolean upperLimit, boolean lowerLimit, boolean limitBypass, boolean wristLock) {
    
//         wristMove = (-wristSpeed) * RobotMap.wristSpeed * outreachWristSpeed * Robot.NoArmSubsystem.speed;
        
//         if (wristMove > wristFinal) {
//             wristFinal = wristFinal + ((wristMove - wristFinal) * RobotMap.wristSmooth);
//           } else if(wristMove < wristFinal) {
//             wristFinal = wristFinal - ((wristFinal - wristMove) * RobotMap.wristSmooth);
//           }
    

//         if (Math.abs(wristFinal) >= .25) {

//             if (!upperLimit && !limitBypass && !wristLock) {
            
//                 if (wristFinal >= 0) {
//                     wristMaster.set(wristFinal);
//                 } else {             
//                     wristMaster.set(0); 
//                 }

//             } else if (!lowerLimit && !limitBypass && !wristLock) {
            
//                 if (wristFinal <= 0) {  
//                     wristMaster.set(wristFinal);
//                 } else {
//                     wristMaster.set(0);
//                 }

//             } else if(!wristLock){

//                 wristMaster.set(wristFinal);
                
//             }
//         } else {

//             wristMaster.set(0);

//         }


    
//     }

//     public void stop() {
//       wristMaster.set(ControlMode.PercentOutput, 0);
//     }

// 	@Override
// 	public void initDefaultCommand() {
//     	setDefaultCommand(new WristControlCommand());
// 	}
// }