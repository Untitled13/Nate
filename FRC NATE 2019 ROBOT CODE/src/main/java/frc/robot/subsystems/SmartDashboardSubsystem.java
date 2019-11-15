package frc.robot.subsystems;

import edu.wpi.first.hal.sim.DriverStationSim;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.commands.SmartDashboardCommand;
import frc.robot.sensors.PDP;

/**
 * Add your docs here.
 */
public class SmartDashboardSubsystem extends Subsystem {
  public double armPreset1;
  public double armPreset2;
  public double armPreset3;
  public double armPreset4;
  public double armPreset5;
  public double armPreset6;
  public double armPreset7;

  public double extentionPreset1;
  public double extentionPreset2;
  public double extentionPreset3;
  public double extentionPreset4;
  public double extentionPreset5;
  public double extentionPreset6;
  public double extentionPreset7;

  public double wristPreset1;
  public double wristPreset2;
  public double wristPreset3;
  public double wristPreset4;
  public double wristPreset5;
  public double wristPreset6;
  public double wristPreset7;

  public int pipelinePreset1;
  public int pipelinePreset2;
  public int pipelinePreset3;
  public int pipelinePreset4;
  public int pipelinePreset5;
  public int pipelinePreset6;
  public int pipelinePreset7;

  public boolean armEncoderReset;

  // Robot
  public void setup() {
    SmartDashboard.putNumber("wristEncoderTravelDistence", 0);
    SmartDashboard.putNumber("armPickUpHatchPosition", armPreset1);
    SmartDashboard.putNumber("armPickUpBallPosition", armPreset2);
    SmartDashboard.putNumber("armPickUpBallOffFloorPosition", armPreset3);
    SmartDashboard.putNumber("armPlaceHatchPosition", armPreset4);
    SmartDashboard.putNumber("armPlaceBallInRocketPosition", armPreset5);
    SmartDashboard.putNumber("armPlaceBallInCargoShipPosition", armPreset6);
    SmartDashboard.putNumber("armVerticlePosition", armPreset7);

    SmartDashboard.putNumber("extentionPickUpHatchPosition", extentionPreset1);
    SmartDashboard.putNumber("extentionPickUpBallPosition", extentionPreset2);
    SmartDashboard.putNumber("extentionPickUpBallOffFloorPosition", extentionPreset3);
    SmartDashboard.putNumber("extentionPlaceHatchPosition", extentionPreset4);
    SmartDashboard.putNumber("extentionPlaceBallInRocketPosition", extentionPreset5);
    SmartDashboard.putNumber("extentionPlaceBallInCargoShipPosition", extentionPreset6);
    SmartDashboard.putNumber("extentionVerticlePosition", extentionPreset7);

    SmartDashboard.putNumber("wristPickUpHatchPosition", wristPreset1);
    SmartDashboard.putNumber("wristPickUpBallPosition", wristPreset2);
    SmartDashboard.putNumber("wristPickUpBallOffFloorPosition", wristPreset3);
    SmartDashboard.putNumber("wristPlaceHatchPosition", wristPreset4);
    SmartDashboard.putNumber("wristPlaceBallInRocketPosition", wristPreset5);
    SmartDashboard.putNumber("wristPlaceBallInCargoShipPosition", wristPreset6);
    SmartDashboard.putNumber("wristVerticlePosition", wristPreset7);

    SmartDashboard.putNumber("pipelinePickUpHatchPosition", pipelinePreset1);
    SmartDashboard.putNumber("pipelinePickUpBallPosition", pipelinePreset2);
    SmartDashboard.putNumber("pipelinePickUpBallOffFloorPosition", pipelinePreset3);
    SmartDashboard.putNumber("pipelinePlaceHatchPosition", pipelinePreset4);
    SmartDashboard.putNumber("pipelinePlaceBallInRocketPosition", pipelinePreset5);
    SmartDashboard.putNumber("pipelinePlaceBallInCargoShipPosition", pipelinePreset6);
    SmartDashboard.putNumber("pipelineVerticlePosition", pipelinePreset7);

    SmartDashboard.putNumber("driveSmooth", .5);
  }

  public void run() {

    // SmartDashboard.putNumber("PDPVoltage", Robot.PDP.PDPVoltage());
    // SmartDashboard.putNumber("PDPTotalAmp", Robot.PDP.PDPTotalAmp());
    // SmartDashboard.putNumber("PDPTotalAmpMax", NumberHold(Robot.PDP.PDPTotalAmp()));

    // SmartDashboard.putNumber("PDPArmAmp", Robot.PDP.PDPArmAmp());
    // SmartDashboard.putNumber("PDPExtentionAmp", Robot.PDP.PDPExtentionAmp());
    // SmartDashboard.putNumber("PDPWristAmp", Robot.PDP.PDPWristAmp());
    // SmartDashboard.putNumber("PDPLeftDriveAmp", Robot.PDP.PDPLeftDriveAmp());
    // SmartDashboard.putNumber("PDPRightDriveAmp", Robot.PDP.PDPRightDriveAmp());
    SmartDashboard.putBoolean("OutreachMode", Robot.OutreachModeSubsystem.OutreachMode);
    SmartDashboard.putBoolean("NoArm", Robot.NoArmSubsystem.NoArm);
    SmartDashboard.putNumber("Arm Percentage", Robot.armSubsystem.armPercentage);
    SmartDashboard.putNumber("Extention Percentage", Robot.extentionSubsystem.extentionPercentage);
    SmartDashboard.putNumber("Wrist Percentage", Robot.WristSubsystem.wristPercentage);
    SmartDashboard.putNumber("ArmAmp", Robot.PDP.PDPArmAmp());
    SmartDashboard.putNumber("ExtentionAmp", Robot.PDP.PDPExtentionAmp());
    SmartDashboard.putNumber("WristAmp", Robot.PDP.PDPWristAmp());

    armPreset1 = (double)SmartDashboard.getNumber("armPickUpHatchPosition", 0); //The encoder position of the arm for the position pick up hatch
    armPreset2 = (double)SmartDashboard.getNumber("armPickUpBallPosition", 0); //The encoder position of the arm for the position pick up ball
    armPreset3 = (double)SmartDashboard.getNumber("armPickUpBallOffFloorPosition", 0); //The encoder position of the arm for the position pick up ball off floor
    armPreset4 = (double)SmartDashboard.getNumber("armPlaceHatchPosition", 0); //The encoder position of the arm for the position place hatch
    armPreset5 = (double)SmartDashboard.getNumber("armPlaceBallInRocketPosition", 0); //The encoder position of the arm for the position place ball in rocket
    armPreset6 = (double)SmartDashboard.getNumber("armPlaceBallInCargoShipPosition", 0); //The encoder position of the arm for the position place ball in cargo ship
    armPreset7 = (double)SmartDashboard.getNumber("armVerticlePosition", 0); //The encoder position of the arm for the position vertical

    extentionPreset1 = (double)SmartDashboard.getNumber("extentionPickUpHatchPosition", 0); //The encoder position of the extention for the position pick up hatch
    extentionPreset2 = (double)SmartDashboard.getNumber("extentionPickUpBallPosition", 0); //The encoder position of the extention for the position pick up ball
    extentionPreset3 = (double)SmartDashboard.getNumber("extentionPickUpBallOffFloorPosition", 0); //The encoder position of the extention for the position pick up ball off floor
    extentionPreset4 = (double)SmartDashboard.getNumber("extentionPlaceHatchPosition", 0); //The encoder position of the extention for the position place hatch
    extentionPreset5 = (double)SmartDashboard.getNumber("extentionPlaceBallInRocketPosition", 0); //The encoder position of the extention for the position place ball in rocket
    extentionPreset6 = (double)SmartDashboard.getNumber("extentionPlaceBallInCargoShipPosition", 0); //The encoder position of the extention for the position place ball in cargo ship
    extentionPreset7 = (double)SmartDashboard.getNumber("extentionVerticlePosition", 0); //The encoder position of the extention for the position vertical

    wristPreset1 = (double)SmartDashboard.getNumber("wristPickUpHatchPosition", 0); //The encoder position of the wrist for the position pick up hatch
    wristPreset2 = (double)SmartDashboard.getNumber("wristPickUpBallPosition", 0); //The encoder position of the wrist for the position pick up ball
    wristPreset3 = (double)SmartDashboard.getNumber("wristPickUpBallOffFloorPosition", 0); //The encoder position of the wrist for the position pick up ball off floor
    wristPreset4 = (double)SmartDashboard.getNumber("wristPlaceHatchPosition", 0); //The encoder position of the wrist for the position place hatch
    wristPreset5 = (double)SmartDashboard.getNumber("wristPlaceBallInRocketPosition", 0); //The encoder position of the wrist for the position place ball in rocket
    wristPreset6 = (double)SmartDashboard.getNumber("wristPlaceBallInCargoShipPosition", 0); //The encoder position of the wrist for the position place ball in cargo ship
    wristPreset7 = (double)SmartDashboard.getNumber("wristVerticlePosition", 0); //The encoder position of the wrist for the position vertical

    pipelinePreset1 = (int)SmartDashboard.getNumber("pipelinePickUpHatchPosition", 0); //The encoder position of the pipeline for the position pick up hatch
    pipelinePreset2 = (int)SmartDashboard.getNumber("pipelinePickUpBallPosition", 0); //The encoder position of the pipeline for the position pick up ball
    pipelinePreset3 = (int)SmartDashboard.getNumber("pipelinePickUpBallOffFloorPosition", 0); //The encoder position of the pipeline for the position pick up ball off floor
    pipelinePreset4 = (int)SmartDashboard.getNumber("pipelinePlaceHatchPosition", 0); //The encoder position of the pipeline for the position place hatch
    pipelinePreset5 = (int)SmartDashboard.getNumber("pipelinePlaceBallInRocketPosition", 0); //The encoder position of the pipeline for the position place ball in rocket
    pipelinePreset6 = (int)SmartDashboard.getNumber("pipelinePlaceBallInCargoShipPosition", 0); //The encoder position of the pipeline for the position place ball in cargo ship
    pipelinePreset7 = (int)SmartDashboard.getNumber("pipelineVerticlePosition", 0); //The encoder position of the pipeline for the position vertical
  }

  @Override
  public void initDefaultCommand() {
   setDefaultCommand(new SmartDashboardCommand());
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}